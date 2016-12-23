package com.shubo.sniff.report;

import com.alibaba.fastjson.JSON;
import com.shubo.AppContext;
import com.shubo.ExceptionString;
import com.shubo.annotation.Horseman;
import com.shubo.annotation.Todd;
import com.shubo.entity.report.ConsolidatedEquityChange;
import com.shubo.entity.report.EquityChange;
import com.shubo.exception.AnnotationException;
import com.shubo.sniff.Sniffer;
import com.shubo.sniff.TableSniffer;
import com.shubo.stastics.AnalyticalResult;
import com.shubo.util.HorsemanUtils;
import com.shubo.util.SimilarityUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by horseman on 2016/11/28.
 * 合并所有者权益变动表
 */
@Todd(key = "ConsolidatedEquityChange",
        index = 2,
        suffix = ".ces",
        folder = "合并所有者权益变动表",
        title = {"合并所有者权益变动表", "合并股东权益变动表"})
public class ConsolidatedEquityChangeSniffer extends Sniffer {

    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content, CECKeyWords, 4);
    }

    @Override
    public ColResult getColCnt(String table, Class clazz) {
        return null;
    }

    /**
     * @param table 需要完善
     * @return
     */


    private static final List<String> likedKeyWord1 = new ArrayList<>();
    private static final List<String> likedKeyWord2 = new ArrayList<>();
    private static final List<String> likedKeyWord3 = new ArrayList<>();
    private static final List<String> likedKeyWord4 = new ArrayList<>();

    private static final List<List<String>> likedKeyWordsSet = new ArrayList<>();

    static {
        likedKeyWord1.add("实收资本或股本");
        likedKeyWord1.add("实收资本（或股本）");
        likedKeyWord1.add("实收资本(或股本)");
        likedKeyWord1.add("实收资本（或股本");
        likedKeyWord1.add("实收资本");
        likedKeyWord1.add("股本");

        likedKeyWord2.add("优先股");
        likedKeyWord2.add("永续债");
        likedKeyWord2.add("其他");

        likedKeyWord3.add("资本公积");
        likedKeyWord3.add("）资本公积");
        likedKeyWord3.add("减:库存股");
        likedKeyWord3.add("减：库存股");
        likedKeyWord3.add("专项储备");
        likedKeyWord3.add(":专项储备");
        likedKeyWord3.add("盈余公积");
        likedKeyWord3.add("一般风险准备");
        likedKeyWord3.add("未分配利润");
        likedKeyWord3.add("其他");
        likedKeyWord3.add("其他综合收益");

        likedKeyWord4.add("少数股东权益");
        likedKeyWord4.add("所有者权益合计");

        likedKeyWordsSet.add(likedKeyWord1);
        likedKeyWordsSet.add(likedKeyWord2);
        likedKeyWordsSet.add(likedKeyWord3);
        likedKeyWordsSet.add(likedKeyWord4);
    }

    private List<IndexEntity> getHeaders(String tableStr) throws AnnotationException {

        tableStr = HorsemanUtils.removeBlank(tableStr);
        String[] lines = tableStr.split("\n");

        // 1,计算列数
        // 选取最多的列数
        int colCnt = 0;
        for (String line : lines) {
            String strs[] = line.split(TableSniffer.ELEMENT_DIVIDOR);
            colCnt = strs.length > colCnt ? strs.length : colCnt;
        }

        // 2,找出关键字集合
        List<String> keyWords1 = new ArrayList<>();
        List<String> keyWords2 = new ArrayList<>();
        List<String> keyWords3 = new ArrayList<>();
        List<String> keyWords4 = new ArrayList<>();

        List<List<String>> keywords = new ArrayList<>();

        keywords.add(keyWords1);
        keywords.add(keyWords2);
        keywords.add(keyWords3);
        keywords.add(keyWords4);

        List<String> forDebug = new ArrayList<>();
        List<String> lastMatchKeys = likedKeyWordsSet.get(0);
        int lastIndex = 0;

        for (String line : lines) {

            if (line.contains("年末余额") || line.contains("期末余额")) {
                break;
            }

            String elements[] = line.split(TableSniffer.ELEMENT_DIVIDOR);
            for (String element : elements) {
                element = HorsemanUtils.removeBlank(element);
                forDebug.add(element);

                if (lastMatchKeys.contains(element)) {
                    keywords.get(lastIndex).add(element);
                    continue;
                }
                for (int i = 0; i < likedKeyWordsSet.size(); i++) {
                    List<String> keys = likedKeyWordsSet.get(i);
                    if (keys.contains(element)) {
                        //System.out.println(element);
                        keywords.get(i).add(element);

                        lastIndex = i;
                        lastMatchKeys = keys;
                        break;
                    }
                }
            }
        }

        // 3,如果关键字集合长度不等于列数，有问题！！需要检查
        if (colCnt - 1 !=
                keyWords1.size() + keyWords2.size() + keyWords3.size() + keyWords4.size()) {
            HorsemanUtils.doSomeThing();
            //AnalyticalResult.singleFileResultNum[1]++;
            return null;
        } else if (colCnt > 15) {
            if (keyWords1.size() % 2 != 0 || keyWords2.size() % 2 != 0 || keyWords3.size() % 2 != 0 || keyWords4.size() % 2 != 0) {
                return null;
            }
        }


        // 4,对搜索到的关键子排序
        // 一般把少数股东权益跟所有者权益合计放到后面
        int index = 1;
        List<String> sameKeys = new ArrayList<>();
        List<IndexEntity> indexEntities = new ArrayList<>();
        for (List<String> keys : keywords) {
            for (String key : keys) {
                Boolean flag = true;
                for (String samekey : sameKeys) {
                    if (key.equals(samekey)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    IndexEntity indexEntity = new IndexEntity(index++, key);
                    indexEntities.add(indexEntity);
                    sameKeys.add(key);
                }
            }
        }
        return indexEntities;
    }

    @Override
    public String[] generateEntityJson(String content) throws AnnotationException {
        List<IndexEntity> indexes = getHeaders(content);
        if (indexes == null) {
            String[] result = new String[2];
            result[0] = ExceptionString.HEADER_SNIFF_ERR;
            return result;
        }
        return generateEntityJson(content, indexes);
    }

    public String[] generateEntityJson(String content, List<IndexEntity> indexes) {
        String lines[] = content.split("\n");

        int colCnt = indexes.size();
        if (lines != null && lines.length > 0) {
            Object data = null;
            try {
                data = ConsolidatedEquityChange.class.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (data == null) {
                return null;
            }

            Field[] declaredFields = data.getClass().getDeclaredFields();
            List<Field> fields = new ArrayList<>();

            Collections.addAll(fields, declaredFields);

            List<String> needKickoutLines = new ArrayList<>();

            for (String line : lines) {
                String[] contents = line.split(TableSniffer.ELEMENT_DIVIDOR, -1);

                if (contents != null && contents.length == colCnt + 1) {

                    try {
                        Field needKickoutField = null;
                        boolean found = false;
                        for (Field field : fields) {
                            EquityChange quityChange = null;
                            Annotation[] annotations = field.getAnnotations();
                            if (annotations.length > 0) {
                                Horseman horsemen = (Horseman) annotations[0];
                                String[] keys = horsemen.keys();
                                for (String key : keys) {
                                    boolean useSimilarity = horsemen.similar();

                                    // 判断该属性是否用模糊判断，如果模糊判断则计算相似度，相似度大于0.9则判定为该属性
                                    // 如果不用模糊判断，则用全匹配
                                    if (useSimilarity) {
                                        float similarity = SimilarityUtils.getSimilarityRatio(key, contents[0].replace(" ", ""));
                                        if (similarity > 0.9f) {
                                            found = true;
                                        }
                                    } else {
                                        if (key.equals(contents[0].replace(" ", ""))) {
                                            found = true;
                                        }
                                    }
                                    if (found) {
                                        EquityChange ec = generateEntity(contents, indexes);
                                        field.set(data, ec);
                                    }
                                }
                            }

                            if (found) {
                                break;
                            }
                        }

                        if (found) {
                            fields.remove(needKickoutField);
                            needKickoutLines.add(line);
                        } else {
                            if (!contents[0].equals("")) {
                                File folder = new File(AppContext.matchFaildFolder);
                                if (!folder.exists()) {
                                    folder.mkdir();
                                }
                                FileUtils.write(new File(AppContext.matchFaildFolder + File.separator + getFolder() + ".txt"), contents[0] + "\r\n", true);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (AnnotationException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 匹配过的行去掉
            for (String deleteStr : needKickoutLines) {
                content = content.replace(deleteStr + "\n", "");
            }

//            System.out.println();

            String[] res = new String[2];
            res[0] = JSON.toJSONString(data);
            res[1] = content;

            return res;
        }

        return null;
    }

    private static EquityChange generateEntity(String[] contents, List<IndexEntity> indexes) {
        EquityChange equityChange = new EquityChange();

        Field[] ecFields = EquityChange.class.getDeclaredFields();
        for (Field f : ecFields) {

            Annotation[] anns = f.getAnnotations();
            Horseman horseman = (Horseman) anns[0];

            String[] keys = horseman.keys();
            boolean found = false;
            IndexEntity indexEntity = null;
            for (String key : keys) {
                for (IndexEntity entity : indexes) {
                    boolean useSimilarity = horseman.similar();

                    // 判断该属性是否用模糊判断，如果模糊判断则计算相似度，相似度大于0.9则判定为该属性
                    // 如果不用模糊判断，则用全匹配
                    if (useSimilarity) {
                        float similarity = SimilarityUtils.getSimilarityRatio(key, entity.getAttributeName());
                        if (similarity > 0.9f) {
                            indexEntity = entity;
                            found = true;
                        }
                    } else {
                        if (key.equals(entity.getAttributeName())) {
                            indexEntity = entity;
                            found = true;
                        }
                    }

                    if (found) {
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }

            if (found) {
                try {
                    f.set(equityChange, contents[indexEntity.getIndex()]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return equityChange;
    }

    private static final String[] CECKeyWords = {
            "加：会计政策变更",
            "前期差错更正",
            "同一控制下企业合并",
            "1．股东投入的普通股",
            "2．提取一般风险准备",
            "1．资本公积转增资本（或股本）",
            "2．盈余公积转增资本（或股本）"
    };
}

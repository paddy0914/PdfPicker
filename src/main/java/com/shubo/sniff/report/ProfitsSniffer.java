package com.shubo.sniff.report;

import com.alibaba.fastjson.JSON;
import com.shubo.AppContext;
import com.shubo.annotation.Horseman;
import com.shubo.annotation.Todd;
import com.shubo.entity.report.ConsolidatedCashFlow;
import com.shubo.entity.report.ConsolidatedProfits;
import com.shubo.exception.AnnotationException;
import com.shubo.sniff.Sniffer;
import com.shubo.sniff.TableSniffer;
import com.shubo.util.SimilarityUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liujinping on 2016/12/20.
 */
@Todd(key = "Profits",
        index = -1,
        suffix = ".pf",
        folder = "合并及公司利润表",
        title = {"合并及公司利润表"})
public class ProfitsSniffer extends Sniffer {
    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content, PFSKeyWords, 9);
    }

    @Override
    public ColResult getColCnt(String table, Class clazz) {
        ColResult colResult = new ColResult();
        int result[] = null;
        int[] colWhere;
        if (Sniffer.isReportEntity(clazz)) {
            colResult.isList = true;
        } else {
            colResult.isList = false;
        }
        result = getColWhere(table);
        colResult.colCnt = result[0];
        colWhere = new int[result.length - 1];
        for (int i = 0; i < result.length - 1; i++) {
            colWhere[i] = result[i + 1];
        }
        colResult.where = colWhere;
        int maxNum = colWhere[0];
        for (int i = 1; i < colWhere.length; i++) {
            maxNum = colWhere[i] > maxNum ? colWhere[i] : maxNum;
        }
        colResult.maxCol = maxNum;

        return colResult;
    }

    //找出有用数据所在列的位置
    public int[] getColWhere(String table) {
        String lines[] = table.split("\n");

        int[] value_place = new int[10];
        int sum = 0;
        //找到每行数据中是数字的那一列，保存在value_place中
        for (int j = 1; j < lines.length; j++) {
            String[] contents = lines[j].split(TableSniffer.ELEMENT_DIVIDOR, -1);
            String regex = "[0-9]{0,}[,]?[0-9]{0,}[,]?[0-9]{0,}[,]?[0-9]{3,}[/.]?[0-9]{0,}";
            for (int i = 0; i < contents.length; i++) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(contents[i]);
                boolean b = matcher.matches();
                if (b && i < contents.length) {
                    value_place[sum++] = i;
                    if (sum > 9) {
                        break;
                    }
                }
            }
            if (sum > 9) {
                break;
            }
        }

        ArrayList al_flag = new ArrayList();
        ArrayList al_result = new ArrayList();

        al_flag.add(value_place[0]);
        al_result.add(value_place[0]);

        //找到value_place中数字不同的数，并将他们保存在al_result中，这些数就是有用数据在contents中的列数
        for (int i = 0; i < value_place.length; i++) {
            boolean flag = true;
            for (int j = 0; j < al_flag.size(); j++) {
                if (value_place[i] == (int) al_flag.get(j))
                    flag = false;
            }
            if (flag) {
                al_result.add(value_place[i]);
                al_flag.add(value_place[i]);
            }
        }

        int[] result = new int[al_result.size() + 1];
        result[0] = al_result.size();
        for (int i = 0; i < al_result.size(); i++) {
            result[i + 1] = (int) al_result.get(i);
        }
        return result;
    }

    @Override
    public String[] generateEntityJson(String content) {
        String lines[] = content.split("\n");

        if (lines != null && lines.length > 0) {
            Object data1 = null;
            Object data2 = null;
            try {
                data1 = ConsolidatedProfits.class.newInstance();
                data2 = ConsolidatedProfits.class.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (data1 == null || data2 == null) {
                return null;
            }

            Field[] declaredFields = data1.getClass().getDeclaredFields();
            List<Field> fields = new ArrayList<>();

            Collections.addAll(fields, declaredFields);

            List<String> needKickoutLines = new ArrayList<>();

            ColResult result = getColCnt(content, ConsolidatedProfits.class);

            int colCnt = result.colCnt;//数据中有用数据的个数

            if (result.isList && colCnt < 2) {
                return null;
            }
            if (colCnt != 4) {
                return null;
            }

            for (String line : lines) {
                String[] contents = line.split(TableSniffer.ELEMENT_DIVIDOR, -1);

                if (contents.length < result.maxCol + 1) {
                    continue;
                }

                if (contents != null && colCnt > 0) {

                    try {
                        Field needKickoutField = null;
                        boolean found = false;
                        for (Field field : fields) {
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
                                        List<String> datas1 = new ArrayList<>();
                                        List<String> datas2 = new ArrayList<>();
                                        //当colCnt>1的时候说明，表中需要解析的数据不止一列
                                        if (colCnt > 1) {
                                            //List<String> datas = new ArrayList<>();
                                            datas1.add(contents[result.where[0]].replace(" ", ""));
                                            datas1.add(contents[result.where[2]].replace(" ", ""));

                                            datas2.add(contents[result.where[1]].replace(" ", ""));
                                            datas2.add(contents[result.where[3]].replace(" ", ""));
                                            data1.getClass().getDeclaredField(field.getName()).set(data1, datas1);
                                            data2.getClass().getDeclaredField(field.getName()).set(data2, datas2);
                                        }
                                        needKickoutField = field;
                                        found = true;
                                        break;
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
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (AnnotationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 匹配过的行去掉
            for (String deleteStr : needKickoutLines) {
                content = content.replace(deleteStr + "\n", "");
            }

            String[] res = new String[3];
            res[0] = JSON.toJSONString(data1);
            res[1] = JSON.toJSONString(data2);
            res[2] = content;

            return res;
        }

        return null;
    }

    private static final String[] PFSKeyWords = {
            "利息收入",
            "已赚保费",
            "手续费及佣金收入",
            "提取保险合同准备金净额",
            "保单红利支出",
            "财务费用",
            "资产减值损失",
            "加：营业外收入",
            "减：所得税费用"
    };
}

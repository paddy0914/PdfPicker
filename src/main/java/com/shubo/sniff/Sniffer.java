package com.shubo.sniff;

import com.alibaba.fastjson.JSON;
import com.shubo.annotation.Horseman;
import com.shubo.entity.Nrgal;
import com.shubo.util.Similarity;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by horseman on 2016/11/22.
 */
abstract public class Sniffer {
    public abstract String getKey();

    public abstract String getSuffix();

    public abstract String getFolder();

    public abstract boolean sniff(String content);

    public abstract boolean sniffByKeywords(String content);

    // 从content中获取JSON,保存在返回数组的第一个String
    // 把content去除匹配成功的字符，保存在返回数组的第二个String
    public abstract String[] generateEntityJson(String content);

    public Logger logger = LoggerFactory.getLogger(Sniffer.class);

    /*
     * 把处理过后的表格内容识别为对应实体
     * 并作为json返回
     */
    public String[] generateEntityJson(String content, Class clazz) {
        String lines[] = content.split("\n");

        if (lines != null && lines.length > 0) {
            Object data = null;
            try {
                data = clazz.newInstance();
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
                String[] contents = line.split(TableSniffer.ELEMENT_DIVIDOR);
                if (contents != null && contents.length > 1) {

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
                                        float similarity = Similarity.getSimilarityRatio(key, contents[0].replace(" ", ""));
                                        if (similarity > 0.9f) {
                                            found = true;
                                        }
                                    } else {
                                        if (key.equals(contents[0])) {
                                            found = true;
                                        }
                                    }
                                    if (found) {
                                        data.getClass().getDeclaredField(field.getName()).set(data, contents[1].replace(" ", ""));
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
                            if (this instanceof NrgalSniffer && !contents[0].equals("")) {
//                            logger.info("{} [{}] [{}]", this.getClass().getName(), contents[0], contents[1]);
//                                filteredPrint(contents[0]);
                                String name = "D:\\年报解析\\test\\";
                                FileUtils.write(new File(name + contents[0]), "", false);
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
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

            System.out.println();

            String[] res = new String[2];
            res[0] = JSON.toJSONString(data);
            res[1] = content;

            return res;
        }

        return null;
    }

    public void filteredPrint(String str) {
        str = str.replace(" ", "");
        if (!str.equals("项目") && !str.equals("非经常性损益项目") && !str.equals("非流动性资产处置损益")
                && !str.equals("或有事项产生的损失") && !str.equals("除上述各项以外的其他营业外收入和支出")
                && !str.equals("以上调整对所得税的影响") && !str.equals("少数股东承担部分") && !str.equals("合计")
                && !str.equals("或有事项产生的损益") && !str.equals("单独进行减值测试的应收款项减值准备转回")) {
            logger.info("[{}]", str);
        }
    }
    /*
     * 把处理过后的表格内容识别为对应实体
     * 并作为json返回
     */
    public String[] generateEntityJsonArrays(String content, Class clazz) {

        HeaderInfo info = sniffHeader(content, clazz);
        List<String> fieldNames = info.headers;
        List<Object> datas =  new ArrayList<>();
        List<String> needKickoutLines = new ArrayList<>();
        if (fieldNames != null && fieldNames.size() > 0) {

            int itemCnt = fieldNames.size();

            String lines[] = content.split("\n");
            if (lines != null && lines.length > 0) {
                for (int i = 0; i < lines.length; i ++) {
                    String line = lines[i];
                    // 排除自身！
                    if (info.headerLineNumber != i) {
                        String[] items = line.split(TableSniffer.ELEMENT_DIVIDOR);
                        if (items.length == itemCnt) {
                            try {
                                Object data = clazz.newInstance();
                                for (int k = 0; k < fieldNames.size(); k ++) {
                                    String field = fieldNames.get(k);
                                    if (!field.equals("")) {
                                        data.getClass().getDeclaredField(field).set(data, items[k].replace(" ", ""));
                                    }
                                }
                                datas.add(data);
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                            needKickoutLines.add(line);
                        } else {
                            //System.out.println("怎么回事?");
                        }
                    }
                }
            }
        }

        // 匹配过的行去掉
        for (String deleteStr : needKickoutLines) {
            content = content.replace(deleteStr + "\n", "");
        }

        String[] res = new String[2];

        res[0] = JSON.toJSONString(datas);
        res[1] = content;

        return res;
    }

    private static boolean contains(String content, List<String> headers) {
        int match = 0;
        for (String header : headers) {
            if (content.contains(header)) {
                if (++ match > 2) {
                    return true;
                }
            }
        }

        return false;
    }

    public static class HeaderInfo {
        // 所有的域
        public List<String> headers;

        //标识第几行是头
        public int headerLineNumber;
    }

    private HeaderInfo sniffHeader(String content, Class clazz) {
        HeaderInfo headerInfo = new HeaderInfo();
        List<String> headers = new ArrayList<>();
        headerInfo.headers = headers;

        String lines[] = content.split("\n");
        String header = null;

        if (lines != null && lines.length > 0) {
            for (int k = 0; k < lines.length; k ++) {
                String line = lines[k];
                if (sniffByKeywords(line)) {
                    header = line;
                    headerInfo.headerLineNumber = k;
                    break;
                }
            }
        }

        if (header == null) {
            return headerInfo;
        }
        Field[] fields = clazz.getDeclaredFields();
        String items[] = header.split(TableSniffer.ELEMENT_DIVIDOR);

        for (String item : items) {
            item = item.replace(" ", "");
            boolean found = false;
            String fieldName = "";
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                Horseman horsemen = (Horseman) annotations[0];
                for (String key : horsemen.keys()) {
                    if (key.equals(item)) {
                        found = true;
                        fieldName = field.getName();
                        break;
                    }
                }
            }
            if (found) {
                headers.add(fieldName);
            } else {
                System.out.println("**************************************************** " + item);
                headers.add("");
            }
        }

        return headerInfo;
    }
}

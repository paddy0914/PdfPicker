package com.shubo.sniff;

import com.alibaba.fastjson.JSON;
import com.shubo.AppContext;
import com.shubo.annotation.Horseman;
import com.shubo.annotation.Todd;
import com.shubo.entity.report.ConsolidatedBalanceSheet;
import com.shubo.entity.report.ConsolidatedCashFlow;
import com.shubo.entity.report.ConsolidatedEquityChange;
import com.shubo.entity.report.ConsolidatedProfits;
import com.shubo.exception.AnnotationException;
import com.shubo.sniff.report.*;
import com.shubo.util.SimilarityUtils;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by horseman on 2016/11/22.
 */
abstract public class Sniffer {
    public int getIndex() throws AnnotationException {
        Annotation[] annotations = getClass().getAnnotations();
        if (annotations != null && annotations.length > 0) {
            Annotation annotation = annotations[0];
            Todd todd = (Todd) annotation;

            return todd.index();
        }

        throw new AnnotationException();
    }

    public String getKey() throws AnnotationException {
        Annotation[] annotations = getClass().getAnnotations();
        if (annotations != null && annotations.length > 0) {
            Annotation annotation = annotations[0];
            Todd todd = (Todd) annotation;

            return todd.key();
        }

        throw new AnnotationException();
    }

    public String getSuffix() throws AnnotationException {
        Annotation[] annotations = getClass().getAnnotations();
        if (annotations != null && annotations.length > 0) {
            Annotation annotation = annotations[0];
            Todd todd = (Todd) annotation;

            return todd.suffix();
        }

        throw new AnnotationException();
    }

    public String getFolder() throws AnnotationException {
        Annotation[] annotations = getClass().getAnnotations();
        if (annotations != null && annotations.length > 0) {
            Annotation annotation = annotations[0];
            Todd todd = (Todd) annotation;

            return todd.folder();
        }

        throw new AnnotationException();
    }

    public String[] getTitle() throws AnnotationException {
        Annotation[] annotations = getClass().getAnnotations();
        if (annotations != null && annotations.length > 0) {
            Annotation annotation = annotations[0];
            Todd todd = (Todd) annotation;

            return todd.title();
        }

        throw new AnnotationException();
    }

    public abstract boolean sniff(String content);

    /**
     * @param table
     * @return ColResult: 1,有用数据的列数; 2, 数据在哪几列；3，数据
     */
    public abstract ColResult getColCnt(String table, Class clazz);

    public boolean sniffWithTitle(List<String> titles) throws AnnotationException {
        String[] annotationTitles = getTitle();

        for (String sniffTitle : annotationTitles) {
            for (String title : titles) {
                if (title.contains(sniffTitle)) {
                    return true;
                }
            }
        }

        return false;
    }

    // 从content中获取JSON,保存在返回数组的第一个String
    // 把content去除匹配成功的字符，保存在返回数组的第二个String
    public abstract String[] generateEntityJson(String content) throws AnnotationException;

    public Logger logger = LoggerFactory.getLogger(Sniffer.class);

    /*
     * 通过内容是否包含某些关键字判定是不是那张表
     */
    public boolean sniffByKeywords(String content, String[] keyWords, int matchCount) {
        int match = 0;
        content = content.replace(" ", "");
        for (String keyword : keyWords) {
            if (content.contains(keyword)) {
                match++;
            }

            if (match == matchCount) {
                return true;
            }
        }

        return false;
    }

    public String[] generateEntityJson(String content, Class clazz) {
        return generateEntityJson(content, clazz, 1);
    }

    /*
     * 把处理过后的表格内容识别为对应实体,并作为json返回
     * @Param content : 待转换内容
     * @Param clazz : 实体
     * @Param colSpan : 每一行要转换多少参数
     */
    public String[] generateEntityJson(String content, Class clazz, int... colSpan) {
//        System.out.println(clazz);
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

            ColResult result = getColCnt(content, clazz);
            int colCnt = result.colCnt;//数据中有用数据的个数
            //也有是八大表的，但是只有一列数据有用
            /*
            if (result.isList && colCnt < 2) {
                return null;
            }
            */
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
                                        List<String> datas = new ArrayList<>();
                                        //当colCnt>1的时候说明，表中需要解析的数据不止一列
                                        if (result.isList) {
                                            //List<String> datas = new ArrayList<>();
                                            for (int k = 0; k < colCnt; k++) {
                                                if ((k + 1) < contents.length) {
                                                    if (result.where[k] < contents.length)//防止有的contents的size过小
                                                        datas.add(contents[result.where[k]].replace(" ", ""));
                                                    else
                                                        datas.add("");
                                                }

                                            }
                                            data.getClass().getDeclaredField(field.getName()).set(data, datas);
                                        } else {
                                            if (result.where[0] < contents.length) {
                                                data.getClass().getDeclaredField(field.getName()).set(data, contents[result.where[0]].replace(" ", ""));
                                            }
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
                                //contents[0]字段的内容与对应的实体类的属性的注解不能匹配的情况
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
                    } catch (IOException e) {
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
        List<Object> datas = new ArrayList<>();
        List<String> needKickoutLines = new ArrayList<>();
        if (fieldNames != null && fieldNames.size() > 0) {

            int itemCnt = fieldNames.size();

            String lines[] = content.split("\n");
            if (lines != null && lines.length > 0) {
                for (int i = 0; i < lines.length; i++) {
                    String line = lines[i];
                    // 排除自身！
                    if (info.headerLineNumber != i) {
                        String[] items = line.split(TableSniffer.ELEMENT_DIVIDOR, -1);
                        if (items.length == itemCnt) {
                            try {
                                Object data = clazz.newInstance();
                                for (int k = 0; k < fieldNames.size(); k++) {
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
                if (++match > 2) {
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

    public HeaderInfo sniffHeader(String content, Class clazz) {
        HeaderInfo headerInfo = new HeaderInfo();
        List<String> headers = new ArrayList<>();
        headerInfo.headers = headers;

        String lines[] = content.split("\n");
        String header = null;

        if (lines != null && lines.length > 0) {
            for (int k = 0; k < lines.length; k++) {
                String line = lines[k];
                if (sniff(line)) {
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
                if (found) {
                    break;
                }
            }
            if (found) {
                headers.add(fieldName);
            } else {
                //System.out.println("**************************************************** " + item);
                headers.add("");
            }
        }

        return headerInfo;
    }

    /*
     * 针对财务报表四大表中的特殊情况作判断
     */
    public static boolean isReportEntity(Class clazz) {
        if (clazz == ConsolidatedBalanceSheet.class ||
                clazz == ConsolidatedCashFlow.class ||
                clazz == ConsolidatedProfits.class ||
                clazz == ConsolidatedEquityChange.class) {
            return true;
        }

        return false;
    }
    //是否是八大表的Sniffer
    public static boolean isReportSniffer(Class clazz) {
        if (clazz == ConsolidatedBalanceShellSniffer.class ||
                clazz == ConsolidatedCashFlowSniffer.class ||
                clazz == ConsolidatedProfitsSniffer.class ||
                clazz == ConsolidatedEquityChangeSniffer.class ||
                clazz == ParentBalanceShellSniffer.class ||
                clazz == ParentCashFlowSniffer.class ||
                clazz == ParentProfitsSniffer.class ||
                clazz == ParentEquityChangeSniffer.class) {
            return true;
        }

        return false;
    }
    //解析表格的情况
    public static class ColResult {
        //有用数据有几列
        public int colCnt;
        //有用数据在那些列
        public int[] where;
        //是否是八大表，八大表的属性对应的是List<String>，其他的表属性对应的是String
        public boolean isList;
        //最大的列数（where中最大的值）
        public int maxCol;
    }
}

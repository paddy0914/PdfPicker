package com.shubo.sniff.report;

import com.alibaba.fastjson.JSON;
import com.shubo.annotation.Horseman;
import com.shubo.annotation.Todd;
import com.shubo.entity.FiveCustomerInformation;
import com.shubo.exception.AnnotationException;
import com.shubo.sniff.Sniffer;
import com.shubo.sniff.TableSniffer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by liujinping on 2016/12/26.
 */
@Todd(key = "FiveCustomerInfo",
        index = 17,
        suffix = ".fiveCustomerInfo",
        folder = "公司前5大客户资料")
public class FiveCustomerInfoSniffer extends Sniffer{

    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content, FiveCustomerInfoKeyWords, MATCH_CNT);
    }

    @Override
    public ColResult getColCnt(String table, Class clazz) {
        ColResult colResult = new ColResult();
        int result[] = null;
        int[] colWhere = null;
        if (Sniffer.isReportEntity(clazz)) {
            colResult.isList = true;
        } else {
            colResult.isList = false;
        }
        result = getColWhere(table, clazz);
        colResult.colCnt = result.length;
        colWhere = new int[result.length];
        for (int i = 0; i < result.length; i++) {
            colWhere[i] = result[i];
        }
        colResult.where = colWhere;
        int maxNum = colWhere[0];
        for (int i = 1; i < colWhere.length; i++) {
            maxNum = colWhere[i] > maxNum ? colWhere[i] : maxNum;
        }
        colResult.maxCol = maxNum;

        return colResult;
    }

    public int[] getColWhere(String table, Class clazz) {
        String lines[] = table.split("\n");
        String[] tableStructures = lines[0].split(TableSniffer.ELEMENT_DIVIDOR, -1);

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

        int[] result = new int[fields.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = -1;
        }
        int index = 0;
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            if (annotations.length > 0) {
                Horseman horsemen = (Horseman) annotations[0];
                String[] keys = horsemen.keys();
                for (String key : keys) {
                    boolean flag = false;
                    for (int i = 0; i < tableStructures.length; i++) {
                        if (tableStructures[i] != "") {
                            if (key.equals(tableStructures[i].replace(" ", ""))) {
                                result[index] = i;
                                flag = true;
                                break;
                            }
                        }
                    }
                    if (flag) {
                        break;
                    }
                }
            }
            index++;
        }
        return result;
    }

    @Override
    public String[] generateEntityJson(String content) throws AnnotationException {
        return generateEntityJson(content, FiveCustomerInformation.class);
    }

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

            List<FiveCustomerInformation> listEmployees = new ArrayList<>();
            ColResult result = getColCnt(content, FiveCustomerInformation.class);

            Field[] declaredFields = data.getClass().getDeclaredFields();
            List<Field> fields = new ArrayList<>();

            Collections.addAll(fields, declaredFields);
            for (int i = 1; i < lines.length; i++) {
                FiveCustomerInformation fiveCustomerInformation = new FiveCustomerInformation();
                String[] contents = lines[i].split(TableSniffer.ELEMENT_DIVIDOR, -1);

                if (contents.length - 1 < result.maxCol) {
                    continue;
                }
                if (result.where != null) {
                    try {
                        for (int j = 0; j < fields.size(); j++) {
                            if (result.where[j] != -1) {
                                fiveCustomerInformation.getClass().getDeclaredField(fields.get(j).getName()).set(fiveCustomerInformation, contents[result.where[j]].replace(" ", ""));
                            } else {
                                fiveCustomerInformation.getClass().getDeclaredField(fields.get(j).getName()).set(fiveCustomerInformation, "");
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    listEmployees.add(fiveCustomerInformation);
                } else {

                }

            }

            String[] res = new String[2];
            res[0] = JSON.toJSONString(listEmployees);
            res[1] = content;

            return res;

        }
        return null;
    }

    private static final int MATCH_CNT = 4;

    private static final String[] FiveCustomerInfoKeyWords = {
            "序号",
            "客户名称",
            "销售额（元）",
            "占年度销售总额比例",
    };
}

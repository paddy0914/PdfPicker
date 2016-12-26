package com.shubo.sniff;

import com.alibaba.fastjson.JSON;
import com.shubo.annotation.Horseman;
import com.shubo.annotation.Todd;
import com.shubo.entity.GuarantySituation;
import com.shubo.entity.MainSubcompany;
import com.shubo.exception.AnnotationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by liujinping on 2016/12/26.
 */
@Todd(key = "GuarantySituation",
        index = 19,
        suffix = ".guarantySituation",
        folder = "担保情况")
public class GuarantySituationSniffer extends Sniffer {

    public static int startPlace = 0;
    //public static int continuePlace = 0;

    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content, GuarantySituationKeyWords, MATCH_CNT);
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
        String[] temp = lines[1].split(TableSniffer.ELEMENT_DIVIDOR, -1);
        if (tableStructures.length / (double) temp.length < 0.5) {
            tableStructures = temp;
            startPlace = 2;
        }

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

    /**
     *  是否是表头
     * @param contents
     * @return  flag
     */
    public Boolean isContainHead(String[] contents) {
        Boolean flag = false;
        for (int i = 0; i < contents.length; i++) {
            if (contents[i].equals(GuarantySituationKeyWords[i])) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public String[] generateEntityJson(String content) throws AnnotationException {
        return generateEntityJson(content, GuarantySituation.class);
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

            List<GuarantySituation> listGuarantySituation = new ArrayList<>();
            ColResult result = getColCnt(content, GuarantySituation.class);

            Field[] declaredFields = data.getClass().getDeclaredFields();
            List<Field> fields = new ArrayList<>();

            Collections.addAll(fields, declaredFields);
            for (int i = startPlace; i < lines.length; i++) {
                GuarantySituation guarantySituation = new GuarantySituation();
                String[] contents = lines[i].split(TableSniffer.ELEMENT_DIVIDOR, -1);

                if (contents.length - 1 < result.maxCol) {
                    continue;
                }
                if(isContainHead(contents)){
                    continue;
                }
                if (result.where != null) {
                    try {
                        for (int j = 0; j < fields.size(); j++) {
                            if (result.where[j] != -1) {
                                guarantySituation.getClass().getDeclaredField(fields.get(j).getName()).set(guarantySituation, contents[result.where[j]].replace(" ", ""));
                            } else {
                                guarantySituation.getClass().getDeclaredField(fields.get(j).getName()).set(guarantySituation, "");
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }

                    listGuarantySituation.add(guarantySituation);
                } else {

                }

            }

            String[] res = new String[2];
            res[0] = JSON.toJSONString(listGuarantySituation);
            res[1] = content;

            return res;

        }
        return null;
    }

    private static final int MATCH_CNT = 8;

    private static final String[] GuarantySituationKeyWords = {
            "担保对象名称",
            "担保额度相关公告披露日期",
            "担保额度",
            "实际发生日期（协议签署日）",
            "实际担保金额",
            "担保类型",
            "担保期",
            "是否履行完毕",
            "是否为关联方担保",
    };
}

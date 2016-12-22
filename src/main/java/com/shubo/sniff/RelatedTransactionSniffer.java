package com.shubo.sniff;

import com.alibaba.fastjson.JSON;
import com.shubo.AppContext;
import com.shubo.annotation.Horseman;
import com.shubo.annotation.Todd;
import com.shubo.entity.RelatedTransaction;
import com.shubo.exception.AnnotationException;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by horseman on 2016/12/20.
 */
@Todd(key = "RelatedTransaction",
        index = 13,
        suffix = ".rt",
        folder = "关联交易",
        title = {"与日常经营相关的关联交易"})
public class RelatedTransactionSniffer extends Sniffer {
    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content, RelatedTransactionKeyWords, MATCH_CNT);
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
        return generateEntityJson(content, RelatedTransaction.class);
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

            List<RelatedTransaction> listRelatedTransactions = new ArrayList<>();
            ColResult result = getColCnt(content, RelatedTransaction.class);

            Field[] declaredFields = data.getClass().getDeclaredFields();
            List<Field> fields = new ArrayList<>();

            Collections.addAll(fields, declaredFields);
            for (int i = 1; i < lines.length; i++) {
                RelatedTransaction relatedTransaction = new RelatedTransaction();
                String[] contents = lines[i].split(TableSniffer.ELEMENT_DIVIDOR, -1);
                if (contents.length < result.where.length || result.colCnt > contents.length - 1) {
                    continue;
                }
                if (result.where != null) {
                    try {
                        for (int j = 0; j < fields.size(); j++) {
                            if (result.where[j] != -1) {
                                relatedTransaction.getClass().getDeclaredField(fields.get(j).getName()).set(relatedTransaction, contents[result.where[j]].replace(" ", ""));
                            } else {
                                relatedTransaction.getClass().getDeclaredField(fields.get(j).getName()).set(relatedTransaction, "");
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                    listRelatedTransactions.add(relatedTransaction);
                } else {

                }

            }

            String[] res = new String[2];
            res[0] = JSON.toJSONString(listRelatedTransactions);
            res[1] = content;

            return res;

        }
        return null;
    }

    private static final int MATCH_CNT = 4;

    private static final String[] RelatedTransactionKeyWords = {
            "关联交易内容",
            "关联交易定价原则",
            "关联交易价格",
            "关联交易结算方式",
            "关联关系方",
            "关联交易类型",
    };
}

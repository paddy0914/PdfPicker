package com.shubo.sniff;

import com.alibaba.fastjson.JSON;
import com.shubo.annotation.Horseman;
import com.shubo.entity.FinanceData;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by horseman on 2016/11/22.
 */
public class FinanceSniffer extends Sniffer {
    Logger logger = LoggerFactory.getLogger(FinanceSniffer.class);
    public String getKey() {
        return "Finance";
    }

    public String getSuffix() {
        return ".table1";
    }

    public String getFolder() {
        return "主要财务数据";
    }

    /*
     * 根据一定的规则探测文档是否包含主要财务数据
     *
     */
    public boolean sniff(String content) {
        if (sniffByKeywords(content)) {
            return true;
        }

        return false;
    }

    /*
     * 把处理过后的表格内容识别为对应实体
     * 并作为json返回
     */
    public String generateEntityJson(String content) {
        String lines[] = content.split("\n");
        if (lines != null && lines.length > 0) {

            FinanceData data = new FinanceData();

            Field[] declaredFields = data.getClass().getDeclaredFields();
            List<Field> fields = new ArrayList<Field>();

            Collections.addAll(fields, declaredFields);

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
                                    if (key.equals(contents[0].replace(" ", ""))) {
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
                        } else{
                            logger.info("[{}] [{}]", contents[0], contents[1]);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }

//                    logger.info("[{}] [{}]", contents[0], contents[1]);
                }
            }

            System.out.println();
            return JSON.toJSONString(data);
        }

        return "";
    }

    private static final int MATCH_RULE = 3;

    private static boolean sniffByKeywords(String content) {
        int match = 0;
        for (String keyword : financeDataKeyWords) {
            if (content.contains(keyword)) {
                match++;
            }

            if (match == MATCH_RULE) {
                return true;
            }
        }

        return false;
    }

    private static final String[] financeDataKeyWords = {
            "基本每股收益",
            "稀释每股收益",
            "经营活动产生的现金流量净额",
            "营业收入",
            "加权平均净资产收益",
            "归属于上市公司股东的净利润",
    };

}

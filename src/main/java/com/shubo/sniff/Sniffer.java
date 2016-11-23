package com.shubo.sniff;

import com.alibaba.fastjson.JSON;
import com.shubo.annotation.Horseman;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            List<Field> fields = new ArrayList<Field>();

            Collections.addAll(fields, declaredFields);

            List<String> needKickoutLines = new ArrayList<String>();
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
                            needKickoutLines.add(line);
                        } else{
                            logger.info("{} [{}] [{}]", this.getClass().getName(), contents[0], contents[1]);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
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
}

package com.shubo.util;

import com.shubo.entity.YearReportDes;

/**
 * Created by horseman on 2016/11/22.
 */
public class NameUtils {
    public static String getFileNameByFileName(String fileName) {
        YearReportDes des = getDes(fileName);

        return des.getYear() + "-" + des.getType() + "-" + des.getCompanyName() + ".table";
    }

    public static YearReportDes getDes(String fileName) {
        int type;
        String typeStr;

        int index;

        typeStr = "第一季度";
        type = 1;
        index = fileName.indexOf(typeStr);

        if (index == -1) {
            typeStr = "半年度";
            type = 2;
            index = fileName.indexOf(typeStr);
        }
        if (index == -1) {
            typeStr = "第三季度";
            type = 3;
            index = fileName.indexOf(typeStr);
        }
        if (index == -1) {
            typeStr = "年度";
            type = 4;
            index = fileName.indexOf(typeStr);
        }

        if (index == -1) {
            return null;
        }

        String prefix = fileName.substring(0, index);

        if (prefix.endsWith("年")) {
            String year = prefix.substring(prefix.length() - 5, prefix.length() - 1);
            String companyName = prefix.substring(0, prefix.length() - 5);

            return new YearReportDes(companyName, year, type);
        }

        return null;
    }
}

package com.shubo.stastics;


import com.shubo.AppContext;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 文件解析情况的结果
 * Created by liujinping on 2016/12/9.
 */
public class AnalyticalResult {
    //文件名
    public static String filename;
    /*
    * 0,合并资产负债表
    * 1,合并现金流表
    * 2,合并股东权益变动表
    * 3,合并现金流表
    * 4,母公司资产负债表
    * 5,母公司现金流表
    * 6,母公司股东权益变动表
    * 7,母公司利润表
    *
    * 8,主要财务数据
    * 9,现金流量表
    * 10,非经常性损益
    * 11,前10名无限售条件股东持股情况
    * 12,前10名股东持股情况
     */
    public static String[] results = new String[13];

    static {
        try {
            String initStr = "文件名,"
                            +"合并资产负债表,合并利润表,合并所有者权益变动表,合并利润表,"
                            + "母公司资产负债表,母公司现金流量表,母公司所有者权益变动表,母公司利润表,"
                            + "主要财务数据,现金流量表,非经常性损益,前10名无限售条件股东持股情况,前10名股东持股情况"
                            + "\n";
            FileUtils.write(new File(AppContext.rootFolder + File.separator + "error.csv"), initStr, false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void reset() {
        filename = "";

        for (int i = 0; i < 13; i ++) {
            results[i] = "未识别";
        }
    }

    public static void writeToCsv() {
        try {
            String str = filename;
            for (String s : results) {
                str += "," + s;
            }
            str += "\n";
            FileUtils.write(new File(AppContext.rootFolder + File.separator + "error.csv"), str, true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reset();
        }
    }
}

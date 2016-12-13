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

    public static String[] results = new String[8];

    //合并资产负债表
    public static String consolidatedBalanceSheetTable;

    //合并现金流表
    public static String consolidatedCashFlowTable;

    //合并股东权益变动表
    public static String consolidatedEquityChangeTable;

    //合并现金流表
    public static String consolidatedProfitsTable;

    //母公司资产负债表
    public static String parentBalanceSheetTable;

    //母公司现金流表
    public static String parentCashFlowTable;

    //母公司股东权益变动表
    public static String parentEquityEquityChangeTable;

    //母公司利润表
    public static String parentProfitsTable;

    static {
        try {
            String initStr = "文件名,"
                            +"合并资产负债表,合并利润表,合并所有者权益变动表,合并利润表,"
                            + "母公司资产负债表,母公司现金流量表,母公司所有者权益变动表,母公司利润表"
                            + "\n";
            FileUtils.write(new File(AppContext.rootFolder + File.separator + "error.csv"), initStr, false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void reset() {
        filename = "";

        consolidatedBalanceSheetTable = "";
        consolidatedCashFlowTable = "";
        consolidatedEquityChangeTable = "";
        consolidatedProfitsTable = "";

        parentBalanceSheetTable = "";
        parentCashFlowTable = "";
        parentEquityEquityChangeTable = "";
        parentProfitsTable = "";

        for (int i = 0; i < 8; i ++) {
            results[i] = "二条";
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

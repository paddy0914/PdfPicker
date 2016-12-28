package com.shubo;

import java.io.File;

/**
 * Created by horseman on 2016/11/25.
 */
public class AppContext {

    public static final String TABLE_OUTPUT_DIR = "表格";
    //public static final String JSON_OUTPUT_DIR = "JSON";
    public static final String JSON_OUTPUT_DIR = "JSONtest";//测试用
    public static final String YEAR_REPORT_FOLDER = "年度报告";

    public static final String MATCH_FAILD_FOLDER = "字段匹配失败";

    public static void setRootFolder(String folder) {
        rootFolder = folder;
        srcFolder = rootFolder + File.separator + "html";
        yearReportFolder = rootFolder + File.separator + YEAR_REPORT_FOLDER;
        matchFaildFolder = rootFolder + File.separator + MATCH_FAILD_FOLDER;
    }

    //public static String rootFolder = "e:\\年报解析2";
    public static String rootFolder = "e:\\年报解析";
    public static String srcFolder = rootFolder + File.separator + "html";
    //public static String srcFolder = rootFolder + File.separator + "年报";//测试用
    public static String yearReportFolder = rootFolder + File.separator + YEAR_REPORT_FOLDER;
    public static String matchFaildFolder = rootFolder + File.separator + MATCH_FAILD_FOLDER;
}

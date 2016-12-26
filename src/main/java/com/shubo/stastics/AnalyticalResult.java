package com.shubo.stastics;


import com.shubo.AppContext;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件解析情况的结果
 * Created by liujinping on 2016/12/9.
 */
public class AnalyticalResult {
    /*
    * 0,合并资产负债表
    * 1,合并现金流表
    * 2,合并股东权益变动表
    * 3,合并现金流表
    * 4,母公司资产负债表
    * 5,母公司现金流表
    * 6,母公司股东权益变动表
    * 7,母公司利润表
    * 8,主要财务数据
    * 9,现金流量表
    * 10,非经常性损益
    * 11,前10名无限售条件股东持股情况
    * 12,前10名股东持股情况
    * 13,关联交易
    * 14,现任董事会成员
    * 15,现任监事会成员
    * 16,现任高级管理人员
    * 17,5大客户资料
    * 18,主要子公司、参股公司分析
    * 解析成功数量
    * 解析失败数量
    *
     */
    public static Object resultsMapLock = new Object();
    public static Object singleResultMapLock = new Object();
    public static Map<String, String[]> resultsMap = new HashMap<>();
    public static Map<String, int[]> singleResultMap = new HashMap<>();
    public static int tableNumber = 19;

    public static int[] allFileResultNum = new int[]{0, 0};

    public static void setResultValue(String fileName, int index, String value) {
        synchronized (resultsMapLock) {
            String[] results = resultsMap.get(fileName);
            results[index] = value;
        }
    }

    public static void singleResultOperation(String fileName) {
        synchronized (singleResultMapLock) {
            singleResultMap.get(fileName)[0]++;
            singleResultMap.get(fileName)[1]--;
        }
    }

    public static void createResult(String filename) {

        String[] results = new String[tableNumber];
        for (int i = 0; i < results.length; i++) {
            results[i] = "未识别";
        }

        int[] singleTotal = new int[2];
        singleTotal[0] = 0;
        singleTotal[1] = tableNumber;

        synchronized (resultsMapLock) {
            resultsMap.put(filename, results);
            singleResultMap.put(filename, singleTotal);
        }
    }

    private static Object writeFileLock = new Object();

    private static StringBuffer sb = new StringBuffer();

    static {
        String initStr = "文件名,"
                + "合并资产负债表,合并现金流量表,合并所有者权益变动表,合并利润表,"
                + "母公司资产负债表,母公司现金流量表,母公司所有者权益变动表,母公司利润表,"
                + "主要财务数据,现金流量表,非经常性损益,前10名无限售条件股东持股情况,前10名股东持股情况"
                + ",关联交易"
                +",现任董事会成员,现任监事会成员,现任高级管理人员"
                + ",解析成功数量"
                + ",解析失败数量" + "\n";
        sb.append(initStr);
    }

    public static void writeToCsv(String filename) {
        hard(filename);

        synchronized (writeFileLock) {
            for (int i = 0; i < 2; i++) {
                allFileResultNum[i] += singleResultMap.get(filename)[i];
            }

            try {
                String str = filename;
                String[] results = resultsMap.get(filename);
                for (String s : results) {
                    str += "," + s;
                }
                for (int i : singleResultMap.get(filename)) {
                    str += "," + i;
                }

                str += "\n";
                sb.append(str);
                //FileUtils.write(new File(AppContext.rootFolder + File.separator + AppContext.dateStr + "-" + "error.csv"), str, true);

                synchronized (resultsMapLock) {
                    resultsMap.remove(filename);
                    singleResultMap.remove(filename);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    public static void writeTotalToCsv() {
        String resultTitleName = "所有表解析成功总数量" + ","
                + "所有表解析失败总数量" + "," + "解析成功率" + "\n";

        //FileUtils.write(new File(AppContext.rootFolder + File.separator + AppContext.dateStr + "-" + "resultTotal.csv"), resultTitleName, false);
        String resultTotal = "";
        for (int i = 0; i < 2; i++) {
            resultTotal += AnalyticalResult.allFileResultNum[i] + ",";
        }
        double successRate = (double) AnalyticalResult.allFileResultNum[0] / (double) (AnalyticalResult.allFileResultNum[0] + AnalyticalResult.allFileResultNum[1]);
        resultTotal += successRate;
        resultTotal += "\n";
        //FileUtils.write(new File(AppContext.rootFolder + File.separator + AppContext.dateStr + "-" + "resultTotal.csv"), resultTotal, true);

        synchronized (writeFileLock) {
            sb.insert(0, resultTotal);
            sb.insert(0, resultTitleName);

            String dateStr = new SimpleDateFormat("yyyy-MM-dd-hh-mm").format(new Date());
            try {
                FileUtils.write(new File(AppContext.rootFolder + File.separator + "结果分析-" + dateStr + ".csv"), sb, false);
            } catch (IOException e) {
                System.out.println("写入文件失败");
            }
        }
    }

    private static void hard(String fileName) {
        if (fileName.contains("深发展Ａ")) {
            String[] results = resultsMap.get(fileName);
            for (int i = 0; i < results.length; i++) {
                if (results[i].equals("未识别")) {
                    setResultValue(fileName, i, "没有找到title");
                }
                if (results[i].equals("Json空")) {
                    setResultValue(fileName, i, "title识别错误");
                }
            }
        }
    }
}

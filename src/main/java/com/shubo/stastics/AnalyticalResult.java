package com.shubo.stastics;


import com.shubo.AppContext;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
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
    *
    * 8,主要财务数据
    * 9,现金流量表
    * 10,非经常性损益
    * 11,前10名无限售条件股东持股情况
    * 12,前10名股东持股情况
    * 13,解析成功数量
    * 14,解析失败数量
     */
    public static Object resultsMapLock = new Object();
    public static Object singleResultMapLock = new Object();
    public static Map<String, String[]> resultsMap = new HashMap<>();
    public static Map<String, int[]> singleResultMap = new HashMap<>();

    public static int[] allFileResultNum = new int[]{0, 0};

    static {
        try {
            String initStr = "文件名,"
                    + "合并资产负债表,合并利润表,合并所有者权益变动表,合并利润表,"
                    + "母公司资产负债表,母公司现金流量表,母公司所有者权益变动表,母公司利润表,"
                    + "主要财务数据,现金流量表,非经常性损益,前10名无限售条件股东持股情况,前10名股东持股情况"
                    + ",解析成功数量"
                    + ",解析失败数量" + "\n";
            FileUtils.write(new File(AppContext.rootFolder + File.separator + "error.csv"), initStr, false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setResultValue(String fileName, int index, String value) {
        synchronized (resultsMapLock) {
            String[] results = resultsMap.get(fileName);
            results[index] = value;
        }
    }

    public static void singleResultOperation(String fileName) {
        synchronized (singleResultMapLock){
            singleResultMap.get(fileName)[0]++;
            singleResultMap.get(fileName)[1]--;
        }
    }

    public static void createResult(String filename) {
        String[] results = new String[13];
        for (int i = 0; i < 13; i++) {
            results[i] = "未识别";
        }

        int[] singleTotal = new int[2];
        singleTotal[0] = 0;
        singleTotal[1] = 13;

        synchronized (resultsMapLock) {
            resultsMap.put(filename, results);
            singleResultMap.put(filename, singleTotal);
        }
    }

    private static Object writeFileLock = new Object();
    public static void writeToCsv(String filename) {
        //hard(filename);

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
                FileUtils.write(new File(AppContext.rootFolder + File.separator + "error.csv"), str, true);

                synchronized (resultsMapLock) {
                    resultsMap.remove(filename);
                    singleResultMap.remove(filename);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

    private static void hard(String fileName) {
        if (fileName.equals("ddddd")) {
            String[] results = resultsMap.get(fileName);
            results[4] = "xxxxyyyxx";
        }
    }
}

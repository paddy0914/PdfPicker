package com.shubo;

import com.shubo.dispatcher.Dispatcher;
import com.shubo.stastics.AnalyticalResult;
import com.shubo.parser.PDF2TXT;
import com.shubo.sniff.TableSniffer;
import com.shubo.util.HorsemanUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import static com.shubo.parser.PDF2TXT.*;

/**
 * Created by horseman on 2016/11/28.
 */
public class ReportParser {
    public static void main(String args[]) {
        System.out.println("------开始处理-------");
        if (args.length > 0) {
            Dispatcher.setThreadCnt(Integer.valueOf(args[0]));
        }
        try {
            String srcYRReportFolder = AppContext.rootFolder + File.separator + "年报";
//            String srcYRReportFolder = "E://年报解析";

            File f = new File(AppContext.yearReportFolder);
            if (!f.exists()) {
                f.mkdir();
            }

            /* 年报解析/年报 */
            File folder = new File(srcYRReportFolder);

            for (File subFolder : folder.listFiles()) {
                /*
                if (Integer.valueOf(subFolder.getName()) != 5) {
                    continue;
                }
                */
                    /* subFolder : 000001 002625 */
                if (subFolder.isDirectory()) {
                    File yrFolder = new File(subFolder.getAbsolutePath() + File.separator + "年报");
                    if (yrFolder.exists() && yrFolder.isDirectory()) {
                        for (File file : yrFolder.listFiles()) {
                            if (file.getName().endsWith("html")
                                    && !file.getName().contains("英文版")
                                    && !file.getName().contains("摘要")
                                    && file.getName().contains("年度报告")
                                    && !file.getName().contains("半年度报告")) {

                                try {
                                    Dispatcher.startTaskSync(() -> {handle(file, subFolder.getName());});
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                String resultTitleName = "所有表解析成功总数量" + ","
                        + "所有表解析失败总数量" + "\n";
                FileUtils.write(new File(AppContext.rootFolder + File.separator + "resultTotal.csv"), resultTitleName, false);
                String resultTotal = "";
                for (int i = 0; i < 2; i++) {
                    resultTotal += AnalyticalResult.allFileResultNum[i] + ",";
                }
                resultTotal += "\n";
                FileUtils.write(new File(AppContext.rootFolder + File.separator + "resultTotal.csv"), resultTotal, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void handle(File file, String stockCode) {
        try {

            String needHandleFileName = stockCode + "-" + file.getName();

            AnalyticalResult.createResult(needHandleFileName);

            System.out.println("开始识别:" + needHandleFileName);
            String needHandleFilePath = AppContext.yearReportFolder + File.separator + needHandleFileName;

            FileUtils.copyFile(file, new File(needHandleFilePath));

            List<String> list = PDF2TXT.parsePDFStructure(needHandleFilePath);

                                    /* 存储除了八大表以外的其他表字符串 */
            List<String> otherEntityString = new ArrayList<>();

            // 记录已经获取过实体的类，防止重复获取
            List<String> capturedEntityKeys = new ArrayList<>();

            HorsemanUtils.createKeys(needHandleFileName);
            //keyQueue.
            for (String str : list) {
                if (str.startsWith(typeTitle + splitChar)) {
                    HorsemanUtils.saveText(needHandleFileName, str);
                } else if (str.startsWith(typeText + splitChar)) {
                    HorsemanUtils.saveText(needHandleFileName, str);
                } else if (str.startsWith(typeTitle)) {
                    HorsemanUtils.saveText(needHandleFileName, str);
                } else if (str.startsWith(typeText)) {
                    HorsemanUtils.saveText(needHandleFileName, str);
                } else if (str.startsWith(typeTable + splitChar)) {
                    if (!TableSniffer.sniffEntity(str, HorsemanUtils.getPossibleKeys(needHandleFileName), needHandleFileName, capturedEntityKeys)) {
                        otherEntityString.add(str);
                    }
                } else {
                                            /* what's this */
                }
            }

            // 其他
            TableSniffer.sniffEachEntity(otherEntityString, needHandleFileName);

            AnalyticalResult.writeToCsv(needHandleFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

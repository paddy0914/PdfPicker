package com.shubo;

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
        System.out.println("------");
        try {
            String srcYRReportFolder = AppContext.rootFolder + File.separator + "年报";
            //String srcYRReportFolder = AppContext.rootFolder + File.separator + "年报test";
            String yearReportFolder = AppContext.rootFolder + File.separator + AppContext.YEAR_REPORT_FOLDER;

            File f = new File(yearReportFolder);
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
                try {
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

                                    AnalyticalResult.reset();

                                    String needHandleFileName = subFolder.getName() + "-" + file.getName();
                                    AnalyticalResult.filename = needHandleFileName;

                                    System.out.println("start:" + needHandleFileName);
                                    String needHandleFilePath = yearReportFolder + File.separator + needHandleFileName;

                                    FileUtils.copyFile(file, new File(needHandleFilePath));

                                    List<String> list = PDF2TXT.parsePDFStructure(needHandleFilePath);

                                    /* 存储除了八大表以外的其他表字符串 */
                                    List<String> otherEntityString = new ArrayList<>();
                                    String title = "";

                                    // 记录已经获取过实体的类，防止重复获取
                                    List<String> capturedEntityKeys = new ArrayList<>();

                                    //keyQueue.
                                    for (String str : list) {
                                        if (str.startsWith(typeTitle + splitChar)) {
                                            HorsemanUtils.saveText(str);
                                        } else if (str.startsWith(typeText + splitChar)) {
                                            HorsemanUtils.saveText(str);
                                        } else if (str.startsWith(typeTitle)) {
                                            HorsemanUtils.saveText(str);
                                        } else if (str.startsWith(typeText)) {
                                            HorsemanUtils.saveText(str);
                                        } else if (str.startsWith(typeTable + splitChar)) {
                                            if (!TableSniffer.sniffEntity(str, HorsemanUtils.getPossibleKeys(), needHandleFileName, capturedEntityKeys)) {
                                                otherEntityString.add(str);
                                            }
                                        } else {
                                            /* what's this */
                                        }
                                    }

                                    // 其他
                                    TableSniffer.sniffEachEntity(otherEntityString, needHandleFileName);

                                    AnalyticalResult.writeToCsv();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.shubo;

import com.shubo.exception.AnnotationException;
import com.shubo.parser.PDF2TXT;
import com.shubo.sniff.TableSniffer;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.shubo.parser.PDF2TXT.splitChar;
import static com.shubo.parser.PDF2TXT.typeTable;
import static com.shubo.parser.PDF2TXT.typeTitle;

/**
 * Created by horseman on 2016/11/28.
 */
public class ReportParser {
    public static void main(String args[]) {
        System.out.println("------");
        try {
            String srcYRReportFolder = AppContext.rootFolder + File.separator + "年报";

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

                                    String needHandleFileName = subFolder.getName() + "-" + file.getName();
                                    String needHandleFilePath = yearReportFolder + File.separator + needHandleFileName;

                                    FileUtils.copyFile(file, new File(needHandleFilePath));

                                    List<String> list = PDF2TXT.parsePDFStructure(needHandleFilePath);

                                    /* 存储除了八大表以外的其他表字符串 */
                                    List<String> otherEntityString = new ArrayList<>();
                                    String title = "";
                                    /*
                                    //添加测试的代码
                                    int i=1;
                                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\年报解析\\list.txt", true)));
                                    for (String str : list) {
                                        bw.write(i+" "+str+"\r\n");
                                        bw.flush();
                                        i++;
                                    }
                                    bw.close();
                                    */
                                    //int j=0;
                                    // 记录已经获取过实体的类，防止重复获取
                                    List<String> capturedEntityKeys = new ArrayList<>();
                                    for (String str : list) {
                                        //System.out.println(j++);
                                        if (str.startsWith(typeTitle + splitChar)) {
                                            title = str;
                                        } else if (str.startsWith(typeTable + splitChar)) {
                                            if (!TableSniffer.sniffEntity(str, title, needHandleFileName, capturedEntityKeys)) {
                                                otherEntityString.add(str);
                                            }
                                        } else {
                                            /* what's this */
                                        }
                                    }

                                    // 其他
                                    TableSniffer.sniffEachEntity(otherEntityString, needHandleFileName);
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

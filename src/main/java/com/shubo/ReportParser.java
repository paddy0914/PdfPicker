package com.shubo;

import com.shubo.exception.AnnotationException;
import com.shubo.parser.PDF2TXT;
import com.shubo.sniff.TableSniffer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
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
                if (Integer.valueOf(subFolder.getName()) != 5) {
                    continue;
                }
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
                                    for (String str : list) {
                                        if (str.startsWith(typeTitle + splitChar)) {
                                            title = str;
                                        } else if (str.startsWith(typeTable + splitChar) ) {
                                            if(!TableSniffer.sniffEntity(str, title, needHandleFileName)) {
                                                otherEntityString.add(str);
                                            }
                                        } else {
                                            /* what's this */
                                        }
                                    }
                                    TableSniffer.sniffEachEntity(otherEntityString, needHandleFileName);
                                }
                            }
                        }
                    }
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {
        }
    }
}

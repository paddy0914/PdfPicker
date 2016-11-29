package com.shubo;

import com.shubo.exception.NameConvertException;
import com.shubo.sniff.TableSniffer;
import com.shubo.util.NameUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {

        if (args.length > 0) {
            AppContext.rootFolder = args[0];
        }
        String srcYRReportFolder = AppContext.rootFolder + File.separator + "年报";
        File folder = new File(srcYRReportFolder);

        for (File subFolder : folder.listFiles()) {
            if (Integer.valueOf(subFolder.getName()) != 5) {
                continue;
            }
            try {
                if (subFolder.isDirectory()) {
                    File yrFolder = new File(subFolder.getAbsolutePath() + File.separator + "年报");

                    if (yrFolder.exists() && yrFolder.isDirectory()) {
                        for (File file : yrFolder.listFiles()) {
                            if (file.getName().endsWith("html") && !file.getName().contains("英文版") && !file.getName().contains("摘要")) {
                                System.out.println("处理 " + file.getAbsolutePath());
                                String outputTableFile = AppContext.rootFolder +
                                        File.separator + AppContext.TABLE_OUTPUT_DIR +
                                        File.separator + NameUtils.getFileNameByFileName(file.getName());
                                TableSniffer.sniff(file, outputTableFile);
                                TableSniffer.sniffEachEntity(new File(outputTableFile));
                            }
                        }
                    }
                }
            } catch (NameConvertException e) {
                System.out.println("NameConvertException");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

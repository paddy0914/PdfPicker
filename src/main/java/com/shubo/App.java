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
    public static final String DIR = "d:\\年报解析\\年报";
    public static final String OUTPUT = "d:\\年报解析\\表格\\";
    public static final String OUTPUT_JSON = "d:\\年报解析\\JSON\\";

    public static void main(String[] args) {

        File folder = new File(DIR);

        for (File subFolder : folder.listFiles()) {
            if (Integer.valueOf(subFolder.getName()) < 12) {
                continue;
            }
            try {
                if (subFolder.isDirectory()) {
                    File yrFolder = new File(subFolder.getAbsolutePath() + File.separator + "年报");

                    if (yrFolder.exists() && yrFolder.isDirectory()) {
                        for (File file : yrFolder.listFiles()) {
                            if (file.getName().endsWith("html") && !file.getName().contains("英文版") && !file.getName().contains("摘要")) {
                                String outputTableFile = OUTPUT + File.separator + NameUtils.getFileNameByFileName(file.getName());
                                System.out.println("处理 " + file.getAbsolutePath());
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

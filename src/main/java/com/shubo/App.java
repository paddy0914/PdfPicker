package com.shubo;

import com.shubo.sniff.TableSniffer;
import com.shubo.util.NameUtils;

import java.io.File;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String DIR = "d:\\年报";
    public static final String OUTPUT = "d:\\表格\\";
    public static void main( String[] args )
    {

        try {
            File folder = new File(DIR);
            for (File subFolder : folder.listFiles()) {
                if (subFolder.isDirectory()) {
                    File yrFolder = new File(subFolder.getAbsolutePath() + File.separator + "年报");

                    if (yrFolder.exists() && yrFolder.isDirectory()) {
                        for (File file : yrFolder.listFiles()) {
                            if (file.getName().endsWith("html") && !file.getName().contains("英文版") && !file.getName().contains("摘要")) {
                                TableSniffer.sniff(file, OUTPUT + File.separator + NameUtils.getFileNameByFileName(file.getName()));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

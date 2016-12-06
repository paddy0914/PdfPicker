package com.shubo;

import com.shubo.exception.AnnotationException;
import com.shubo.exception.NameConvertException;
import com.shubo.sniff.TableSniffer;
import com.shubo.util.NameUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by horseman on 2016/11/23.
 */
public class Test {
    public static final String OUTPUT = "d:\\年报解析\\表格\\";

    public static void main(String args[]) {
        File file = new File("F:\\年报解析\\年报\\");
        String outputTableFile = null;
        try {
            outputTableFile = OUTPUT + File.separator + NameUtils.getFileNameByFileName(file.getName());

            System.out.println("处理 " + file.getAbsolutePath());
            TableSniffer.sniff(file, outputTableFile);
//            TableSniffer.sniffEachEntity(new File(outputTableFile));
        } catch (NameConvertException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.shubo;

import com.shubo.exception.AnnotationException;
import com.shubo.parser.PDF2TXT;
import com.shubo.sniff.TableSniffer;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by horseman on 2016/11/28.
 */
public class ReportParser {
    public static void main(String args[]) {
        try {
            String filePath = "D:/年报解析/年报/000005/年报/世纪星源2015年年度报告.html";
            File file = new File(filePath);

            List<String> list = PDF2TXT.parsePDFStructure(filePath);

            String title = "";
            for (String str : list) {
                if (str.startsWith("title#_#")) {
                    title = str;
                } else if (str.startsWith("table#_#") ) {
                    TableSniffer.sniffEntity(str, title, file.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AnnotationException e) {
            e.printStackTrace();
        }
    }
}

package com.shubo.sniff;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Created by horseman on 2016/11/21.
 */
public class FinanceDataSniffer {
    public static boolean sniff(File file, String outputFileName) throws IOException {
        Document doc = null;
        try {
            doc = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        sniffAllTable(file, outputFileName);

        return false;
    }

    private static String getTableText(Element table) {
        String result = "";
        if (table != null) {
            Elements trs = table.select("tr");
            for (Element tr : trs) {
                Elements tds = tr.select("td");
                for (Element td : tds) {
                    result += td.text();
                    System.out.print(td.text());
                }
                result += "\n";
                System.out.println();
            }
            result += "--------------------------------------------------------------------\n";
            System.out.println("--------------------------------------------------------------------");
        }

        return result;
    }

    public static void sniffAllTable(File file, String outputFileName) throws IOException {
        Document doc = null;
        try {
            doc = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements tables = doc.select("table");
        for (Element element : tables) {
            FileUtils.write(new File(outputFileName), getTableText(element), true);
        }

    }

    /*
     * 根据一定的规则探测文档是否包含主要财务数据
     *
     */
    private static Element sniffFinance(Document doc) {
        Elements tables = doc.select("table");
        for (Element element : tables) {
            if (sniffByKeywords(element.text())) {
                return element;
            }
        }

        return null;
    }

    private static final int MATCH_RULE = 3;
    private static boolean sniffByKeywords(String content) {
        int match = 0;
        for (String keyword : financeDataKeyWords) {
            if (content.contains(keyword)) {
                match ++;
            }

            if (match == MATCH_RULE) {
                return true;
            }
        }

        return false;
    }

    private static final String[] financeDataKeyWords = {
            "基本每股收益",
            "稀释每股收益",
            "经营活动产生的现金流量净额",
            "营业收入",
            "加权平均净资产收益",
            "归属于上市公司股东的净利润",
    };
}

package com.shubo.sniff;

import com.shubo.AppContext;
import com.shubo.exception.AnnotationException;
import com.shubo.sniff.report.*;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by horseman on 2016/11/21.
 */
public class TableSniffer {
    public static final String TABLE_SUFFIX = ".table";
    public static final String ELEMENT_DIVIDOR = "###!ERTIAO!###";
    public static final String TABLE_DIVIDOR = "----------------------------------------------------------------------";

    public static List<Sniffer> reportSniffers = new ArrayList();
    public static List<Sniffer> otherSniffers = new ArrayList();

    static {

        reportSniffers.add(new ConsolidatedCashFlowSniffer());
        reportSniffers.add(new ConsolidatedBalanceShellSniffer());
        reportSniffers.add(new ConsolidatedEquityChangeSniffer());
        reportSniffers.add(new ConsolidatedProfitsSniffer());

        reportSniffers.add(new ParentBalanceShellSniffer());
        reportSniffers.add(new ParentCashFlowSniffer());
        reportSniffers.add(new ParentEquityChangeSniffer());
        reportSniffers.add(new ParentProfitsSniffer());

        otherSniffers.add(new FinanceSniffer());
        otherSniffers.add(new NrgalSniffer());
        otherSniffers.add(new ShareHolderSniffer());
        otherSniffers.add(new ShareHolderNLSniffer());
        otherSniffers.add(new CashFlowSniffer());
    }

    /*
     * 识别html文档中的表格，并按照一定格式存在 *.table文件中
     */
    public static boolean sniff(File file, String outputFileName) throws IOException {

        sniffAllTable(file, outputFileName);

        return false;
    }

    /*
     *  通过title名识别表格
     *  适用于八大表
     */
    public static boolean sniffEntity(String table, String title, String fileName, List<String> capturedKeys) throws IOException, AnnotationException {

        System.out.println("title=" + title);
        for (Sniffer sniffer : reportSniffers) {

            if (sniffer.sniffWithTitle(title) && !capturedKeys.contains(sniffer.getKey())) {

                String tableStr = getTableContent(Jsoup.parse(table));

                // 母公司权益变动表中，有两个table，一个是本期，一个是上期，这里取第一个本期的
                if (sniffer instanceof ParentEquityChangeSniffer) {
                    Document doc = Jsoup.parse(table);
//                    Elements elements = doc.select("tbody");
//                    if (elements.size() > 0) {
//                        tableStr = getTableContent(doc);
//                    }
                }

                String[] result = sniffer.generateEntityJson(tableStr);

                if (result != null && result.length == 2) {

                    String outputPath = AppContext.rootFolder +
                            File.separator + AppContext.JSON_OUTPUT_DIR +
                            File.separator + sniffer.getFolder() +
                            File.separator + fileName.replace("html", "json");

                    FileUtils.write(new File(outputPath), result[0], false);
                    capturedKeys.add(sniffer.getKey());
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    /*
     *  通过内容识别表格
     *  适用于八大表以外的其他表
     */
    public static void SniffEntity(String table, String fileName, List<String> snifferedRecords) throws AnnotationException, IOException {
        for (Sniffer sniffer : otherSniffers) {

            if (!snifferedRecords.contains(sniffer.getKey()) && sniffer.sniff(table)) {

                snifferedRecords.add(sniffer.getKey());

                String outputPath = AppContext.rootFolder +
                        File.separator + AppContext.JSON_OUTPUT_DIR +
                        File.separator + sniffer.getFolder() +
                        File.separator + fileName.replace("html", "json");

                String[] result = sniffer.generateEntityJson(table);

                if (result != null && result.length == 2) {
                    FileUtils.write(new File(outputPath), result[0], false);
                    break;
                }
            }
        }
    }

    /*
     * 处理八大表之外的其他表
     */
    public static void sniffEachEntity(List<String> tables, String fileName) throws IOException, AnnotationException {

        // 用于记录已经探测到的sniffer,防止重复探测到某一个表
        // 每个文件只识别一个特定的表
        List<String> snifferedRecords = new ArrayList<>();

        for (String table : tables) {
            String parsedTable = getTableContent(Jsoup.parse(table));
            SniffEntity(parsedTable, fileName, snifferedRecords);
        }
    }

    private static String getTableContent(Element table) {
        String result = "";
        if (table != null) {
            Elements trs = table.select("tr");
            for (Element tr : trs) {
                Elements tds = tr.select("td");
                for (int i = 0; i < tds.size(); i++) {
                    Element td = tds.get(i);

                    result += td.text();
                    //System.out.println(td.text());
                    result += (i == tds.size() - 1) ? "" : ELEMENT_DIVIDOR;
                }
                result += "\n";
            }
        }

        return result;
    }

    private static String getTableText(List<Element> tables) {
        String result = "";
        if (tables != null && tables.size() > 0) {
            for (Element table : tables) {
                if (table != null) {
                    result += getTableContent(table);
                }
            }
        } else {
            return result;
        }

        result += TABLE_DIVIDOR + "\n";

        return result;
    }

    private static String getTableText(Element table) {
        String result = "";
        if (table != null) {
            result += getTableContent(table);
            result += TABLE_DIVIDOR + "\n";
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

        if (tables.size() > 0) {

            // 用于合并相邻的表
            // 判断标准：两个table之间的元素小于2
            List<Element> needWriteTables = new ArrayList<>();

            int interval = 0;

            // 用于记录上一步是不是合并了table，如果合并了 则跳过当前table循环
            int jump = 0;
            for (Element element : tables) {
                if (jump > 0) {
                    jump--;
                    continue;
                }
                needWriteTables.add(element);

//                System.out.println(element.text());
                Element e = element.nextElementSibling();
                while (e != null) {
                    if (e.tag().getName().equals("table")) {
                        needWriteTables.add(e);
                        interval = 0;
                    } else {
                        interval++;
                    }

                    if (interval > 2) {
                        break;
                    }

                    e = e.nextElementSibling();
                }
                FileUtils.write(new File(outputFileName), getTableText(needWriteTables), true);
                interval = 0;

                jump = needWriteTables.size() - 1;
                needWriteTables.clear();
            }
        }
    }


}

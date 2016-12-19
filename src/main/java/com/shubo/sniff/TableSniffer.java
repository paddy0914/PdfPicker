package com.shubo.sniff;

import com.shubo.AppContext;
import com.shubo.ExceptionString;
import com.shubo.stastics.AnalyticalResult;
import com.shubo.sniff.report.IndexEntity;
import com.shubo.exception.AnnotationException;
import com.shubo.sniff.report.*;
import com.shubo.util.HorsemanUtils;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.shubo.stastics.AnalyticalResult.singleResultOperation;

//import static com.shubo.ReportParser.analyticalResultList;

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
    public static boolean sniffEntity(String table, List<String> titles, String fileName, List<String> capturedKeys) throws IOException, AnnotationException {

//        System.out.println("title=" + title);
        for (Sniffer sniffer : reportSniffers) {

            if (sniffer.sniffWithTitle(titles) && !capturedKeys.contains(sniffer.getKey())) {

                String tableStr = getTableContent(Jsoup.parse(table));

                System.out.println("获取 " + HorsemanUtils.subment(sniffer.getFolder()) + " " + fileName);
                String[] result = sniffer.generateEntityJson(tableStr);

                if (result != null && result.length == 2) {
                    //如果得到的result[0]的内容为{}表示解析失败
                    if (result[0].equals(ExceptionString.HEADER_SNIFF_ERR)) {
                        AnalyticalResult.setResultValue(fileName, sniffer.getIndex(), ExceptionString.HEADER_SNIFF_ERR);
                    } else if (result[0].length() > 2) {
                        String outputPath = AppContext.rootFolder +
                                File.separator + AppContext.JSON_OUTPUT_DIR +
                                File.separator + sniffer.getFolder() +
                                File.separator + fileName.replace("html", "json");

                        FileUtils.write(new File(outputPath), result[0], false);
                        capturedKeys.add(sniffer.getKey());

                        AnalyticalResult.setResultValue(fileName, sniffer.getIndex(), "成功");
                        ////统计解析情况
                        singleResultOperation(fileName);
                        return true;
                    } else {
                        AnalyticalResult.setResultValue(fileName, sniffer.getIndex(), "Json空");
                        return false;
                    }

                } else {
                    AnalyticalResult.setResultValue(fileName, sniffer.getIndex(), "-失败");
                    return false;
                }
            }
        }

        return false;
    }
        /*
         *  通过内容识别表格
         *  适用于八大表
         */

    public static void ContentSniffEntity(String table, String fileName, List<String> snifferedRecords) throws AnnotationException, IOException {
        for (Sniffer sniffer : reportSniffers) {

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
                    if (result[0].length() > 2) {
                        FileUtils.write(new File(outputPath), result[0], false);
                        AnalyticalResult.setResultValue(fileName, sniffer.getIndex(), "成功");
                        //统计解析情况
                        singleResultOperation(fileName);
                    } else {
                        AnalyticalResult.setResultValue(fileName, sniffer.getIndex(), "JSON空");
                    }
                    break;
                } else {
                    AnalyticalResult.setResultValue(fileName, sniffer.getIndex(), "失败");
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

    /**
     * 表格内容用ELEMENT_DIVIDOR字符连接
     *
     * @param table
     * @return
     */
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

    //专门处理所有者权益变动表

    /**
     * @param table
     * @return
     */
    private static List<IndexEntity> getTableStructure(String table) {
        List<IndexEntity> list_indexEntity = new ArrayList<>();
        Element table_element = Jsoup.parse(table);
        int k = 1;
        if (table != null) {
            Elements trs = table_element.select("tr");
            Elements trs_one = trs.get(0).select("td");
            Elements trs_two = trs.get(1).select("td");
            Elements trs_three = trs.get(2).select("td");
            //表格中有本期和上期
            if (trs_one.size() > 2) {
                if (trs_two.size() / 2 < 4) {
                    for (int i = 0; i < trs_three.size() / 2; i++) {
                        list_indexEntity.add(new IndexEntity(k, trs_three.get(i).text()));
                        k++;
                    }
                    for (int j = 1; j < trs_two.size() / 2; j++) {
                        //hm.put(new Integer(k), tds2.get(m).text());
                        list_indexEntity.add(new IndexEntity(k, trs_two.get(j).text()));
                        k++;
                    }
                } else {
                    for (int j = 0; j < trs_two.size() / 2; j++) {
                        //hm.put(new Integer(k), tds2.get(m).text());
                        list_indexEntity.add(new IndexEntity(k, trs_two.get(j).text()));
                        k++;
                    }
                }
            } else {
                if (trs_two.size() < 4) {
                    for (int i = 0; i < trs_three.size(); i++) {
                        list_indexEntity.add(new IndexEntity(k, trs_three.get(i).text()));
                        k++;
                    }
                    for (int j = 1; j < trs_two.size(); j++) {
                        //hm.put(new Integer(k), tds2.get(m).text());
                        list_indexEntity.add(new IndexEntity(k, trs_two.get(j).text()));
                        k++;
                    }
                } else {
                    for (int j = 0; j < trs_two.size(); j++) {
                        //hm.put(new Integer(k), tds2.get(m).text());
                        list_indexEntity.add(new IndexEntity(k, trs_two.get(j).text()));
                        k++;
                    }
                }
            }
            return list_indexEntity;
        } else
            return null;
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

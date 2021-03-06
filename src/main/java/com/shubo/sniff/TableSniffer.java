package com.shubo.sniff;

import com.shubo.AppContext;
import com.shubo.ExceptionString;
import com.shubo.sniff.employee.DirectorSniffer;
import com.shubo.sniff.employee.SeniorManagerSniffer;
import com.shubo.sniff.employee.SupervisorSniffer;
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

        reportSniffers.add(new ConsolidatedBalanceShellSniffer());
        reportSniffers.add(new ConsolidatedCashFlowSniffer());
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

        otherSniffers.add(new RelatedTransactionSniffer());//与日常经营相关的关联交易

        otherSniffers.add(new DirectorSniffer());//现任董事会成员
        otherSniffers.add(new SupervisorSniffer());//现任监事会成员
        otherSniffers.add(new SeniorManagerSniffer());//现任高级管理人员

        otherSniffers.add(new FiveCustomerInfoSniffer());//公司前5大客户资料

        otherSniffers.add(new MainSubcompanySniffer());//主要子公司、参股公司分析（主要控股参股公司分析）

        otherSniffers.add(new GuarantySituationSniffer());//担保情况

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

                if (result != null && result.length == 2 && Sniffer.isReportSniffer(sniffer.getClass())) {
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

                } else if (result != null && result.length == 3) {
                    if (result[0].equals(ExceptionString.HEADER_SNIFF_ERR)) {
                        writeAnalyticalResult(fileName, sniffer, ExceptionString.HEADER_SNIFF_ERR);
                    } else if (result[0].length() > 2) {
                        return writeJsonFile(fileName, result, sniffer, capturedKeys);
                    } else {
                        writeAnalyticalResult(fileName, sniffer, "Json空");
                        return false;
                    }
                } else {
                    writeAnalyticalResult(fileName, sniffer, "失败");
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
    public static void SniffEntity(String table, String fileName, List<String> snifferedRecords, List<String> titles) throws AnnotationException, IOException {
        for (Sniffer sniffer : otherSniffers) {
            Sniffer rightSniffer = null;
            if (!snifferedRecords.contains(sniffer.getKey()) && sniffer.sniff(table)) {
                if (sniffer instanceof DirectorSniffer || sniffer instanceof SupervisorSniffer || sniffer instanceof SeniorManagerSniffer) {
                    if (sniffer.sniffWithTitle(titles)) {
                        rightSniffer = sniffer;
                    } else {
                        continue;
                    }
                } else {
                    rightSniffer = sniffer;
                }

                snifferedRecords.add(rightSniffer.getKey());

                String outputPath = AppContext.rootFolder +
                        File.separator + AppContext.JSON_OUTPUT_DIR +
                        File.separator + rightSniffer.getFolder() +
                        File.separator + fileName.replace("html", "json");

                System.out.println("获取 " + HorsemanUtils.subment(rightSniffer.getFolder()) + " " + fileName);
                String[] result = rightSniffer.generateEntityJson(table);

                if (result != null && result.length == 2) {
                    if (result[0].length() > 2) {
                        FileUtils.write(new File(outputPath), result[0], false);
                        AnalyticalResult.setResultValue(fileName, rightSniffer.getIndex(), "成功");
                        //统计解析情况
                        singleResultOperation(fileName);
                    } else {
                        AnalyticalResult.setResultValue(fileName, rightSniffer.getIndex(), "JSON空");
                    }
                    break;
                } else {
                    AnalyticalResult.setResultValue(fileName, rightSniffer.getIndex(), "失败");
                }
            }
        }
    }

    /*
     * 处理八大表之外的其他表
     */
    public static void sniffEachEntity(List<String> tables, String fileName, List<String> titles) throws IOException, AnnotationException {

        // 用于记录已经探测到的sniffer,防止重复探测到某一个表
        // 每个文件只识别一个特定的表
        List<String> snifferedRecords = new ArrayList<>();

        for (String table : tables) {
            String parsedTable = getTableContent(Jsoup.parse(table));
            SniffEntity(parsedTable, fileName, snifferedRecords, titles);
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

    public static Boolean writeJsonFile(String fileName, String[] result, Sniffer sniffer, List<String> capturedKeys) throws AnnotationException, IOException {
        if (sniffer instanceof ConsolidatedBalanceShellSniffer || sniffer instanceof ParentBalanceShellSniffer) {
            ConsolidatedBalanceShellSniffer snifferCBS = new ConsolidatedBalanceShellSniffer();
            ParentBalanceShellSniffer snifferPBS = new ParentBalanceShellSniffer();
            String outputPath1 = AppContext.rootFolder +
                    File.separator + AppContext.JSON_OUTPUT_DIR +
                    File.separator + snifferCBS.getFolder() +
                    File.separator + fileName.replace("html", "json");

            String outputPath2 = AppContext.rootFolder +
                    File.separator + AppContext.JSON_OUTPUT_DIR +
                    File.separator + snifferPBS.getFolder() +
                    File.separator + fileName.replace("html", "json");

            FileUtils.write(new File(outputPath1), result[0], false);
            FileUtils.write(new File(outputPath2), result[1], false);
            capturedKeys.add(sniffer.getKey());

            AnalyticalResult.setResultValue(fileName, snifferCBS.getIndex(), "成功");
            AnalyticalResult.setResultValue(fileName, snifferPBS.getIndex(), "成功");
            ////统计解析情况
            singleResultOperation(fileName);
            singleResultOperation(fileName);
            return true;
        } else if (sniffer instanceof ConsolidatedCashFlowSniffer || sniffer instanceof ParentCashFlowSniffer) {
            ConsolidatedCashFlowSniffer snifferCCF = new ConsolidatedCashFlowSniffer();
            ParentCashFlowSniffer snifferPCF = new ParentCashFlowSniffer();
            String outputPath1 = AppContext.rootFolder +
                    File.separator + AppContext.JSON_OUTPUT_DIR +
                    File.separator + snifferCCF.getFolder() +
                    File.separator + fileName.replace("html", "json");

            String outputPath2 = AppContext.rootFolder +
                    File.separator + AppContext.JSON_OUTPUT_DIR +
                    File.separator + snifferPCF.getFolder() +
                    File.separator + fileName.replace("html", "json");

            FileUtils.write(new File(outputPath1), result[0], false);
            FileUtils.write(new File(outputPath2), result[1], false);
            capturedKeys.add(sniffer.getKey());

            AnalyticalResult.setResultValue(fileName, snifferCCF.getIndex(), "成功");
            AnalyticalResult.setResultValue(fileName, snifferPCF.getIndex(), "成功");
            ////统计解析情况
            singleResultOperation(fileName);
            singleResultOperation(fileName);
            return true;
        } else if (sniffer instanceof ConsolidatedProfitsSniffer || sniffer instanceof ParentProfitsSniffer) {
            ConsolidatedProfitsSniffer snifferCP = new ConsolidatedProfitsSniffer();
            ParentProfitsSniffer snifferPP = new ParentProfitsSniffer();
            String outputPath1 = AppContext.rootFolder +
                    File.separator + AppContext.JSON_OUTPUT_DIR +
                    File.separator + snifferCP.getFolder() +
                    File.separator + fileName.replace("html", "json");

            String outputPath2 = AppContext.rootFolder +
                    File.separator + AppContext.JSON_OUTPUT_DIR +
                    File.separator + snifferPP.getFolder() +
                    File.separator + fileName.replace("html", "json");

            FileUtils.write(new File(outputPath1), result[0], false);
            FileUtils.write(new File(outputPath2), result[1], false);
            capturedKeys.add(sniffer.getKey());

            AnalyticalResult.setResultValue(fileName, snifferCP.getIndex(), "成功");
            AnalyticalResult.setResultValue(fileName, snifferPP.getIndex(), "成功");
            ////统计解析情况
            singleResultOperation(fileName);
            singleResultOperation(fileName);
            return true;
        }
        return false;
    }

    public static void writeAnalyticalResult(String fileName, Sniffer sniffer, String resultStr) throws AnnotationException {
        if (sniffer instanceof ConsolidatedBalanceShellSniffer || sniffer instanceof ParentBalanceShellSniffer) {
            ConsolidatedBalanceShellSniffer cbss = new ConsolidatedBalanceShellSniffer();
            ParentBalanceShellSniffer pbss = new ParentBalanceShellSniffer();
            AnalyticalResult.setResultValue(fileName, cbss.getIndex(), resultStr);
            AnalyticalResult.setResultValue(fileName, pbss.getIndex(), resultStr);
        }
        if (sniffer instanceof ConsolidatedCashFlowSniffer || sniffer instanceof ParentCashFlowSniffer) {
            ConsolidatedCashFlowSniffer ccfs = new ConsolidatedCashFlowSniffer();
            ParentCashFlowSniffer pcfs = new ParentCashFlowSniffer();
            AnalyticalResult.setResultValue(fileName, ccfs.getIndex(), resultStr);
            AnalyticalResult.setResultValue(fileName, pcfs.getIndex(), resultStr);
        }
        if (sniffer instanceof ConsolidatedProfitsSniffer || sniffer instanceof ParentProfitsSniffer) {
            ConsolidatedProfitsSniffer cps = new ConsolidatedProfitsSniffer();
            ParentProfitsSniffer pps = new ParentProfitsSniffer();
            AnalyticalResult.setResultValue(fileName, cps.getIndex(), resultStr);
            AnalyticalResult.setResultValue(fileName, pps.getIndex(), resultStr);
        }
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

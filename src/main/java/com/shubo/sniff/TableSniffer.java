package com.shubo.sniff;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

    public static List<Sniffer> sniffers = new ArrayList();

    static {
        sniffers.add(new FinanceSniffer());
        sniffers.add(new NrgalSniffer());
        sniffers.add(new ShareHolderSniffer());
        sniffers.add(new ShareHolderNLSniffer());
        sniffers.add(new CashFlowSniffer());
    }

    /*
     * 识别html文档中的表格，并按照一定格式存在 *.table文件中
     */
    public static boolean sniff(File file, String outputFileName) throws IOException {

        sniffAllTable(file, outputFileName);

        return false;
    }

    public static void sniffEachEntity(File file) throws IOException {
        String fileContent = FileUtils.readFileToString(file);
        String[] tableContents = fileContent.split(TABLE_DIVIDOR + "\n");

        // 用于记录已经探测到的sniffer,防止重复探测到某一个表
        // 每个文件只识别一个特定的表
        List<String> snifferedRecords = new ArrayList<String>();

        for (String table : tableContents) {
            for (Sniffer sniffer : sniffers) {

                if (!snifferedRecords.contains(sniffer.getKey()) && sniffer.sniff(table)) {

                    snifferedRecords.add(sniffer.getKey());

                    String fileName = file.getName();
                    String outputFileName = fileName.replace(TABLE_SUFFIX, sniffer.getSuffix());

                    String folder = file.getParent() + File.separator + sniffer.getFolder();
                    if (!new File(folder).exists()) {
                        new File(folder).mkdir();
                    }

                    String outputFilePath = folder + File.separator + outputFileName;

                    FileUtils.write(new File(outputFilePath), table, false);

                    String[] result = sniffer.generateEntityJson(table);

                    if (result != null && result.length == 2) {
                        String path = outputFilePath.replace(sniffer.getSuffix(), ".json");
                        FileUtils.write(new File(path.replace("表格", "JSON")), result[0], false);

                        // 匹配过的行已经删除，产生新的table，以免重复匹配
                        table = result[1];
                    }
                }
            }
        }
    }

    private static String getTableContnet(Element table) {
        String result = "";
        if (table != null) {
            Elements trs = table.select("tr");
            for (Element tr : trs) {
                Elements tds = tr.select("td");
                for (Element td : tds) {
                    result += td.text() + ELEMENT_DIVIDOR;
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
                    result += getTableContnet(table);
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
            result += getTableContnet(table);
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
                    jump --;
                    continue;
                }
                needWriteTables.add(element);

                System.out.println(element.text());
                Element e = element.nextElementSibling();
                while (e != null) {
                    if (e.tag().getName().equals("table")) {
                        needWriteTables.add(e);
                        interval = 0;
                    } else {
                        interval ++;
                    }

                    if (interval > 2) {
                        break;
                    }

                    e = e.nextElementSibling();
                }
                FileUtils.write(new File(outputFileName), getTableText(needWriteTables), true);

                jump = needWriteTables.size() - 1;
                needWriteTables.clear();
            }
        }


    }


}

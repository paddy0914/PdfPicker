package com.shubo.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PDF2TXT {
    public static void main(String[] args) throws IOException {
        // String string ="徐工机械、本公司或公司指徐工集团工程机械股份有限院";
        // System.out.println(string.matches(".*指.*(公司|院)"));

        List<String> list = parsePDFStructure("D:/年报解析/年报/000005/年报/世纪星源2015年年度报告.html");
        for(String str : list) {
            System.out.println(str);
        }
    }

    public static final String typeText = "text";
    public static final String typeTitle = "title";
    public static final String typeImage = "image";
    public static final String typeTable = "table";
    public static final String splitChar = "#_#";

    private static final String titleMatch = ".*<a name=\"bookmark\\d+\">.*";
    private static final String imageMatch = ".*<img.*src=.*";

    /**
     * 主要解析pdf结构的方法
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> parsePDFStructure(String path) throws IOException {
        List<String> list = new ArrayList<>();
        Document documentMain = Jsoup.parse(new File(path), "utf-8");
        Elements elements = documentMain.select("body").first().children();
        Element proElement = null;
        for (int i = 0; i < elements.size(); i++) {
            Element elementNow = elements.get(i);
            String tag = elementNow.tagName();
            String html = elementNow.toString();
            String content = elementNow.text().replaceAll(" ", "");
            if (content.equals("")) {
                continue;
            }
            if (html.matches(titleMatch)) {
                // 识别标题
                list.add(typeTitle + splitChar + content);
            } else if (html.matches(imageMatch)) {
                // 识别image
                Elements elementsImage = elementNow.select("img");
                for (Element elementImage : elementsImage) {
                    list.add(typeImage + splitChar + elementImage.toString());
                }
            } else if (tag.equals(typeTable)) {
                // 识别table
                if (typeTable.equals(proElement.tagName())) {
                    // 合并表格
                    list.remove(list.size() - 1);
                    html = mergeTable(proElement, elementNow);
                }
                list.add(typeTable + splitChar + html);
            } else if (tag.equals("p")) {
                if ("".equals(content)) {
                    continue;
                }
                if (content.charAt(content.length() - 1) == '。') {
                    list.add(typeText + splitChar + content);
                } else {
                    // 句子被切割断了
                    StringBuffer sBuffer = new StringBuffer(content);
                    int num = findNextContent(elementNow, sBuffer, tag);
                    list.add(typeText + splitChar + sBuffer.toString());
                    i += num;
                }

            } else if (tag.contains("h")) {
                if ("".equals(content)) {
                    continue;
                }
                if (content.contains("。")) {
                    // 是句子 不是标题
                    if (content.charAt(content.length() - 1) == '。') {
                        list.add(typeText + splitChar + content);
                    } else {
                        // 句子被切割断了
                        StringBuffer sBuffer = new StringBuffer(content);
                        int num = findNextContent(elementNow, sBuffer, tag);
                        list.add(typeText + splitChar + sBuffer.toString());
                        i += num;
                    }
                } else {
                    list.add(typeText + splitChar + content);
                }
            }
            proElement = elementNow;
        }
        return list;
    }

    private static String mergeTable(Element first, Element second) {
        if (first == null) {
            return second.toString();
        }
        if (second == null) {
            return first.toString();
        }
        return first.append(second.select("tbody").toString()).toString();
    }

    /**
     * 合并缺少项 向下找至多两个标签
     *
     * @param elementNow
     * @param sBuffer
     * @param tag
     * @return
     */
    public static int findNextContent(Element elementNow, StringBuffer sBuffer, String tag) {
        int num = 0;
        Element next = elementNow.nextElementSibling();
        while (num < 2) {
            if (next == null || !next.tagName().equals(tag)) {
                break;
            }
            if (next.html().matches(titleMatch))
                break;
            String content = next.text().replaceAll(" ", "");
            if (content.matches("^第.*节.*")) {
                break;
            }
            sBuffer.append(content);
            num++;
            if (!"".equals(content) && content.charAt(content.length() - 1) == '。') {
                break;
            }
            next = next.nextElementSibling();
        }
        return num;
    }

}

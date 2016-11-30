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
    public static final String typeText = "text";
    public static final String typeTitle = "title";
    public static final String typeImage = "image";
    public static final String typeTable = "table";
    public static final String splitChar = "#_#";
    private static final String matchChapter = "^第.*节.*";
    private static final String matchLittleChapter = "^（.*）.*";
    private static final String titleMatch = ".*<a name=\"bookmark\\d+\">.*";
    private static final String imageMatch = ".*<img.*src=.*";

    public static void main(String[] args) throws IOException {
        // String string ="徐工机械、本公司或公司指徐工集团工程机械股份有限院";
        // System.out.println(string.matches(".*指.*(公司|院)"));
    }

    public static List<String> parsePDFStructure(String path) throws IOException {
        // TODO Auto-generated method stub
        Document documentMain = Jsoup.parse(new File(path), "utf-8");
        boolean isHasTitle = false;
        if (documentMain != null) {
            Elements elements = documentMain.select("body h4");
            if (elements.size() <= 0) {
                elements = documentMain.select("body h2");
            }
            for (Element element : elements) {
                String tmp = element.toString();
                isHasTitle = tmp.matches(titleMatch);
                break;
            }

        }
        if (isHasTitle) {
            return parsePDFFromTitle(documentMain.select("body").first().children());
        } else {
            return parsePDFFromRule(documentMain.select("body").first().children());
        }
    }

    public static List<String> parsePDFFromRule(Elements elements) throws IOException {
        List<String> list = new ArrayList<>();
        Element proElement = null;
        for (int i = 0; i < elements.size(); i++) {
            Element elementNow = elements.get(i);
            String html = elementNow.toString();
            String tag = elementNow.tagName();
            if (tag.equals("p")) {
                String content = elementNow.text().replaceAll(" ", "");
                if ("".equals(content)) {
                    continue;
                }
                if (content.replaceAll("\\d+", "").equals("")) {
                    continue;
                }

                boolean judge = (content.length() < 30);

                if (content.charAt(content.length() - 1) == '。') {
                    list.add(typeText + splitChar + content);
                } else if (content.indexOf('、') < 3 && content.indexOf('、') > 0 && judge) {
                    list.add(typeTitle + splitChar + content);
                } else if (content.indexOf('．') < 3 && content.indexOf('．') > 0 && judge) {
                    list.add(typeTitle + splitChar + content);
                } else if (content.matches(matchLittleChapter) && judge) {
                    list.add(typeTitle + splitChar + content);
                } else if (content.matches(matchChapter) && Character.isLetter(content.charAt(content.length() - 1))
                        && judge) {
                    list.add(typeTitle + splitChar + content);
                } else if (content.length() < 20) {
                    list.add(typeText + splitChar + content);
                } else { // 句子被切割断了
                    StringBuffer sBuffer = new StringBuffer(content);
                    int num = findNextContent(elementNow, sBuffer, tag);
                    list.add(typeText + splitChar + sBuffer.toString());
                    i += num;
                }

            } else if (tag.contains("h")) {
                String content = elementNow.text().replaceAll(" ", "");
                if ("".equals(content)) {
                    continue;
                }
                if (content.contains("。")) { // 是句子 不是标题
                    if (content.charAt(content.length() - 1) == '。') {
                        list.add(typeText + splitChar + content);
                    } else { // 句子被切割断了
                        StringBuffer sBuffer = new StringBuffer(content);
                        int num = findNextContent(elementNow, sBuffer, tag);
                        list.add(typeText + splitChar + sBuffer.toString());
                        i += num;
                    }
                } else {
                    if (content.indexOf('、') < 3 && content.indexOf('、') > 0) {
                        list.add(typeTitle + splitChar + content);
                    } else if (content.indexOf('．') < 3 && content.indexOf('．') > 0) {
                        list.add(typeTitle + splitChar + content);
                    } else if (content.matches(matchLittleChapter)) {
                        list.add(typeTitle + splitChar + content);
                    } else if (content.matches(matchChapter)) {
                        list.add(typeTitle + splitChar + content);
                    } else {
                        list.add(typeTitle + content);
                    }
                }
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
            }
            proElement = elementNow;
        }
        return list;
    }

    /**
     * 通过标签解析pdf结构的方法
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> parsePDFFromTitle(Elements elements) throws IOException {
        List<String> list = new ArrayList<>();
        Element proElement = null;
        for (int i = 0; i < elements.size(); i++) {
            Element elementNow = elements.get(i);
            String tag = elementNow.tagName();
            String html = elementNow.toString();
            String content = elementNow.text().replaceAll(" ", "");
            if ("".equals(content)) {
                continue;
            }
            if (content.replaceAll("\\d+", "").equals("")) {
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
        // TODO Auto-generated method stub
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
            if (content.matches(matchChapter) || content.matches(matchLittleChapter)
                    || (content.indexOf('、') < 3 && content.indexOf('、') > 0)
                    || (content.indexOf('．') < 3 && content.indexOf('．') > 0)) {
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


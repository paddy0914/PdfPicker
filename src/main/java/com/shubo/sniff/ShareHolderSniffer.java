package com.shubo.sniff;

import com.shubo.entity.ShareHolder;

/**
 * Created by horseman on 2016/11/23.
 */
public class ShareHolderSniffer extends Sniffer {
    public String getKey() {
        return "ShareHolder";
    }

    public String getSuffix() {
        return ".table.股东";
    }

    public String getFolder() {
        return "前10名股东持股情况";
    }

    public boolean sniff(String content) {
        return sniffByKeywords(content);
    }

    public String[] generateEntityJson(String content) {
        return generateEntityJson(content, ShareHolder.class);
    }

    private static final int MATCH_RULE = 3;

    private static boolean sniffByKeywords(String content) {
        int match = 0;
        content = content.replace(" ", "");

        for (String keyword : NrgalDataKeyWords) {
            if (content.contains(keyword)) {
                match++;
            }

            if (match == MATCH_RULE) {
                return true;
            }
        }

        return false;
    }

    private static final String[] NrgalDataKeyWords = {
            "股东性质",
            "持股数量",
            "股份状态（质押或冻结情况）",
            "持有有限售条件的股份数量",
            "持有无限售条件的股份数量",
    };
}

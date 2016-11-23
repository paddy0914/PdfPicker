package com.shubo.sniff;

import com.shubo.entity.ShareHolderNoLimit;

/**
 * Created by horseman on 2016/11/23.
 */
public class ShareHolderNLSniffer extends Sniffer {
    public String getKey() {
        return "ShareHolderNL";
    }

    public String getSuffix() {
        return ".table.股东NL";
    }

    public String getFolder() {
        return "前10名无限售条件股东持股情况";
    }

    public boolean sniff(String content) {
        return sniffByKeywords(content);
    }

    public String[] generateEntityJson(String content) {
        return generateEntityJsonArrays(content, ShareHolderNoLimit.class);
    }

    private static final int MATCH_RULE = 3;

    public boolean sniffByKeywords(String content) {
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
            "持有无限售条件股份数量",
            "股份种类",
            "股东名称",
    };
}

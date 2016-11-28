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
        return sniffByKeywords(content, ShareHolderNLDataKeyWords, MATCH_CNT);
    }

    public String[] generateEntityJson(String content) {
        return generateEntityJsonArrays(content, ShareHolderNoLimit.class);
    }

    private static final int MATCH_CNT = 3;

    private static final String[] ShareHolderNLDataKeyWords = {
            "持有无限售条件股份数量",
            "股份种类",
            "股东名称",
    };
}

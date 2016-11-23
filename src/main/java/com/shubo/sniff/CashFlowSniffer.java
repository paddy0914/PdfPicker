package com.shubo.sniff;

import com.shubo.entity.CashFlow;

/**
 * Created by horseman on 2016/11/23.
 */
public class CashFlowSniffer extends Sniffer {
    public String getKey() {
        return "CashFlow";
    }

    public String getSuffix() {
        return ".table.cashflow";
    }

    public String getFolder() {
        return "现金流";
    }

    public boolean sniff(String content) {
        return sniffByKeywords(content);
    }

    public String[] generateEntityJson(String content) {
        return generateEntityJson(content, CashFlow.class);
    }

    private static final int MATCH_RULE = 4;

    private static boolean sniffByKeywords(String content) {
        int match = 0;
        content = content.replace(" ", "");
        for (String keyword : financeDataKeyWords) {
            if (content.contains(keyword)) {
                match++;
            }

            if (match == MATCH_RULE) {
                return true;
            }
        }

        return false;
    }

    private static final String[] financeDataKeyWords = {
            "经营活动现金流入小计",
            "经营活动现金流出小计",
            "经营活动产生的现金流量净额",
            "投资活动现金流入小计",
            "资活动现金流出小计",
            "投资活动产生的现金流量净额",
            "筹资活动现金流入小计",
            "筹资活动现金流出小计",
            "筹资活动产生的现金流量净额",
            "现金及现金等价物净增加额",
    };
}

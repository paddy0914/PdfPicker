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
        return sniffByKeywords(content, financeDataKeyWords, MATCH_CNT);
    }

    public String[] generateEntityJson(String content) {
        return generateEntityJson(content, CashFlow.class);
    }

    private static final int MATCH_CNT = 8;

    private final String[] financeDataKeyWords = {
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

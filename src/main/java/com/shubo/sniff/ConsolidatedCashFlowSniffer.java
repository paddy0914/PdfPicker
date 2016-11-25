package com.shubo.sniff;

import com.shubo.entity.report.ConsolidatedCashFlow;

/**
 * Created by horseman on 2016/11/25.
 */
public class ConsolidatedCashFlowSniffer extends Sniffer {
    @Override
    public String getKey() {
        return "ConsolidatedCashFlow";
    }

    @Override
    public String getSuffix() {
        return ".ccf";
    }

    @Override
    public String getFolder() {
        return "合并現金流量表";
    }

    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content);
    }

    @Override
    public String[] generateEntityJson(String content) {
        return generateEntityJson(content, ConsolidatedCashFlow.class);
    }

    private static final int MATCH_RULE = 3;

    public boolean sniffByKeywords(String content) {
        int match = 0;
        content = content.replace(" ", "");
        for (String keyword : CCFKeyWords) {
            if (content.contains(keyword)) {
                match++;
            }

            if (match == MATCH_RULE) {
                return true;
            }
        }

        return false;
    }

    private static final String[] CCFKeyWords = {
            "客户存款和同业存放款项净增加额",
            "向中央银行借款净增加额",
            "收到原保险合同保费取得的现金",
            "收到再保险业务现金净额",
            "保户储金及投资款净增加额",
            "拆入资金净增加额",
            "回购业务资金净增加额"
    };
}

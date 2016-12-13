package com.shubo.sniff;

import com.shubo.annotation.Todd;
import com.shubo.entity.CashFlow;

/**
 * Created by horseman on 2016/11/23.
 */
@Todd(key = "CashFlow",
        index = 9,
        suffix = ".cashflow",
        folder = "现金流")
public class CashFlowSniffer extends Sniffer {

    public boolean sniff(String content) {
        return sniffByKeywords(content, financeDataKeyWords, MATCH_CNT);
    }

    @Override
    public int[] getColCnt(String table) {
        int[] result = {1, 1};
        return result;
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

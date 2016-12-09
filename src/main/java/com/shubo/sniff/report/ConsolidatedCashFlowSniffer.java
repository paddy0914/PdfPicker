package com.shubo.sniff.report;

import com.shubo.annotation.Todd;
import com.shubo.entity.report.ConsolidatedCashFlow;
import com.shubo.sniff.Sniffer;

/**
 * Created by horseman on 2016/11/25.
 */
@Todd(key = "ConsolidatedCashFlow",
        suffix = ".ccf",
        folder = "合并现金流量表",
        title = {"合并现金流量表"})
public class ConsolidatedCashFlowSniffer extends Sniffer {

    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content, CCFKeyWords, 3);
    }

    /**
     *   需要完善
     * @param table
     * @return
     */
    @Override
    public int[] getColCnt(String table) {
        return new int[0];
    }

    @Override
    public String[] generateEntityJson(String content) {
        return generateEntityJson(content, ConsolidatedCashFlow.class, 2, 3);
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

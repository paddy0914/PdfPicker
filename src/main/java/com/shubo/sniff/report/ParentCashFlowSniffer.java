package com.shubo.sniff.report;

import com.shubo.annotation.Todd;

/**
 * Created by horseman on 2016/11/29.
 */
@Todd(key = "ParentCashFlowSniffer",
        index = 5,
        suffix = ".pccf",
        folder = "母公司现金流量表",
        title = {"母公司现金流量表","现金流量表"})
public class ParentCashFlowSniffer extends ConsolidatedCashFlowSniffer {
}

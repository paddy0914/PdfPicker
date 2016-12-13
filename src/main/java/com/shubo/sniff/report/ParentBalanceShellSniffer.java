package com.shubo.sniff.report;

import com.shubo.annotation.Todd;
import com.shubo.sniff.Sniffer;

/**
 * Created by horseman on 2016/11/29.
 */
@Todd(key = "ParentBalanceShellSniffer",
        index = 4,
        suffix = ".pcbs",
        folder = "母公司资产负债表",
        title = {"母公司资产负债表"})
public class ParentBalanceShellSniffer extends ConsolidatedBalanceShellSniffer {
}

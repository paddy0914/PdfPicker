package com.shubo.sniff.report;

import com.shubo.annotation.Todd;

/**
 * Created by horseman on 2016/11/29.
 */
@Todd(key = "ParentProfitsSniffer",
        index = 7,
        suffix = ".pcp",
        folder = "母公司利润表",
        title = {"母公司利润表"})
public class ParentProfitsSniffer extends ConsolidatedProfitsSniffer {
}

package com.shubo.sniff.report;

import com.shubo.annotation.Todd;
import com.shubo.entity.report.ConsolidatedEquityChange;

/**
 * Created by horseman on 2016/11/29.
 */
@Todd(key = "ParentEquityChangeSniffer",
        index = 6,
        suffix = ".pces",
        folder = "母公司所有者权益变动表",
        title = {"母公司所有者权益变动表"})
public class ParentEquityChangeSniffer extends ConsolidatedEquityChangeSniffer {
    @Override
    public String[] generateEntityJson(String content) {
        return generateEntityJson(content, ConsolidatedEquityChange.class, 11, 8);
    }
}

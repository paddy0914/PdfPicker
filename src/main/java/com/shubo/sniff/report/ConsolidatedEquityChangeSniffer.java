package com.shubo.sniff.report;

import com.shubo.annotation.Todd;
import com.shubo.entity.report.ConsolidatedEquityChange;
import com.shubo.sniff.Sniffer;

/**
 * Created by horseman on 2016/11/28.
 * 合并所有者权益变动表
 */
@Todd(key = "ConsolidatedEquityChange",
        suffix = ".ces",
        folder = "合并所有者权益变动表")
public class ConsolidatedEquityChangeSniffer extends Sniffer {

    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content, CECKeyWords, 4);
    }

    @Override
    public String[] generateEntityJson(String content) {
        return generateEntityJson(content, ConsolidatedEquityChange.class, 13);
    }

    private static final String[] CECKeyWords = {
            "加：会计政策变更",
            "前期差错更正",
            "同一控制下企业合并",
            "1．股东投入的普通股",
            "2．提取一般风险准备",
            "1．资本公积转增资本（或股本）",
            "2．盈余公积转增资本（或股本）"
    };
}

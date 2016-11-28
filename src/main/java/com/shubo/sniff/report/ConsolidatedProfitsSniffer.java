package com.shubo.sniff.report;

import com.shubo.annotation.Todd;
import com.shubo.entity.report.ConsolidatedProfits;
import com.shubo.sniff.Sniffer;

/**
 * Created by horseman on 2016/11/28.
 */
@Todd(key = "ConsolidatedProfits",
        suffix = ".cp",
        folder = "合并利润表",
        title = {"合并利润表", "母公司利润表"})
public class ConsolidatedProfitsSniffer extends Sniffer {

    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content, CPKeyWords, 6);
    }

    @Override
    public String[] generateEntityJson(String content) {
        return generateEntityJson(content, ConsolidatedProfits.class, 2);
    }

    private static final String[] CPKeyWords = {
            "利息收入",
            "已赚保费",
            "手续费及佣金收入",
            "提取保险合同准备金净额",
            "保单红利支出",
            "财务费用",
            "资产减值损失",
            "加：营业外收入",
            "减：所得税费用"
    };
}

package com.shubo.sniff.report;

import com.shubo.entity.report.ConsolidatedProfits;
import com.shubo.sniff.Sniffer;

/**
 * Created by horseman on 2016/11/28.
 */
public class ConsolidatedProfitsSniffer extends Sniffer {
    @Override
    public String getKey() {
        return "ConsolidatedProfits";
    }

    @Override
    public String getSuffix() {
        return ".cp";
    }

    @Override
    public String getFolder() {
        return "合并利润表";
    }

    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content, CPKeyWords, 6);
    }

    @Override
    public String[] generateEntityJson(String content) {
        return generateEntityJsonArrays(content, ConsolidatedProfits.class);
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

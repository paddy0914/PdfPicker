package com.shubo.sniff.report;

import com.shubo.entity.report.ConsolidatedBalanceSheet;
import com.shubo.sniff.Sniffer;

/**
 * Created by horseman on 2016/11/28.
 */
public class ConsolidatedBalanceShellSniffer extends Sniffer {
    @Override
    public String getKey() {
        return "ConsolidatedBalanceShell";
    }

    @Override
    public String getSuffix() {
        return ".cbs";
    }

    @Override
    public String getFolder() {
        return "合并资产负债表";
    }

    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content, CBSKeyWords, 7);
    }

    @Override
    public String[] generateEntityJson(String content) {
        return generateEntityJson(content, ConsolidatedBalanceSheet.class);
    }

    private static final String[] CBSKeyWords = {
            "固定资产清理固定资产清理",
            "生产性生物资产",
            "长期待摊费用",
            "吸收存款及同业存放",
            "衍生金融负债",
            "卖出回购金融资产款",
            "应付职工薪酬",
            "划分为持有待售的负债",
            "递延所得税负债",
            "永续债"
    };
}

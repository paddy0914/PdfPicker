package com.shubo.sniff;

import com.shubo.entity.Finance;

/**
 * Created by horseman on 2016/11/22.
 */
public class FinanceSniffer extends Sniffer {
    public String getKey() {
        return "Finance";
    }

    public String getSuffix() {
        return ".table1";
    }

    public String getFolder() {
        return "主要财务数据";
    }

    /*
     * 根据一定的规则探测文档是否包含主要财务数据
     *
     */
    public boolean sniff(String content) {
        if (sniffByKeywords(content)) {
            return true;
        }

        return false;
    }

    public String[] generateEntityJson(String content) {
        return generateEntityJson(content, Finance.class);
    }

    private static final int MATCH_RULE = 3;

    public boolean sniffByKeywords(String content) {
        int match = 0;
        content = content.replace(" ", "");
        for (String keyword : financeDataKeyWords) {
            if (content.contains(keyword)) {
                match++;
            }

            if (match == MATCH_RULE) {
                return true;
            }
        }

        return false;
    }

    private static final String[] financeDataKeyWords = {
            "基本每股收益",
            "稀释每股收益",
            "经营活动产生的现金流量净额",
            "营业收入",
            "加权平均净资产收益",
            "归属于上市公司股东的净利润",
    };

}

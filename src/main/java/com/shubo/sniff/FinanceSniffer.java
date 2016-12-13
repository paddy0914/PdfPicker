package com.shubo.sniff;

import com.shubo.annotation.Todd;
import com.shubo.entity.Finance;

/**
 * Created by horseman on 2016/11/22.
 * 主要财务数据
 */
@Todd(key = "Finance",
        index = 8,
        suffix = ".finance",
        folder = "主要财务数据")
public class FinanceSniffer extends Sniffer {

    /*
     * 根据一定的规则探测文档是否包含主要财务数据
     *
     */
    public boolean sniff(String content) {
        return sniffByKeywords(content, financeDataKeyWords, MATCH_CNT);
    }

    @Override
    public int[] getColCnt(String table) {
        int[] result ={-1,-1};
        return result;
    }

    public String[] generateEntityJson(String content) {
        return generateEntityJson(content, Finance.class);
    }

    private static final int MATCH_CNT = 3;

    private static final String[] financeDataKeyWords = {
            "基本每股收益",
            "稀释每股收益",
            "经营活动产生的现金流量净额",
            "营业收入",
            "加权平均净资产收益",
            "归属于上市公司股东的净利润",
    };

}

package com.shubo.sniff;

import com.shubo.annotation.Todd;
import com.shubo.entity.ShareHolderNoLimit;

/**
 * Created by horseman on 2016/11/23.
 */
@Todd(key = "ShareHolderNL",
        suffix = ".shareHolderNL",
        folder = "前10名无限售条件股东持股情况")
public class ShareHolderNLSniffer extends Sniffer {

    public boolean sniff(String content) {
        return sniffByKeywords(content, ShareHolderNLDataKeyWords, MATCH_CNT);
    }

    @Override
    public int[] getColCnt(String table) {
        int[] result ={-1,-1};
        return result;
    }

    public String[] generateEntityJson(String content) {
        return generateEntityJsonArrays(content, ShareHolderNoLimit.class);
    }

    private static final int MATCH_CNT = 3;

    private static final String[] ShareHolderNLDataKeyWords = {
            "持有无限售条件股份数量",
            "股份种类",
            "股东名称",
    };
}

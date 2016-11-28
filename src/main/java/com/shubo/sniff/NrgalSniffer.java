package com.shubo.sniff;

import com.shubo.entity.Nrgal;

/**
 * Created by horseman on 2016/11/22.
 */
public class NrgalSniffer extends Sniffer {
    public String getKey() {
        return "Nrgal";
    }

    public String getSuffix() {
        return ".nrgal";
    }

    public String getFolder() {
        return "非经常性损益";
    }

    public boolean sniff(String content) {
        return sniffByKeywords(content, NrgalDataKeyWords, MATCH_CNT);
    }

    public String[] generateEntityJson(String content) {
        return generateEntityJson(content, Nrgal.class);
    }

    private static final int MATCH_CNT = 4;

    private static final String[] NrgalDataKeyWords = {
            "非流动",
            "资产处置损益",
            "营业外收入和支出",
            "除上述各项",
            "所得税",
            "影响",
            "少数股东",
            "承担部分",
    };
}

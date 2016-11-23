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
        if (sniffByKeywords(content)) {
            return true;
        }

        return false;
    }

    public String[] generateEntityJson(String content) {
        return generateEntityJson(content, Nrgal.class);
    }

    private static final int MATCH_RULE = 3;

    public boolean sniffByKeywords(String content) {
        int match = 0;
        content = content.replace(" ", "");
        for (String keyword : NrgalDataKeyWords) {
            if (content.contains(keyword)) {
                match++;
            }

            if (match == MATCH_RULE) {
                return true;
            }
        }

        return false;
    }

    private static final String[] NrgalDataKeyWords = {
            "非流动性资产处置损益",
            "除上述各项以外的",
            "以上调整对所得税的影响",
            "加权平均净资产收益",
            "少数股东承担部分",
    };
}

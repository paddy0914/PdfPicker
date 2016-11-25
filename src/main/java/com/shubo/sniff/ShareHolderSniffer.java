package com.shubo.sniff;

import com.shubo.entity.ShareHolder;

/**
 * Created by horseman on 2016/11/23.
 */
public class ShareHolderSniffer extends Sniffer {
    public String getKey() {
        return "ShareHolder";
    }

    public String getSuffix() {
        return ".table.股东";
    }

    public String getFolder() {
        return "前10名股东持股情况";
    }

    public boolean sniff(String content) {
        return sniffByKeywords(content);
    }

    public String[] generateEntityJson(String content) {
        return generateEntityJsonArrays(content, ShareHolder.class);
    }

    private static final int MATCH_RULE = 3;

    @Override
    public HeaderInfo sniffHeader(String content, Class clazz) {
        HeaderInfo info = super.sniffHeader(content, clazz);

        // 补全最后的一个数量，因为通用解析头的时候，股东情况的之家冻结情况在表头分成了两行
        if (info != null && info.headers.size() > 0) {
            info.headers.add("freezAmount");
        }

        return info;
    }

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
            "股东性质",
            "持股数量",
            "股份状态",
            "质押或冻结情况",
            "持有有限售条件的股份数量",
            "持有无限售条件的股份数量",
    };
}

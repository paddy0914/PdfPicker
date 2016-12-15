package com.shubo.sniff;

import com.shubo.annotation.Todd;
import com.shubo.entity.ShareHolder;

/**
 * Created by horseman on 2016/11/23.
 * 前10名股东持股情况
 */
@Todd(key = "ShareHolder",
        index = 12,
        suffix = ".shareHolder",
        folder = "前10名股东持股情况")
public class ShareHolderSniffer extends Sniffer {

    public boolean sniff(String content) {
        return sniffByKeywords(content, ShareHolderDataKeyWords, MATCH_CNT);
    }

    @Override
    public ColResult getColCnt(String table, Class clazz) {
        ColResult colResult=new ColResult();
        colResult.isList=false;
        colResult.where=new int[]{1};
        colResult.colCnt=1;
        colResult.maxCol=1;
        return colResult;
    }

    public int[] getColCnt(String table) {
        int[] result = {1, 1};
        return result;
    }

    public String[] generateEntityJson(String content) {
        return generateEntityJsonArrays(content, ShareHolder.class);
    }

    private static final int MATCH_CNT = 3;

    @Override
    public HeaderInfo sniffHeader(String content, Class clazz) {
        HeaderInfo info = super.sniffHeader(content, clazz);

        // 补全最后的一个数量，因为通用解析头的时候，股东情况的之家冻结情况在表头分成了两行
        if (info != null && info.headers.size() > 0) {
            info.headers.add("freezAmount");
        }

        return info;
    }

    private static final String[] ShareHolderDataKeyWords = {
            "股东性质",
            "持股数量",
            "股份状态",
            "质押或冻结情况",
            "持有有限售条件的股份数量",
            "持有无限售条件的股份数量",
    };
}

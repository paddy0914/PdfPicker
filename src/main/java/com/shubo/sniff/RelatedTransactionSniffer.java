package com.shubo.sniff;

import com.shubo.annotation.Todd;
import com.shubo.exception.AnnotationException;

/**
 * Created by horseman on 2016/12/20.
 */
@Todd(key = "RelatedTransaction",
        index = 13,
        suffix = ".rt",
        folder = "关联交易",
        title = {"与日常经营相关的关联交易"})
public class RelatedTransactionSniffer extends Sniffer {
    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content, RelatedTransactionKeyWords, MATCH_CNT);
    }

    @Override
    public ColResult getColCnt(String table, Class clazz) {
        return null;
    }

    @Override
    public String[] generateEntityJson(String content) throws AnnotationException {
        return new String[0];
    }

    private static final int MATCH_CNT = 4;

    private static final String[] RelatedTransactionKeyWords = {
            "关联交易内容",
            "关联交易定价原则",
            "关联交易价格",
            "关联交易结算方式",
            "关联关系方",
            "关联交易类型",
    };
}

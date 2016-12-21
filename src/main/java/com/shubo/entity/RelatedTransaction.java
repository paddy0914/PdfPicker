package com.shubo.entity;

import java.util.List;

/**
 * Created by horseman on 2016/12/20.
 */
public class RelatedTransaction {
    public List<Item> transactions;
    public static class Item {
        public String 关联交易方;
        public String 关联关系;
        public String 关联交易类型;
        public String 关联交易内容;
        public String 关联交易定价原则;
        public String 关联交易价格;
        public String 关联交易金额万元;
        public String 占同类交易金额的比例;
        public String 关联交易结算方式;
        public String 市场价格;
        public String 披露日期;
    }
}

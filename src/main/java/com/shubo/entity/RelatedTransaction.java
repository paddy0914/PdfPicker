package com.shubo.entity;

import com.shubo.annotation.Horseman;

import java.util.List;

/**
 * Created by horseman on 2016/12/20.
 */
public class RelatedTransaction {
    @Horseman(keys={"关联交易方"})
    public String 关联交易方;
    @Horseman(keys={"关联关系"})
    public String 关联关系;
    @Horseman(keys={"关联交易类型"})
    public String 关联交易类型;
    @Horseman(keys={"关联交易内容"})
    public String 关联交易内容;
    @Horseman(keys={"关联交易定价原则"})
    public String 关联交易定价原则;
    @Horseman(keys={"关联交易价格"})
    public String 关联交易价格;
    @Horseman(keys={"关联交易金额（万元）"})
    public String 关联交易金额万元;
    @Horseman(keys={"占同类交易金额的比例(%)"})
    public String 占同类交易金额的比例;
    @Horseman(keys={"关联交易结算方式"})
    public String 关联交易结算方式;
    @Horseman(keys={"市场价格"})
    public String 市场价格;
    @Horseman(keys={"披露日期"})
    public String 披露日期;
    @Horseman(keys={"披露索引"})
    public String 披露索引;
}

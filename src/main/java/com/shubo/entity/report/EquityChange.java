package com.shubo.entity.report;

import com.shubo.annotation.Horseman;

/**
 * Created by liujinping on 2016/12/12.
 */
public class EquityChange {
    @Horseman(keys = {"实收资本或股本"})
    public String sszb;

    @Horseman(keys = {"优先股"})
    public String yxg;

    @Horseman(keys = {"永续债"})
    public String yxz;

    @Horseman(keys = {"资本公积"})
    public String zbgj;

    @Horseman(keys = {"减:库存股"})
    public String jkcg;

    @Horseman(keys = {"专项储备"})
    public String zxcb;

    @Horseman(keys = {"盈余公积"})
    public String yygj;

    @Horseman(keys = {"一般风险准备"})
    public String ybfxzb;

    @Horseman(keys = {"未分配利润"})
    public String wfplr;

    @Horseman(keys = {"其他"})
    public String qt;

    @Horseman(keys = {"少数股东权益"})
    public String ssgdqy;

    @Horseman(keys = {"所有者权益合计"})
    public String syzqyhj;
}

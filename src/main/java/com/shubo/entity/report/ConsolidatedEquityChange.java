package com.shubo.entity.report;

import com.shubo.annotation.Horseman;

/**
 * Created by horseman on 2016/11/25.
 */
public class ConsolidatedEquityChange {
    @Horseman(keys = {"一、上年期末余额"})
    public String ysnqmye;

    @Horseman(keys = {"加：会计政策变更"})
    public String jhjzcbg;

    @Horseman(keys = {"前期差错更正"})
    public String qqccgz;

    @Horseman(keys = {"同一控制下企业合并"})
    public String tykzxqyhb;

    @Horseman(keys = {"其他"})
    public String qt;

    @Horseman(keys = {"二、本年期初余额"})
    public String ebnqcye;

    @Horseman(keys = {"三、本期增减变动金额（减少以“－”号填列）"})
    public String sbqzjbdjejsyhtl;

    @Horseman(keys = {"（一）综合收益总额"})
    public String yzhsyze;

    @Horseman(keys = {"（二）所有者投入和减少资本"})
    public String esyztrhjszb;

    @Horseman(keys = {"1．股东投入的普通股"})
    public String gdtrdptg;

    @Horseman(keys = {"2．其他权益工具持有者投入资本"})
    public String qtqygjcyztrzb;

    @Horseman(keys = {"3．股份支付计入所有者权益的金额 "})
    public String gfzfjrsyzqydje;

    @Horseman(keys = {"4．其他"})
    public String qt1;

    @Horseman(keys = {"（三）利润分配"})
    public String slrfp;

    @Horseman(keys = {"1．提取盈余公积"})
    public String tqyygj;

    @Horseman(keys = {"2．提取一般风险准备"})
    public String tqybfxzb;

    @Horseman(keys = {"3．对所有者（或股东）的分配"})
    public String dsyzhgddfp;

    @Horseman(keys = {"4．其他"})
    public String qt2;

    @Horseman(keys = {"（四）所有者权益内部结转"})
    public String ssyzqynbjz;

    @Horseman(keys = {"1．资本公积转增资本（或股本）"})
    public String zbgjzzzbhgb;

    @Horseman(keys = {"2．盈余公积转增资本（或股本）"})
    public String yygjzzzbhgb;

    @Horseman(keys = {"3．盈余公积弥补亏损"})
    public String yygjmbks;

    @Horseman(keys = {"4．其他"})
    public String qt3;

    @Horseman(keys = {"（五）专项储备"})
    public String wzxcb;

    @Horseman(keys = {"1．本期提取"})
    public String bqtq;

    @Horseman(keys = {"2．本期使用"})
    public String bqsy;

    @Horseman(keys = {"（六）其他"})
    public String lqt;

    @Horseman(keys = {"四、本期期末余额"})
    public String sbqqmye;
}

package com.shubo.entity.report;

import com.shubo.annotation.Horseman;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by horseman on 2016/11/25.
 * 合并所有者权益变动表
 */
/*
public class ConsolidatedEquityChange {
    @Horseman(keys = {"一、上年期末余额"})
    public List<String> ysnqmye;

    @Horseman(keys = {"加：会计政策变更"})
    public List<String> jhjzcbg;

    @Horseman(keys = {"前期差错更正"})
    public List<String> qqccgz;

    @Horseman(keys = {"同一控制下企业合并"})
    public List<String> tykzxqyhb;

    @Horseman(keys = {"其他"})
    public List<String> qt;

    @Horseman(keys = {"二、本年期初余额"})
    public List<String> ebnqcye;

    @Horseman(similar = true, keys = {"三、本期增减变动金额（减少以“－”号填列）"})
    public List<String> sbqzjbdjejsyhtl;

    @Horseman(keys = {"（一）综合收益总额"})
    public List<String> yzhsyze;

    @Horseman(keys = {"（二）所有者投入和减少资本"})
    public List<String> esyztrhjszb;

    @Horseman(keys = {"1．股东投入的普通股"})
    public List<String> gdtrdptg;

    @Horseman(keys = {"2．其他权益工具持有者投入资本"})
    public List<String> qtqygjcyztrzb;

    @Horseman(keys = {"3．股份支付计入所有者权益的金额 "})
    public List<String> gfzfjrsyzqydje;

    @Horseman(keys = {"4．其他"})
    public List<String> qt1;

    @Horseman(keys = {"（三）利润分配"})
    public List<String> slrfp;

    @Horseman(keys = {"1．提取盈余公积"})
    public List<String> tqyygj;

    @Horseman(keys = {"2．提取一般风险准备"})
    public List<String> tqybfxzb;

    @Horseman(keys = {"3．对所有者（或股东）的分配"})
    public List<String> dsyzhgddfp;

    @Horseman(keys = {"4．其他"})
    public List<String> qt2;

    @Horseman(keys = {"（四）所有者权益内部结转"})
    public List<String> ssyzqynbjz;

    @Horseman(keys = {"1．资本公积转增资本（或股本）"})
    public List<String> zbgjzzzbhgb;

    @Horseman(keys = {"2．盈余公积转增资本（或股本）"})
    public List<String> yygjzzzbhgb;

    @Horseman(keys = {"3．盈余公积弥补亏损"})
    public List<String> yygjmbks;

    @Horseman(keys = {"4．其他"})
    public List<String> qt3;

    @Horseman(keys = {"（五）专项储备"})
    public List<String> wzxcb;

    @Horseman(keys = {"1．本期提取"})
    public List<String> bqtq;

    @Horseman(keys = {"2．本期使用"})
    public List<String> bqsy;

    @Horseman(keys = {"（六）其他"})
    public List<String> lqt;

    @Horseman(keys = {"四、本期期末余额"})
    public List<String> sbqqmye;
}
*/
/*
public class ConsolidatedEquityChange {
    @Horseman(keys = {"一、上年期末余额"})
    public List<HashMap<HashMap<Integer,String>,String>> ysnqmye;

    @Horseman(keys = {"加：会计政策变更"})
    public List<HashMap<HashMap<Integer,String>,String>> jhjzcbg;

    @Horseman(keys = {"前期差错更正"})
    public List<HashMap<HashMap<Integer,String>,String>> qqccgz;

    @Horseman(keys = {"同一控制下企业合并"})
    public List<HashMap<HashMap<Integer,String>,String>> tykzxqyhb;

    @Horseman(keys = {"其他"})
    public List<HashMap<HashMap<Integer,String>,String>> qt;

    @Horseman(keys = {"二、本年期初余额"})
    public List<HashMap<HashMap<Integer,String>,String>> ebnqcye;

    @Horseman(similar = true, keys = {"三、本期增减变动金额（减少以“－”号填列）"})
    public List<HashMap<HashMap<Integer,String>,String>> sbqzjbdjejsyhtl;

    @Horseman(keys = {"（一）综合收益总额"})
    public List<HashMap<HashMap<Integer,String>,String>> yzhsyze;

    @Horseman(keys = {"（二）所有者投入和减少资本"})
    public List<HashMap<HashMap<Integer,String>,String>> esyztrhjszb;

    @Horseman(keys = {"1．股东投入的普通股"})
    public List<HashMap<HashMap<Integer,String>,String>> gdtrdptg;

    @Horseman(keys = {"2．其他权益工具持有者投入资本"})
    public List<HashMap<HashMap<Integer,String>,String>> qtqygjcyztrzb;

    @Horseman(keys = {"3．股份支付计入所有者权益的金额 "})
    public List<HashMap<HashMap<Integer,String>,String>> gfzfjrsyzqydje;

    @Horseman(keys = {"4．其他"})
    public List<HashMap<HashMap<Integer,String>,String>> qt1;

    @Horseman(keys = {"（三）利润分配"})
    public List<HashMap<HashMap<Integer,String>,String>> slrfp;

    @Horseman(keys = {"1．提取盈余公积"})
    public List<HashMap<HashMap<Integer,String>,String>> tqyygj;

    @Horseman(keys = {"2．提取一般风险准备"})
    public List<HashMap<HashMap<Integer,String>,String>> tqybfxzb;

    @Horseman(keys = {"3．对所有者（或股东）的分配"})
    public List<HashMap<HashMap<Integer,String>,String>> dsyzhgddfp;

    @Horseman(keys = {"4．其他"})
    public List<HashMap<HashMap<Integer,String>,String>> qt2;

    @Horseman(keys = {"（四）所有者权益内部结转"})
    public List<HashMap<HashMap<Integer,String>,String>> ssyzqynbjz;

    @Horseman(keys = {"1．资本公积转增资本（或股本）"})
    public List<HashMap<HashMap<Integer,String>,String>> zbgjzzzbhgb;

    @Horseman(keys = {"2．盈余公积转增资本（或股本）"})
    public List<HashMap<HashMap<Integer,String>,String>> yygjzzzbhgb;

    @Horseman(keys = {"3．盈余公积弥补亏损"})
    public List<HashMap<HashMap<Integer,String>,String>> yygjmbks;

    @Horseman(keys = {"4．其他"})
    public List<HashMap<HashMap<Integer,String>,String>> qt3;

    @Horseman(keys = {"（五）专项储备"})
    public List<HashMap<HashMap<Integer,String>,String>> wzxcb;

    @Horseman(keys = {"1．本期提取"})
    public List<HashMap<HashMap<Integer,String>,String>> bqtq;

    @Horseman(keys = {"2．本期使用"})
    public List<HashMap<HashMap<Integer,String>,String>> bqsy;

    @Horseman(keys = {"（六）其他"})
    public List<HashMap<HashMap<Integer,String>,String>> lqt;

    @Horseman(keys = {"四、本期期末余额"})
    public List<HashMap<HashMap<Integer,String>,String>> sbqqmye;
}
*/
public class ConsolidatedEquityChange {
    @Horseman(keys = {"一、上年期末余额", "一、上年年末余额"})
    public EquityChange ysnqmye;

    @Horseman(keys = {"加：会计政策变更"})
    public EquityChange jhjzcbg;

    @Horseman(keys = {"前期差错更正"})
    public EquityChange qqccgz;

    @Horseman(keys = {"同一控制下企业合并"})
    public EquityChange tykzxqyhb;

    @Horseman(keys = {"其他"})
    public EquityChange qt;

    @Horseman(keys = {"二、本年期初余额"})
    public EquityChange ebnqcye;

    @Horseman(similar = true, keys = {"三、本期增减变动金额（减少以“－”号填列）"})
    public EquityChange sbqzjbdjejsyhtl;

    @Horseman(keys = {"（一）综合收益总额"})
    public EquityChange yzhsyze;

    @Horseman(keys = {"（二）所有者投入和减少资本"})
    public EquityChange esyztrhjszb;

    @Horseman(keys = {"1．股东投入的普通股"})
    public EquityChange gdtrdptg;

    @Horseman(keys = {"2．其他权益工具持有者投入资本"})
    public EquityChange qtqygjcyztrzb;

    @Horseman(keys = {"3．股份支付计入所有者权益的金额 "})
    public EquityChange gfzfjrsyzqydje;

    @Horseman(keys = {"4．其他"})
    public EquityChange qt1;

    @Horseman(keys = {"（三）利润分配"})
    public EquityChange slrfp;

    @Horseman(keys = {"1．提取盈余公积"})
    public EquityChange tqyygj;

    @Horseman(keys = {"2．提取一般风险准备"})
    public EquityChange tqybfxzb;

    @Horseman(keys = {"3．对所有者（或股东）的分配"})
    public EquityChange dsyzhgddfp;

    @Horseman(keys = {"4．其他"})
    public EquityChange qt2;

    @Horseman(keys = {"（四）所有者权益内部结转"})
    public EquityChange ssyzqynbjz;

    @Horseman(keys = {"1．资本公积转增资本（或股本）"})
    public EquityChange zbgjzzzbhgb;

    @Horseman(keys = {"2．盈余公积转增资本（或股本）"})
    public EquityChange yygjzzzbhgb;

    @Horseman(keys = {"3．盈余公积弥补亏损"})
    public EquityChange yygjmbks;

    @Horseman(keys = {"4．其他"})
    public EquityChange qt3;

    @Horseman(keys = {"（五）专项储备"})
    public EquityChange wzxcb;

    @Horseman(keys = {"1．本期提取"})
    public EquityChange bqtq;

    @Horseman(keys = {"2．本期使用"})
    public EquityChange bqsy;

    @Horseman(keys = {"（六）其他"})
    public EquityChange lqt;

    @Horseman(keys = {"四、本期期末余额"})
    public EquityChange sbqqmye;
}


package com.shubo.entity.report;

import com.shubo.annotation.Horseman;

import java.util.List;

/**
 * Created by horseman on 2016/11/25.
 * 合并资产负债表
 */
public class ConsolidatedBalanceSheet {
    @Horseman(keys = {"流动资产："})
    public List<String> ldzc;

    @Horseman(keys = {"货币资金"})
    public List<String> hbzj;

    @Horseman(keys = {"结算备付金"})
    public List<String> jsbfj;

    @Horseman(keys = {"拆出资金"})
    public List<String> cczj;

    @Horseman(keys = {"以公允价值计量且其变动计入当期损益的金融资产"})
    public List<String> ygyjzjlqqbdjrdqsydjrzc;

    @Horseman(keys = {"衍生金融资产"})
    public List<String> ysjrzc;

    @Horseman(keys = {"应收票据"})
    public List<String> yspj;

    @Horseman(keys = {"应收账款"})
    public List<String> yszk;

    @Horseman(keys = {"预付款项"})
    public List<String> yfkx;

    @Horseman(keys = {"应收保费"})
    public List<String> ysbf;

    @Horseman(keys = {"应收分保账款"})
    public List<String> ysfbzk;

    @Horseman(keys = {"应收分保合同准备金"})
    public List<String> ysfbhtzbj;

    @Horseman(keys = {"应收利息"})
    public List<String> yslx;

    @Horseman(keys = {"应收股利"})
    public List<String> ysgl;

    @Horseman(keys = {"其他应收款"})
    public List<String> qtysk;

    @Horseman(keys = {"买入返售金融资产"})
    public List<String> mrfsjrzc;

    @Horseman(keys = {"存货"})
    public List<String> ch;

    @Horseman(keys = {"划分为持有待售的资产"})
    public List<String> hfwcydsdzc;

    @Horseman(keys = {"一年内到期的非流动资产"})
    public List<String> ynndqdfldzc;

    @Horseman(keys = {"其他流动资产"})
    public List<String> qtldzc;

    @Horseman(keys = {"流动资产合计"})
    public List<String> ldzchj;

    @Horseman(keys = {"非流动资产："})
    public List<String> fldzc;

    @Horseman(keys = {"发放贷款及垫款","发放委托贷款及垫款"})
    public List<String> ffdkjdk;

    @Horseman(keys = {"发放委托贷款及垫款"})
    public List<String> ffwtdkjdk;

    @Horseman(keys = {"可供出售金融资产"})
    public List<String> kgcsjrzc;

    @Horseman(keys = {"持有至到期投资"})
    public List<String> cyzdqtz;

    @Horseman(keys = {"长期应收款"})
    public List<String> cqysk;

    @Horseman(keys = {"长期股权投资"})
    public List<String> cqgqtz;

    @Horseman(keys = {"投资性房地产"})
    public List<String> tzxfdc;

    @Horseman(keys = {"固定资产"})
    public List<String> gdzc;

    @Horseman(keys = {"在建工程"})
    public List<String> zjgc;

    @Horseman(keys = {"工程物资"})
    public List<String> gcwz;

    @Horseman(keys = {"固定资产清理"})
    public List<String> gdzcql;

    @Horseman(keys = {"生产性生物资产"})
    public List<String> scxswzc;

    @Horseman(keys = {"油气资产"})
    public List<String> yqzc;

    @Horseman(keys = {"无形资产"})
    public List<String> wxzc;

    @Horseman(keys = {"开发支出"})
    public List<String> kfzc;

    @Horseman(keys = {"商誉"})
    public List<String> sy;

    @Horseman(keys = {"长期待摊费用"})
    public List<String> cqdtfy;

    @Horseman(keys = {"递延所得税资产"})
    public List<String> dysdszc;

    @Horseman(keys = {"其他非流动资产"})
    public List<String> qtfldzc;

    @Horseman(keys = {"非流动资产合计"})
    public List<String> fldzchj;

    @Horseman(keys = {"资产总计"})
    public List<String> zczj;

    @Horseman(keys = {"流动负债："})
    public List<String> ldfz;

    @Horseman(keys = {"短期借款"})
    public List<String> dqjk;

    @Horseman(keys = {"向中央银行借款"})
    public List<String> xzyyxjk;

    @Horseman(keys = {"吸收存款及同业存放"})
    public List<String> xsckjtycf;

    @Horseman(keys = {"拆入资金"})
    public List<String> crzj;

    @Horseman(keys = {"以公允价值计量且其变动计入当期损益的金融负债"})
    public List<String> ygyjzjlqqbdjrdqsydjrfz;

    @Horseman(keys = {"衍生金融负债"})
    public List<String> ysjrfz;

    @Horseman(keys = {"应付票据"})
    public List<String> yfpj;

    @Horseman(keys = {"应付账款"})
    public List<String> yfzk;

    @Horseman(keys = {"预收款项"})
    public List<String> yskx;

    @Horseman(keys = {"卖出回购金融资产款"})
    public List<String> mchgjrzck;

    @Horseman(keys = {"应付手续费及佣金"})
    public List<String> yfsxfjyj;

    @Horseman(keys = {"应付职工薪酬"})
    public List<String> yfzgxc;

    @Horseman(keys = {"应交税费"})
    public List<String> yjsf;

    @Horseman(keys = {"应付利息"})
    public List<String> yflx;

    @Horseman(keys = {"应付股利"})
    public List<String> yfgl;

    @Horseman(keys = {"其他应付款"})
    public List<String> qtyfk;

    @Horseman(keys = {"应付分保账款"})
    public List<String> yffbzk;

    @Horseman(keys = {"保险合同准备金"})
    public List<String> bxhtzbj;

    @Horseman(keys = {"代理买卖证券款"})
    public List<String> dlmmzqk;

    @Horseman(keys = {"代理承销证券款"})
    public List<String> dlcxzqk;

    @Horseman(keys = {"划分为持有待售的负债"})
    public List<String> hfwcydsdfz;

    @Horseman(keys = {"一年内到期的非流动负债"})
    public List<String> ynndqdfldfz;

    @Horseman(keys = {"其他流动负债"})
    public List<String> qtldfz;

    @Horseman(keys = {"流动负债合计"})
    public List<String> ldfzhj;

    @Horseman(keys = {"非流动负债："})
    public List<String> fldfz;

    @Horseman(keys = {"长期借款"})
    public List<String> cqjk;

    @Horseman(keys = {"应付债券"})
    public List<String> yfzq;

    @Horseman(keys = {"其中：优先股"})
    public List<String> yfzqqzyxg;

    @Horseman(keys = {"永续债"})
    public List<String> yfzqyxz;

    @Horseman(keys = {"长期应付款"})
    public List<String> cqyfk;

    @Horseman(keys = {"长期应付职工薪酬"})
    public List<String> cqyfzgxc;

    @Horseman(keys = {"专项应付款"})
    public List<String> zxyfk;

    @Horseman(keys = {"预计负债"})
    public List<String> yjfz;

    @Horseman(keys = {"递延收益"})
    public List<String> dysy;

    @Horseman(keys = {"递延所得税负债"})
    public List<String> dysdsfz;

    @Horseman(keys = {"其他非流动负债"})
    public List<String> qtfldfz;

    @Horseman(keys = {"非流动负债合计"})
    public List<String> fldfzhj;

    @Horseman(keys = {"负债合计"})
    public List<String> fzhj;

    @Horseman(keys = {"所有者权益：","所有者权益（或股东权益）："})
    public List<String> syzqy;

    @Horseman(keys = {"股本"})
    public List<String> gb;

    @Horseman(keys = {"其他权益工具"})
    public List<String> qtqygj;

    @Horseman(keys = {"其中：优先股"})
    public List<String> qtqygjqzyxg;

    @Horseman(keys = {"永续债"})
    public List<String> qtqygjyxz;

    @Horseman(keys = {"资本公积"})
    public List<String> zbgj;

    @Horseman(keys = {"减：库存股"})
    public List<String> jkcg;

    @Horseman(keys = {"其他综合收益"})
    public List<String> qtzhsy;

    @Horseman(keys = {"专项储备"})
    public List<String> zxcb;

    @Horseman(keys = {"盈余公积"})
    public List<String> yygj;

    @Horseman(keys = {"一般风险准备"})
    public List<String> ybfxzb;

    @Horseman(keys = {"未分配利润"})
    public List<String> wfplr;

    @Horseman(keys = {"归属于母公司所有者权益合计"})
    public List<String> gsymgssyzqyhj;

    @Horseman(keys = {"少数股东权益"})
    public List<String> ssgdqy;

    @Horseman(keys = {"所有者权益合计","所有者权益（或股东权益）合计"})
    public List<String> syzqyhj;

    @Horseman(keys = {"负债和所有者权益总计","负债和所有者权益（或股东权益）总计"})
    public List<String> fzhsyzqyzj;

    @Horseman(keys = {"交易性金融资产"})
    public List<String> jyxjrzc;

    @Horseman(keys = {"交易性金融负债"})
    public List<String> jyxjrfz;

    @Horseman(keys = {"实收资本（或股本）"})
    public List<String> sszb;

    @Horseman(keys = {"外币报表折算差额"})
    public List<String> wbbbzsce;


}

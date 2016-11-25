package com.shubo.entity;

import com.shubo.annotation.Horseman;

/**
 * Created by horseman on 2016/11/22.
 * 非经常性损益
 * Non recurring gains and losses
 * 简称 Nrgal !!
 */
public class Nrgal {
    // 非流动资产处置损益
    @Horseman(keys = {"减：处置非流动资产收益",
            "非流动资产处置损益",
            "处置非流动资产净收益",
            "非流动性资产处置损益",
            "处置非流动资产净损失",
            "处置非流动资产净收益（损失）",
            "非流动性资产处置损益，包括已计提资产减值准备的冲销部分",
            "非流动性资产处置损益（固定资产、抵债资产、长期股权投资处置损益）"})
    public String ncadgal;

    // 交易性金融负债产生的公允价值变动损益，以及处置交易性金融负债和可供出售金融资产取得的投资收益
    @Horseman(keys = {
            "交易价格显失公允的交易产生的超过公允价值部分的损益",
            "交易性金融负债产生的公允价值变动损益，以及处置交易性金融负债和可供出售金融资产取得的投资收益"})
    public String whatthehell;

    // 出售、处理部门或投资单位收益
    @Horseman(keys = {"出售、处理部门或投资单位收益"})
    public String incomeByDisposalOrInvest;

    // 或有事项产生的损失
    @Horseman(keys = {"或有事项产生的损失",
            "或有事项产生的损益",
            "或有事项产生的损益（预计负债）"})
    public String somethingLoss;

    @Horseman(keys = {"除上述各项以外的其他营业外收入和支出",
            "除了上述以外的营业外收支净额",
            "除上述以外的营业外收支净额",
            "除上述各项之外的其他营业外收支净额",
            "除上述各项之外的其他营业外收入和支出净额",
            "除上述各项之外的其他营业外收入和支出；",
            "除上述各项之外的其他营业外收支净额",
            "除上述各项之外的其他营业外收支等",
            "除上述各项之外的其他营业外净收入",
            "除上述各项之外的其他营业外收入和支出",
            "除上述各项之外的其它营业外收入和支出",
            "除上述各项之外的营业外收入和支出"})
    public String others;

    @Horseman(keys = {
            "减：所得税的影响数",
            "减：非经常性损益的所得税影响数",
            "减：非经常性损益对所得税的影响数",
            "减：所得税费用",
            "以上调整对所得税的影响",
            "所得税的影响",
            "所得税的影响数",
            "所得税影响额(减项)",
            "所得税影响",
            "所得税影响数",
            "所得税影响金额",
            "所得税影响额"})
    public String incomeTaxEffect;

    @Horseman(keys = {
            "减：非经常性损益对少数股东权益影响数",
            "减：非经常性损益对少数股东损益的影响数",
            "减：归属于少数股东的非经常性损益",
            "减：少数股东损益的影响数",
            "减：少数股东影响数",
            "少数股东损益",
            "少数股东承担部分",
            "少数股东损益影响",
            "少数股东权益影响额"})
    public String shareholderImpact;

    @Horseman(keys = {
            "其他符合非经常性损益定义的损益项目",
    })
    public String qtfudyxm;

    @Horseman(keys = {"长期股权投资处置损益",
            "处置长期股权投资收益",
            "处置股权投资损益"})
    public String cqgqtzczsy;

    @Horseman(keys = {"合计"})
    public String total;
}

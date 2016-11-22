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
    @Horseman(keys={"非流动资产处置损益"})
    public String ncadgal;

    // 交易性金融负债产生的公允价值变动损益，以及处置交易性金融负债和可供出售金融资产取得的投资收益
    @Horseman(keys={"非流动资产处置损益"})
    public String whatthehell;

    // 出售、处理部门或投资单位收益
    @Horseman(keys={"出售、处理部门或投资单位收益"})
    public String incomeByDisposalOrInvest;

    // 非流动性资产处置损益
    @Horseman(keys={"非流动性资产处置损益"})
    public String fldxzcczsy;

    // 或有事项产生的损失
    @Horseman(keys={"或有事项产生的损失"})
    public String somethingLoss;

    // 除上述各项之外的其他营业外收入和支出
    @Horseman(keys={"除上述各项以外的其他营业外收入和支出"})
    public String others;

    // 所得税影响
    @Horseman(keys={"以上调整对所得税的影响", "所得税影响"})
    public String incomeTaxEffect;

    // 少数股东损益影响
    @Horseman(keys={"少数股东承担部分", "少数股东损益影响"})
    public String shareholderImpact;

    // 合计
    @Horseman(keys={"合计"})
    public String total;
}

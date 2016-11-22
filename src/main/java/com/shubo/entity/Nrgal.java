package com.shubo.entity;

/**
 * Created by horseman on 2016/11/22.
 * 非经常性损益
 * Non recurring gains and losses
 * 简称 Nrgal !!
 */
public class Nrgal {
    // 非流动资产处置损益
    String ncadgal;

    // 交易性金融负债产生的公允价值变动损益，以及处置交易性金融负债和可供出售金融资产取得的投资收益
    String whatthehell;

    // 出售、处理部门或投资单位收益
    String incomeByDisposalOrInvest;

    // 除上述各项之外的其他营业外收入和支出
    String others;

    // 所得税影响
    String incomeTaxEffect;

    // 少数股东损益影响
    String shareholderImpact;

    // 合计
    String total;
}

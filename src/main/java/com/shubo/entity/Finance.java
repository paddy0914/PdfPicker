package com.shubo.entity;

import com.shubo.annotation.Horseman;

/**
 * Created by horseman on 2016/11/22.
 */
public class Finance {

    // 股本
    @Horseman(keys={"股本"})
    public String equity;

    // 资产总额
    @Horseman(keys={"资产总额", "总资产"})
    public String totalAssets;

    // 营业收入
    @Horseman(keys={"营业收入"})
    public String operationRevenue;

    // 营业利润
    @Horseman(keys={"营业利润"})
    public String operationProfits;

    // 净利润
    @Horseman(keys={"净利润"})
    public String netProfits;

    // 利润总额
    @Horseman(keys={"利润总额"})
    public String totalProfits;

    // 归属于母公司股东的净利润
    @Horseman(keys={"归属于母公司股东的净利润"})
    public String shareholderMotherNetProfits;

    // 归属于母公司股东的扣除非经常性损益后的净利润
    @Horseman(keys={"归属于母公司股东的扣除非经常性损益后的净利润"})
    public String gsymgsgddkcfjcxsyhdjlr;

    // 归属于上市公司股东的股东权益
    @Horseman(keys={"归属于上市公司股东的股东权益"})
    public String shareholderEquity;

    // 归属于上市公司股东的每股净资产
    @Horseman(keys={"归属于上市公司股东的每股净资产"})
    public String shareholderNetAssetsPerShare;

    // 归属于母公司股东权益
    @Horseman(keys={"归属于母公司股东权益"})
    public String shareholderMotherEquity;

    // 归属于母公司股东的每股净资产（元）
    @Horseman(keys={"归属于母公司股东的每股净资产（元）"})
    public String shareholderMotherNetAssetsPerShare;

    // 归属于上市公司股东的净利润
    @Horseman(keys={"归属于上市公司股东的净利润"})
    public String shareholderProfits;

    // 经营活动产生的现金流量净额
    @Horseman(keys={"经营活动产生的现金流量净额"})
    public String operationActivityNetCashFlows;

    // 每股经营活动产生的现金流量净额（元）
    @Horseman(keys={"每股经营活动产生的现金流量净额（元）"})
    public String operationActivityNetCashFlowsPerShare;

    // 基本每股收益
    @Horseman(keys={"基本每股收益", "基本每股收益（元）"})
    public String basicEarningsPerShare;

    // 基本每股收益
    @Horseman(keys={"扣除非经常性损益后的基本每股收益（元）"})
    public String basicEarningsPerShareOfDNRGAL;

    // 扣除非经常性损益后的加权平均净资产收益率（未年化）
    @Horseman(keys={"扣除非经常性损益后的加权平均净资产收益率（未年化）"})
    public String qcfjcxsyhdjqpjjzcsylwnh;

    // 扣除非经常性损益后的加权平均净资产收益率（年化）
    @Horseman(keys={"扣除非经常性损益后的加权平均净资产收益率（年化）"})
    public String qcfjcxsyhdjqpjjzcsylnh;

    // 平均总资产利润率（未年化）
    @Horseman(keys={"平均总资产利润率（未年化）"})
    public String averageTotalAssetsProfitMarginNotYears;

    // 平均总资产利润率（年化）
    @Horseman(keys={"平均总资产利润率（年化）"})
    public String averageTotalAssetsProfitMarginYears;

    // 全面摊薄净资产收益率（未年化）
    @Horseman(keys={"全面摊薄净资产收益率（未年化）"})
    public String qmtbjzcsylwnh;

    // 全面摊薄净资产收益率（年化）
    @Horseman(keys={"全面摊薄净资产收益率（年化）"})
    public String qmtbjzcsylnh;

    // 加权平均净资产收益率（未年化）
    @Horseman(keys={"加权平均净资产收益率（未年化）"})
    public String jqpjjzcsylwnh;

    // 加权平均净资产收益率（年化）
    @Horseman(keys={"加权平均净资产收益率（年化）"})
    public String jqpjjzcsylnh;

    // 稀释每股收益
    @Horseman(keys={"稀释每股收益", "稀释每股收益（元）"})
    public String dilutedEarningsPerShare;

    // 全面摊薄净资产收益率
    @Horseman(keys={"全面摊薄净资产收益率"})
    public String fullyDilutedReturnOnNetAssets;

    // 加权平均净资产收益率
    @Horseman(keys={"加权平均净资产收益率"})
    public String weightedAverageRateReturnOnNetAssets;

    // 负债总额
    @Horseman(keys={"负债总额"})
    public String totalDebt;

    @Horseman(keys={"资产减值损失"})
    public String assetsDevaluation;

    @Horseman(keys={"资产减值损失前营业利润"})
    public String operationProfitsBeforeAssetsDevaluation;
}

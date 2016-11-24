package com.shubo.entity;

import com.shubo.annotation.Horseman;

/**
 * Created by horseman on 2016/11/22.
 */
public class Finance {

    /*
     *  股东权益 = 净资产
     *  股东权益 != 归属于普通股股东的股东权益
     */

    @Horseman(keys={"股本"})
    public String equity;

    // 资产总额
    @Horseman(keys={"资产总额", "总资产", "总资产（元）"})
    public String totalAssets;

    // 营业收入
    @Horseman(keys={"营业收入", "营业收入（元）"})
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

    @Horseman(keys={"归属于上市公司股东的扣除非经常性损益的净利润（元）",
                    "归属于上市公司股东的扣除非经常性损益后的净利润",
                    "扣除非经常性损益后的净利润"})
    public String gsyssgsgddkcfjxxsydjlr;

    // 归属于上市公司股东的股东权益
    @Horseman(keys={"归属于上市公司股东的股东权益",
            "归属于上市公司股东的净资产（元）",
            "所有者权益（或股东权益）",
            "股东权益",
            "归属于上市公司股东的所有者权益"})
    public String shareholderEquity;

    @Horseman(keys={"归属于普通股股东的股东权益"})
    public String normalShareholderEquity;

    // 归属于上市公司股东的每股净资产
    @Horseman(keys={"归属于上市公司股东的每股净资产",
            "每股净资产(元)",
            "每股净资产（元）",
            "归属于普通股股东的每股净资产（元）"})
    public String shareholderNetAssetsPerShare;

    // 归属于母公司股东权益
    @Horseman(keys={"归属于母公司股东权益",
                    "归属于母公司股东的权益"})
    public String shareholderMotherEquity;

    // 归属于母公司股东的每股净资产（元）
    @Horseman(keys={"归属于母公司股东的每股净资产（元）",
                    "归属于母公司股东的每股净资产(元)"})
    public String shareholderMotherNetAssetsPerShare;

    // 归属于上市公司股东的净利润
    @Horseman(keys={"归属于上市公司股东的净利润", "归属于上市公司股东的净利润（元）"})
    public String shareholderProfits;

    // 经营活动产生的现金流量净额
    @Horseman(keys={"经营活动产生的现金流量净额", "经营活动产生的现金流量净额（元）"})
    public String operationActivityNetCashFlows;

    // 每股经营活动产生的现金流量净额（元）
    @Horseman(keys={"每股经营活动产生的现金流量净额（元）",
                    "每股经营活动产生的现金流量净额"})
    public String operationActivityNetCashFlowsPerShare;

    @Horseman(keys={"基本每股收益", "基本每股收益（元）", "基本每股收益（元/股）"})
    public String basicEarningsPerShare;

    @Horseman(keys={"稀释每股收益", "稀释每股收益（元）", "稀释每股收益（元/股）"})
    public String dilutedEarningsPerShare;

    @Horseman(keys={"扣除非经常性损益后的稀释每股收益（元）"})
    public String dilutedEarningsPerShareExcludeNrgal;

    @Horseman(keys={"扣除非经常性损益后的基本每股收益（元）",
                    "扣除非经常性损益后的基本每股收益"})
    public String basicEarningsPerShareOfDNRGAL;

    @Horseman(keys={"扣除非经常性损益后的加权平均净资产收益率",
            "扣除非经常性损益后的平均总资产收益率"})
    public String qcfjcxsyhdjqpjjzcsyl;

    @Horseman(keys={"扣除非经常性损益后的加权平均净资产收益率（未年化）",
                    "扣除非经常性损益后的平均总资产收益率（未年化）"})
    public String qcfjcxsyhdjqpjjzcsylwnh;

    @Horseman(keys={"扣除非经常性损益后的加权平均净资产收益率（年化）"})
    public String qcfjcxsyhdjqpjjzcsylnh;

    // 平均总资产利润率（未年化）
    @Horseman(keys={"平均总资产利润率（未年化）", "平均总资产收益率（未年化）"})
    public String averageTotalAssetsProfitMarginNotYears;


    // 平均总资产利润率（年化）
    @Horseman(keys={"平均总资产利润率（年化）", "平均总资产收益率（年化）"})
    public String averageTotalAssetsProfitMarginYears;

    @Horseman(keys={"净资产收益率（未经年化）"})
    public String jzcyylwnh;

    @Horseman(keys={"全面摊薄净资产收益率(％)"})
    public String qmtbjzcsyl;

    // 全面摊薄净资产收益率（未年化）
    @Horseman(keys={"全面摊薄净资产收益率（未年化）"})
    public String qmtbjzcsylwnh;

    // 全面摊薄净资产收益率（年化）
    @Horseman(keys={"全面摊薄净资产收益率（年化）"})
    public String qmtbjzcsylnh;

    // 扣除非经常性损益后的全面摊薄净资产收益率
    @Horseman(keys={"扣除非经常性损益后的全面摊薄净资产收益率",
                    "扣除非经常性损益后全面摊薄净资产收益率"})
    public String qcfjcxsyhdqmtbjzcsyl;

    // 扣除非经常性损益后的全面摊薄净资产收益率（未年化）
    @Horseman(keys={"扣除非经常性损益后的全面摊薄净资产收益率（未年化）"})
    public String qcfjcxsyhdqmtbjzcsylwnh;

    // 扣除非经常性损益后的全面摊薄净资产收益率（年化）
    @Horseman(keys={"扣除非经常性损益后的全面摊薄净资产收益率（年化）"})
    public String qcfjcxsyhdqmtbjzcsylnh;

    // 加权平均净资产收益率（未年化）
    @Horseman(keys={"加权平均净资产收益率（未年化）"})
    public String jqpjjzcsylwnh;

    // 加权平均净资产收益率（年化）
    @Horseman(keys={"加权平均净资产收益率（年化）"})
    public String jqpjjzcsylnh;

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

    @Horseman(keys={"资产减值准备"})
    public String zcjzzb;

    @Horseman(keys={"拨备前营业利润"})
    public String pbqyylr;

    @Horseman(keys={"资产减值损失前营业利润"})
    public String operationProfitsBeforeAssetsDevaluation;
}

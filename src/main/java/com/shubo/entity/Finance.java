package com.shubo.entity;

import com.shubo.annotation.Horseman;

/**
 * Created by horseman on 2016/11/22.
 */
public class Finance {

    /*
     *  股东权益 = 净资产
     *  股东权益 != 归属于普通股股东的股东权益
     *  第一季度每股收益=基本每股收益。第二季度基本每股收益=第一季度每股收益+第二季度每股收益,以此类推
     */

    @Horseman(keys = {"股本",
            "股本（股）",
            "总股本",
            "总股本（股）"})
    public String equity;

    // 资产总额
    @Horseman(keys = {"资产总额",
            "总资产",
            "总资产（元）",
            "总资产（千元）",
            "总资产（人民币千元）",
            "资产总额（元）"})
    public String totalAssets;

    // 营业收入
    @Horseman(keys = {"营业收入",
            "营业收入（元）",
            "营业收入（元）",
            "营业总收入（元）",
            "营业收入（人民币千元）",
            "营业总收入",
            "营业收入（千元）"})
    public String operationRevenue;

    @Horseman(keys = {"营业利润",
            "报告期利润",
            "营业利润（元）"})
    public String operationProfits;

    // 净利润
    @Horseman(keys = {"净利润",
            "归属于公司普通股股东的净利润",
            "归属于公司股东的净利润"})
    public String netProfits;

    // 利润总额
    @Horseman(keys = {"利润总额",
            "利润总额（千元）",
            "利润总额（元）"})
    public String totalProfits;

    @Horseman(keys = {"归属于母公司股东的净利润",
            "归属于母公司所有者净利润",
            "归属于母公司所有者的净利润"})
    public String shareholderMotherNetProfits;

    @Horseman(keys = {"归属于母公司股东的扣除非经常性损益后的净利润",
            "归属于母公司股东扣除非经常性损益后的净利润"})
    public String gsymgsgddkcfjcxsyhdjlr;

    @Horseman(keys = {"归属于上市公司股东的扣除非经常性损益的净利润（元）",
            "归属于上市公司股东的扣除非经常性损益的净利润（元",
            "归属于上市公司股东的扣除非经常性损益的净利润（元",
            "扣除非经常性损益后的归属于上市公司股东净利润",
            "归属于上市公司股东的扣除非经常性损益后的净利润（元）",
            "归属于上市公司股东的扣除非经常性损益的净利润",
            "归属于上市公司股东扣除非经常性损益后的净利润",
            "扣除非经常性损益后归属于公司普通股股东的净利润",
            "归属于公司股东的扣除非经常性损益后的净利润",
            "归属于上市公司股东的扣除非经常性损益后的净利润",
            "归属于上市公司股东的扣除非经常性损益的净利润（人民币千元）",
            "归属于上市公司股东的扣除非经常性损益后的净利润（千元）",
            "归属于上市公司股东的扣除非经常性损益的净利润（千元）",
            "扣除非经常性损益后归属于上市公司股东的净利润",
            "扣除非经常性损益后的净利润"})
    public String gsyssgsgddkcfjxxsydjlr;

    // 归属于上市公司股东的股东权益
    @Horseman(keys = {"归属于上市公司股东的股东权益",
            "归属于上市公司股东的净资产（归属于上市公司股东的所有者权益）（元）",
            "归属于上市公司股东的净资产（人民币千元）",
            "归属于上市公司股东的净资产（元）",
            "归属于上市公司股东的净资产（元",
            "归属于上市公司股东的净资产（归属于上市公司股东的所有者权益）（元",
            "股东权益(不含少数股东权益)",
            "归属于上市公司股东的净资产",
            "归属上市公司股东权益",
            "所有者权益（股东权益）",
            "归属上市公司股东的股东权益",
            "归属于上市公司的所有者权益",
            "归属于上市公司的股东权益",
            "所有者权益（或股东权益）",
            "归属于上市公司股东的净资产（千元）",
            "所有者权益（或股东权益）（千元）",
            "所有者权益",
            "股东权益",
            "归属于上市公司股东的所有者权益",
            "归属于上市公司股东的所有者权益（元",
            "归属于上市公司股东的所有者权益（元）"})
    public String shareholderEquity;

    @Horseman(keys = {"归属于普通股股东的股东权益"})
    public String normalShareholderEquity;

    @Horseman(keys = {"用最新股本计算的每股收益（元/股）"})
    public String xgbjsmgsy;

    @Horseman(keys = {"用最新股本计算的基本每股收益（元/股）"})
    public String xgbjsjbmgsy;

    @Horseman(keys = {"归属于上市公司股东的每股净资产",
            "每股净资产(元)",
            "每股净资产（元）",
            "每股净资产",
            "归属于上市公司的每股净资产",
            "归属于普通股股东的每股净资产（元）",
            "归属于上市公司股东的每股净资产（人民币元/股）",
            "归属于上市公司股东的每股净资产（元/股）",
            "归属于上市公司股东的每股净资产（元/股"})
    public String shareholderNetAssetsPerShare;

    // 归属于母公司股东权益
    @Horseman(keys = {"归属于母公司股东权益",
            "归属于母公司所有者权益",
            "归属于母公司的股东权益",
            "归属于母公司股东权益（或股东权益）",
            "归属于母公司的所有者权益（或股东权益）",
            "所有者权益（归属于母公司股东的权益）",
            "所有者权益（归属于母公司股东权益）",
            "归属于母公司所有者权益（或股东权益）",
            "归属于母公司股东的权益"})
    public String shareholderMotherEquity;

    @Horseman(keys = {"归属于母公司股东的每股净资产（元）",
            "归属于母公司所有者的每股净资产",
            "归属于母公司股东的每股净资产",
            "归属于母公司的每股净资产",
            "归属于母公司股东的每股净资产(元)"})
    public String shareholderMotherNetAssetsPerShare;

    @Horseman(keys = {
            "归属于上市公司股东的净利润",
            "归属于上市公司股东净利润",
            "归属上市公司股东的净利润（元）",
            "归属于上市公司股东的净利润（元）",
            "归属于上市公司股东的净利润（元",
            "归属于上市公司股东的净利润（人民币千元）",
            "归属于上市公司股东的净利润（千元）"})
    public String shareholderProfits;

    @Horseman(keys = {"经营活动产生的现金流量净额",
            "经营活动产生的现金流量净额（人民币千元）",
            "经营活动产生的现金流量净额（元）",
            "经营活动产生的现金流量净额（元"})
    public String operationActivityNetCashFlows;

    @Horseman(keys = {"每股经营活动产生的现金流量净额（元）",
            "每股经营活动产生的现金流量净额（人民币元╱股）",
            "每股经营活动产生的现金流量净额",
            "每股经营活动产生的现金流量净额（元/",
            "经营活动产生的现金流量净额（千元）",
            "每股经营活动产生的现金流量净额（元/股）",
            "每股经营活动产生的现金流量净额（元/股"})
    public String operationActivityNetCashFlowsPerShare;

    @Horseman(keys = {"每股收益",
            "每股收益(元)",
            "归属于上市公司股东的每股收益"})
    public String basicEarningsPerShare;

    @Horseman(keys = {"基本每股收益",
            "归属于上市公司股东的基本每股收益（人民币元╱股）",
            "基本每股收益（元／股）",
            "基本每股收益（元/股",
            "基本每股收益（元）",
            "基本每股收益（元/股）"})
    public String earningsPerShare;

    @Horseman(keys = {"稀释每股收益",
            "归属于上市公司股东的稀释每股收益（人民币元╱股）",
            "稀释性每股收益",
            "稀释每股收益（元）",
            "稀释每股收益（元/股",
            "稀释每股收益（元／股）",
            "稀释每股收益（元/股）"})
    public String dilutedEarningsPerShare;

    @Horseman(keys = {"扣除非经常性损益后的稀释每股收益（元）"})
    public String dilutedEarningsPerShareExcludeNrgal;

    @Horseman(keys = {"扣除非经常性损益后的基本每股收益（元）",
            "扣除非经常性损益后的每股收益",
            "扣除非经常性损益后的基本每股收益（元/股）",
            "扣除非经常性损益后的基本每股收益"})
    public String basicEarningsPerShareOfDNRGAL;

    @Horseman(keys = {"扣除非经常性损益后的平均净资产收益率（未年化）"})
    public String qcfjcxsyhdpjjzcsylwnh;

    @Horseman(keys = {"扣除非经常性损益后的加权平均净资产收益率",
            "扣除非经常性损益后加权平均净资产收益率",
            "扣除非经常性损益后的加权平均净资产收益率(%)",
            "扣除非经常性损益后的加权平均净资产收益率（％）",
            "扣除非经常性损益后的加权平均净资产收益率（%",
            "扣除非经常性损益后的加权平均净资产收益率（%）"})
    public String qcfjcxsyhdjqpjjzcsyl;

    @Horseman(keys = {"扣除非经常性损益后的加权平均净资产"})
    public String qcfjcxsyhdjqpjjzc;

    @Horseman(keys = {"扣除非经常性损益后的平均总资产收益率"})
    public String qcfjcxsyhdpjjzcsyl;

    @Horseman(keys = {"扣除非经常性损益后的加权平均净资产收益率（未年化）"})
    public String qcfjcxsyhdjqpjjzcsylwnh;

    @Horseman(keys = {"扣除非经常性损益后的平均总资产收益率（未年化）"})
    public String qcfjcxsyhdpjzzcsylwnh;

    @Horseman(keys = {"扣除非经常性损益后的加权平均净资产收益率（年化）"})
    public String qcfjcxsyhdjqpjjzcsylnh;

    @Horseman(keys = {"扣除非经常性损益后的净资产收益率",
            "扣除非经常性损益后的净资产收益率（%）",
            "扣除非经常性损益后的净资产收益率（%）"})
    public String qcfjcxsyhdjzcsyl;

    @Horseman(keys = {"扣除非经常性损益后的净资产收益率（未经年化）",
            "扣除非经常性损益后的净资产收益率（未年化）"})
    public String qcfjcxsyhdjzcsylwnh;

    @Horseman(keys = {"扣除非经营性损益后的加权净资产收益率（%）"})
    public String qcfjcxsyhdjqjzcsylnh;

    // 平均总资产利润率（未年化）
    @Horseman(keys = {"平均总资产利润率（未年化）",
            "平均总资产收益率（未年化）",
            "平均资产利润率（未年化）"})
    public String averageTotalAssetsProfitMarginNotYears;


    // 平均总资产利润率（年化）
    @Horseman(keys = {"平均总资产利润率（年化）", "平均总资产收益率（年化）"})
    public String averageTotalAssetsProfitMarginYears;

    @Horseman(keys = {"净资产收益率（未经年化）"})
    public String jzcyylwnh;

    @Horseman(keys = {"净资产收益率",
            "净资产收益率(%)",
            "净资产收益率（％）",
            "净资产收益率（%）"})
    public String jzcyyl;

    @Horseman(keys = {"全面摊薄净资产收益"})
    public String qmtbjzcsy;

    @Horseman(keys = {"全面摊薄净资产收益率(％)",
            "全面摊薄净资产收益率(%)",
            "净资产收益率（摊薄）",
            "全面摊薄净资产收益率（%）",
            "净资产收益率（全面摊薄）"})
    public String qmtbjzcsyl;

    @Horseman(keys = {"全面摊薄净资产收益率（未年化）",
            "全面摊薄净资产收益率（未经年化）"})
    public String qmtbjzcsylwnh;

    @Horseman(keys = {"全面摊薄净资产收益率（年化）"})
    public String qmtbjzcsylnh;

    @Horseman(keys = {"扣除非经常性损益后的全面摊薄净资产收益率",
            "扣除非经营性损益后的全面摊薄净资产收益率（%）",
            "扣除非经常性损益后的全面摊薄净资产收益率(%)",
            "扣除非经常性损益后全面摊薄净资产收益率（%）",
            "扣除非经常性损益后全面摊薄净资产收益率"})
    public String qcfjcxsyhdqmtbjzcsyl;

    @Horseman(keys = {"扣除非经常性损益后的全面摊薄净资产收益率（未年化）"})
    public String qcfjcxsyhdqmtbjzcsylwnh;

    @Horseman(keys = {"扣除非经常性损益后的全面摊薄净资产收益率（年化）"})
    public String qcfjcxsyhdqmtbjzcsylnh;

    @Horseman(keys = {"平均净资产收益率（未经年化）",
            "平均净资产收益率（未年化）"})
    public String pjjzcsylwnh;

    @Horseman(keys = {"加权平均净资产收益率",
            "加权平均净资产收益率（％",
            "加权平均净资产收益率（%",
            "净资产收益率（加权平均）",
            "加权平均净资产收益率（%）",
            "加权平均净资产收益率(%)",
            "加权平均净资产收益率（％）",
            "加权平均净资产收益率(％)"})
    public String weightedAverageRateReturnOnNetAssets;

    @Horseman(keys = {"加权平均净资产收益率（未年化）"})
    public String jqpjjzcsylwnh;

    @Horseman(keys = {"加权平均净资产收益率（年化）"})
    public String jqpjjzcsylnh;

    @Horseman(keys = {"平均净资产收益率（年化）"})
    public String pjjzcsylnh;

    // 全面摊薄净资产收益率
    @Horseman(keys = {"全面摊薄净资产收益率"})
    public String fullyDilutedReturnOnNetAssets;


    // 负债总额
    @Horseman(keys = {"负债总额",
            "负债总额（元）"})
    public String totalDebt;

    @Horseman(keys = {"资产负债率",
            "资产负债率(%)",
            "资产负债率（%）",
            "资产负债率（％）"})
    public String zcfzl;

    @Horseman(keys = {"资产减值损失"})
    public String assetsDevaluation;

    @Horseman(keys = {"资产减值准备"})
    public String zcjzzb;

    @Horseman(keys = {"拨备前营业利润"})
    public String pbqyylr;

    @Horseman(keys = {"资产减值损失前营业利润"})
    public String operationProfitsBeforeAssetsDevaluation;

    @Horseman(keys = {"营业外收入"})
    public String yywsr;

    @Horseman(keys = {"营业外支出"})
    public String yywzc;

    @Horseman(keys = {"流动资产"})
    public String ldzc;

    @Horseman(keys = {"流动负债"})
    public String ldfz;

    @Horseman(keys = {"股权转让净收益"})
    public String gqzrjsy;

    @Horseman(keys = {"非流动资产处置净收益"})
    public String fldzcczjsy;
}

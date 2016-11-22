package com.shubo.entity;

import com.shubo.annotation.Horseman;

/**
 * Created by horseman on 2016/11/22.
 */
public class FinanceData {
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
    public String shareholderMotherNetProfitsOfDNRGAL;

    // 归属于上市公司股东的股东权益
    @Horseman(keys={"归属于上市公司股东的股东权益"})
    public String shareholderEquity;

    // 归属于上市公司股东的每股净资产
    @Horseman(keys={"归属于上市公司股东的每股净资产"})
    public String shareholderNetAssetsPerShare;

    // 股本
    @Horseman(keys={"股本"})
    public String equity;

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

    // 稀释每股收益
    @Horseman(keys={"稀释每股收益", "稀释每股收益（元）"})
    public String dilutedEarningsPerShare;

    // 全面摊薄净资产收益率
    @Horseman(keys={"全面摊薄净资产收益率"})
    public String fullyDilutedReturnOnNetAssets;

    // 加权平均净资产收益率
    @Horseman(keys={"加权平均净资产收益率"})
    public String weightedAverageRateReturnOnNetAssets;

    // 资产总额
    @Horseman(keys={"资产总额"})
    public String totalAssets;

    // 负债总额
    @Horseman(keys={"负债总额"})
    public String totalDebt;

    public String getOperationRevenue() {
        return operationRevenue;
    }

    public void setOperationRevenue(String operationRevenue) {
        this.operationRevenue = operationRevenue;
    }

    public String getOperationProfits() {
        return operationProfits;
    }

    public void setOperationProfits(String operationProfits) {
        this.operationProfits = operationProfits;
    }

    public String getTotalProfits() {
        return totalProfits;
    }

    public void setTotalProfits(String totalProfits) {
        this.totalProfits = totalProfits;
    }

    public String getShareholderEquity() {
        return shareholderEquity;
    }

    public void setShareholderEquity(String shareholderEquity) {
        this.shareholderEquity = shareholderEquity;
    }

    public String getShareholderNetAssetsPerShare() {
        return shareholderNetAssetsPerShare;
    }

    public void setShareholderNetAssetsPerShare(String shareholderNetAssetsPerShare) {
        this.shareholderNetAssetsPerShare = shareholderNetAssetsPerShare;
    }

    public String getEquity() {
        return equity;
    }

    public void setEquity(String equity) {
        this.equity = equity;
    }

    public String getShareholderProfits() {
        return shareholderProfits;
    }

    public void setShareholderProfits(String shareholderProfits) {
        this.shareholderProfits = shareholderProfits;
    }

    public String getOperationActivityNetCashFlows() {
        return operationActivityNetCashFlows;
    }

    public void setOperationActivityNetCashFlows(String operationActivityNetCashFlows) {
        this.operationActivityNetCashFlows = operationActivityNetCashFlows;
    }

    public String getBasicEarningsPerShare() {
        return basicEarningsPerShare;
    }

    public void setBasicEarningsPerShare(String basicEarningsPerShare) {
        this.basicEarningsPerShare = basicEarningsPerShare;
    }

    public String getDilutedEarningsPerShare() {
        return dilutedEarningsPerShare;
    }

    public void setDilutedEarningsPerShare(String dilutedEarningsPerShare) {
        this.dilutedEarningsPerShare = dilutedEarningsPerShare;
    }

    public String getFullyDilutedReturnOnNetAssets() {
        return fullyDilutedReturnOnNetAssets;
    }

    public void setFullyDilutedReturnOnNetAssets(String fullyDilutedReturnOnNetAssets) {
        this.fullyDilutedReturnOnNetAssets = fullyDilutedReturnOnNetAssets;
    }

    public String getWeightedAverageRateReturnOnNetAssets() {
        return weightedAverageRateReturnOnNetAssets;
    }

    public void setWeightedAverageRateReturnOnNetAssets(String weightedAverageRateReturnOnNetAssets) {
        this.weightedAverageRateReturnOnNetAssets = weightedAverageRateReturnOnNetAssets;
    }

    public String getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(String totalAssets) {
        this.totalAssets = totalAssets;
    }

    public String getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(String totalDebt) {
        this.totalDebt = totalDebt;
    }
}

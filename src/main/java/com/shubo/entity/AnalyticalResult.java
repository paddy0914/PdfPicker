package com.shubo.entity;

/**
 * 文件解析情况的结果
 * Created by liujinping on 2016/12/9.
 */
public class AnalyticalResult {
    //文件名
    String filename;
    //合并资产负债表
    int consolidatedBalanceSheetTable;
    //合并现金流表
    int consolidatedCashFlowTable;
    //合并股东权益变动表
    int consolidatedEquityChangeTable;
    //合并现金流表
    int consolidatedProfitsTable;
    //母公司资产负债表
    int parentBalanceSheetTable;
    //母公司现金流表
    int parentCashFlowTable;
    //母公司股东权益变动表
    int parentEquityEquityChangeTable;
    //母公司利润表
    int parentProfitsTable;
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getConsolidatedBalanceSheetTable() {
        return consolidatedBalanceSheetTable;
    }

    public void setConsolidatedBalanceSheetTable(int consolidatedBalanceSheetTable) {
        this.consolidatedBalanceSheetTable = consolidatedBalanceSheetTable;
    }

    public int getConsolidatedCashFlowTable() {
        return consolidatedCashFlowTable;
    }

    public void setConsolidatedCashFlowTable(int consolidatedCashFlowTable) {
        this.consolidatedCashFlowTable = consolidatedCashFlowTable;
    }

    public int getConsolidatedEquityChangeTable() {
        return consolidatedEquityChangeTable;
    }

    public void setConsolidatedEquityChangeTable(int consolidatedEquityChangeTable) {
        this.consolidatedEquityChangeTable = consolidatedEquityChangeTable;
    }

    public int getConsolidatedProfitsTable() {
        return consolidatedProfitsTable;
    }

    public void setConsolidatedProfitsTable(int consolidatedProfitsTable) {
        this.consolidatedProfitsTable = consolidatedProfitsTable;
    }

    public int getParentBalanceSheetTable() {
        return parentBalanceSheetTable;
    }

    public void setParentBalanceSheetTable(int parentBalanceSheetTable) {
        this.parentBalanceSheetTable = parentBalanceSheetTable;
    }

    public int getParentCashFlowTable() {
        return parentCashFlowTable;
    }

    public void setParentCashFlowTable(int parentCashFlowTable) {
        this.parentCashFlowTable = parentCashFlowTable;
    }

    public int getParentEquityEquityChangeTable() {
        return parentEquityEquityChangeTable;
    }

    public void setParentEquityEquityChangeTable(int parentEquityEquityChangeTable) {
        this.parentEquityEquityChangeTable = parentEquityEquityChangeTable;
    }

    public int getParentProfitsTable() {
        return parentProfitsTable;
    }

    public void setParentProfitsTable(int parentProfitsTable) {
        this.parentProfitsTable = parentProfitsTable;
    }
}

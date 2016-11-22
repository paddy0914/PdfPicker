package com.shubo.entity;

/**
 * Created by horseman on 2016/11/22.
 */
public class YearReportDes {
    String companyName;
    String year;

    public YearReportDes(String companyName, String year, int type) {
        this.companyName = companyName;
        this.year = year;
        this.type = type;
    }

    /* 1:一季度 2:中报 3:三季度 4:年度报告 */
    int type;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

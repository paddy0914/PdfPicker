package com.shubo.entity;

import com.shubo.annotation.Horseman;

/**
 * Created by horseman on 2016/11/23.
 */
public class CashFlow {
    @Horseman(keys={"经营活动现金流入小计"})
    public String operationCashIn;

    @Horseman(keys={"经营活动现金流出小计"})
    public String operationCashOut;

    @Horseman(keys={"经营活动产生的现金流量净额"})
    public String operationNetCash;

    @Horseman(keys={"投资活动现金流入小计"})
    public String investCashIn;

    @Horseman(keys={"资活动现金流出小计"})
    public String investCashOut;

    @Horseman(keys={"投资活动产生的现金流量净额"})
    public String investNetCash;

    @Horseman(keys={"筹资活动现金流入小计"})
    public String fundCashIn;

    @Horseman(keys={"筹资活动现金流出小计"})
    public String fundCashOut;

    @Horseman(keys={"筹资活动产生的现金流量净额"})
    public String fundNetCash;

    @Horseman(keys={"现金及现金等价物净增加额"})
    public String netValue;

    @Horseman(keys={"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"})
    public String xx;

}

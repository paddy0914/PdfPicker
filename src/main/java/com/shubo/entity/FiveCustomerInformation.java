package com.shubo.entity;

import com.shubo.annotation.Horseman;

/**
 * Created by liujinping on 2016/12/26.
 */
public class FiveCustomerInformation {
    @Horseman(keys={"序号"})
    public String 序号;
    @Horseman(keys={"客户名称"})
    public String 客户名称;
    @Horseman(keys={"销售额"})
    public String 销售额;
    @Horseman(keys={"占年度销售总额比例"})
    public String 占年度销售总额比例;
}

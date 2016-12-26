package com.shubo.entity;

import com.shubo.annotation.Horseman;

/**
 * Created by liujinping on 2016/12/26.
 */
public class Employee {
    @Horseman(keys={"姓名"})
    public String 姓名;
    @Horseman(keys={"职务"})
    public String 职务;
    @Horseman(keys={"性别"})
    public String 性别;
    @Horseman(keys={"年龄"})
    public String 年龄;
    @Horseman(keys={"任职起始日期"})
    public String 任职起始日期;
    @Horseman(keys={"任职终止日期"})
    public String 任职终止日期;
    @Horseman(keys={"年初持股数"})
    public String 年初持股数;
    @Horseman(keys={"年末持股数"})
    public String 年末持股数;
    @Horseman(keys={"变动原因"})
    public String 变动原因;
    @Horseman(keys={"报告期内从公司领取的税前报酬总额万元"})
    public String 报告期内从公司领取的税前报酬总额万元;
    @Horseman(keys={"是否在股东单位或其他关联单位领取"})
    public String 是否在股东单位或其他关联单位领取;
}

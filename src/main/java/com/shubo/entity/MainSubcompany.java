package com.shubo.entity;

import com.shubo.annotation.Horseman;

/**
 * Created by liujinping on 2016/12/26.
 */
public class MainSubcompany{
    @Horseman(keys={"公司名称"})
    public String 公司名称;
    @Horseman(keys={"公司类型"})
    public String 公司类型;
    @Horseman(keys={"所处行业"})
    public String 所处行业;
    @Horseman(keys={"主要产品或服务"})
    public String 主要产品或服务;
    @Horseman(keys={"注册资本"})
    public String 注册资本;
    @Horseman(keys={"总资本"})
    public String 总资本;
    @Horseman(keys={"净资产"})
    public String 净资产;
    @Horseman(keys={"营业收入"})
    public String 营业收入;
    @Horseman(keys={"营业利润"})
    public String 营业利润;
    @Horseman(keys={"净利润"})
    public String 净利润;
}

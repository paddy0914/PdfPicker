package com.shubo.entity;

import com.shubo.annotation.Horseman;

/**
 * Created by horseman on 2016/11/23.
 * 前10名无限售条件股东持股情况
 */
public class ShareHolderNoLimit {

    @Horseman(keys = {"股东名称"})
    public String name;

    @Horseman(keys = {"持有无限售条件股份数量"})
    public String noLimitAmount;

    @Horseman(keys = {"股份种类"})
    public String type;
}

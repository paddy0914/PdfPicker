package com.shubo.entity;

import com.shubo.annotation.Horseman;

/**
 * Created by horseman on 2016/11/23.
 * 前10名股东持股情况
 */
public class ShareHolder {

    @Horseman(keys = {"股东名称"})
    public String name;

    @Horseman(keys = {"股东性质"})
    public String shareholderQuality;

    @Horseman(keys = {"持股比例", "持股比例(%)", "持股比例 （%）", "持股比例（%）"})

    public String holdRatio;

    @Horseman(keys = {"报告期末持股数量", "持股数量"})
    public String holdAmount;

    @Horseman(keys = {"报告期内增减变动情况"})
    public String changeInfo;

    @Horseman(keys = {"持有有限售条件的股份数量"})
    public String limitShareAmount;

    @Horseman(keys = {"持有无限售条件的股份数量"})
    public String noLimitShareAmount;

    @Horseman(keys = {"股份状态（质押或冻结情况）", "质押或冻结情况", "质押冻结情况"})
    public String status;

    @Horseman(keys = {"数量", "质押或冻结的股份数量"})
    public String freezAmount;
}

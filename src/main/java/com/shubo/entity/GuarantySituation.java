package com.shubo.entity;

import com.shubo.annotation.Horseman;

/**
 * Created by liujinping on 2016/12/26.
 */
public class GuarantySituation {
    @Horseman(keys={"担保对象名称"})
    public String 担保对象名称;
    @Horseman(keys={"担保额度相关公告披露日期"})
    public String 担保额度相关公告披露日期;
    @Horseman(keys={"担保额度"})
    public String 担保额度;
    @Horseman(keys={"实际发生日期（协议签署日）"})
    public String 实际发生日期;
    @Horseman(keys={"实际担保金额"})
    public String 实际担保金额;
    @Horseman(keys={"担保类型"})
    public String 担保类型;
    @Horseman(keys={"担保期"})
    public String 担保期;
    @Horseman(keys={"是否履行完毕"})
    public String 是否履行完毕;
    @Horseman(keys={"是否为关联方担保"})
    public String 是否为关联方担保;
}

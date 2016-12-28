package com.shubo.sniff.employee;

import com.shubo.annotation.Todd;

/**
 * Created by liujinping on 2016/12/26.
 */
@Todd(key = "SeniorManager",
        index = 16,
        suffix = ".supervisor",
        folder = "现任高级管理人员",
        title={"现任高级管理人员"})
public class SeniorManagerSniffer extends EmployeeSniffer{
}

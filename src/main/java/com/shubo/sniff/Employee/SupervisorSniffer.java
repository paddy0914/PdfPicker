package com.shubo.sniff.Employee;

import com.shubo.annotation.Todd;

/**
 * Created by liujinping on 2016/12/26.
 */
@Todd(key = "Supervisor",
        index = 15,
        suffix = ".supervisor",
        folder = "现任监事会成员")
public class SupervisorSniffer extends EmployeeSniffer {
}

package com.shubo.sniff.Employee;

import com.shubo.annotation.Todd;

/**
 * Created by liujinping on 2016/12/26.
 */
@Todd(key = "Director",
        index = 14,
        suffix = ".director",
        folder = "现任董事会成员")
public class DirectorSniffer extends EmployeeSniffer {
}
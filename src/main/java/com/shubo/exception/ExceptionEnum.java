package com.shubo.exception;

/**
 * Created by liujinping on 2016/12/7.
 */

/**
 *  解析结果说明：
 *  SUCCESS   表示 解析成功
 *  FAILURE_TITLE_ERRO 表示 对应的Title错误
 *  FAILURE_NOTFOUND_TITLE 表示 没有找到Title
 *  FAILURE_UNKNOW_ERRO 表示 未知错误
 */
public class ExceptionEnum {
     public static enum WrongEnum{
         SUCCESS,
         FAILURE_TITLE_ERRO,
         FAILURE_NOTFOUND_TITLE,
         FAILURE_UNKNOW_ERRO,
    };
}

package com.shubo.sniff;

/**
 * Created by horseman on 2016/11/22.
 */
abstract public class Sniffer {
    public abstract String getKey();
    public abstract String getSuffix();
    public abstract String getFolder();
    public abstract boolean sniff(String content);
    public abstract String generateEntityJson(String content);
}

package com.shubo.sniff.report;

/**
 * Created by liujinping on 2016/12/12.
 */
public class IndexEntity {
    private int index;

    private String AttributeName;

    public int getIndex() {
        return index;
    }

    public IndexEntity(int index, String attributeName) {
        this.index = index;
        AttributeName = attributeName;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAttributeName() {
        return AttributeName;
    }

    public void setAttributeName(String attributeName) {
        AttributeName = attributeName;
    }
}

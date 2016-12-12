package com.shubo.entity.report;

/**
 * Created by liujinping on 2016/12/12.
 */
public class IndexEntity {
    public int index;

    public String AttributeName;

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

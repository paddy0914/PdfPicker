package com.shubo.sniff;

/**
 * Created by horseman on 2016/11/22.
 */
public class NrgalSniffer extends Sniffer {
    public String getKey() {
        return "Nrgal";
    }

    public String getSuffix() {
        return ".table2";
    }

    public String getFolder() {
        return "非经常性损益";
    }

    public boolean sniff(String content) {
        return false;
    }

    public String generateEntityJson(String content) {
        return null;
    }
}

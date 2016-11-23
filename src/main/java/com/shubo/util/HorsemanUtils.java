package com.shubo.util;

/**
 * Created by horseman on 2016/11/23.
 */
public class HorsemanUtils {
    public static String[] getLines(String content) {
        return null;
    }

    public static void main(String args[]) {
        String str = "helloworld";
        int cnt = 5;
        moveStr(str, cnt);
        System.out.println(str + " " + cnt);
    }

    public static void moveStr(String contents, int cnt) {
        contents = contents.replace("h", "oo");
        cnt --;
    }
}

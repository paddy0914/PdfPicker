package com.shubo.util;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by horseman on 2016/11/23.
 */
public class HorsemanUtils {
    public static String[] getLines(String content) {
        return null;
    }

    public static void main(String args[]) {
        Queue<String> keyQueue = new LinkedBlockingQueue<>(4);
        keyQueue.add("1");
        keyQueue.add("2");
        keyQueue.add("3");
        keyQueue.add("4");
        keyQueue.add("5");
        keyQueue.add("6");

        String[] keys = (String[]) keyQueue.toArray();
        String str = "helloworld";
        int cnt = 5;
        moveStr(str, cnt);
        System.out.println(str + " " + cnt);
    }

    public static void moveStr(String contents, int cnt) {
        contents = contents.replace("h", "oo");
        cnt --;
    }

    public static String removeBlank(String content) {
        return content.replace(" ", "");
    }

    public static void doSomeThing() {

    }

    public static Map<String, String[]> keysMap = new HashMap<>();
    public static Map<String, Integer> indexMap = new HashMap<>();

    public static void saveText(String fileName, String str) {
        String[] keys = keysMap.get(fileName);
        int index = indexMap.get(fileName);

        keys[index] = str;
        index = index + 1;
        if (index == 4) {
            index = 0;
        }
        indexMap.put(fileName, index);
    }

    public static void createKeys(String fileName) {
        String[] keys = new String[4];
        keysMap.put(fileName, keys);
        indexMap.put(fileName, 0);
    }

    public static List<String> getPossibleKeys(String fileName) {
        List<String> possibleKeys = new ArrayList<>();
        String[] keys = keysMap.get(fileName);
        for (int i = 0; i < 4; i ++) {
            if (!keys[i].equals("")) {
                possibleKeys.add(keys[i]);
            }
            keys[i] = "";
        }

        return possibleKeys;
    }
}

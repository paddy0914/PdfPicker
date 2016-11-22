package com.shubo.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by horseman on 2016/11/21.
 */
public class FileUtils {
    public static String getFileContent(String fileName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String line;
        while ((line = bf.readLine()) != null) {
            stringBuilder.append(line);
        }

        return stringBuilder.toString();
    }
}

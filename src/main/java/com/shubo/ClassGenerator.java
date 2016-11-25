package com.shubo;

import com.shubo.annotation.Horseman;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * Created by horseman on 2016/11/25.
 */
public class ClassGenerator {
    public static void main(String args[]) {
        File file = new File("d:\\class.txt");
        try {
            BufferedReader bio = new BufferedReader(new FileReader(file));
            String str;
            while ((str = bio.readLine()) != null) {
//                @Horseman(keys = {"货币资金"})
//                public String hbzj;
                System.out.println("@Horseman(keys = {\"" + str + "\"})");
                System.out.println("public String " + PinYin.cn2py(str) + ";");
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

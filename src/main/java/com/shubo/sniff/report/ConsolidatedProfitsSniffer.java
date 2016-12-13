package com.shubo.sniff.report;

import com.shubo.annotation.Todd;
import com.shubo.entity.report.ConsolidatedProfits;
import com.shubo.sniff.Sniffer;
import com.shubo.sniff.TableSniffer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by horseman on 2016/11/28.
 */
@Todd(key = "ConsolidatedProfits",
        index = 3,
        suffix = ".cp",
        folder = "合并利润表",
        title = {"合并利润表"})
public class ConsolidatedProfitsSniffer extends Sniffer {

    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content, CPKeyWords, 6);
    }

    @Override
    public int[] getColCnt(String table) {
        int[] result = new int[3];
        result[0] = 2;
        result[1] = 1;
        result[2] = 2;

        return result;
    }

    /**
     * @param table
     * @return int[2] : 1,需要几列数据; 2, 数据在哪一列
     */
    public int[] getColCnt2(String table) {
        int[] result = new int[2];
        result[0] = 1;
        String lines[] = table.split("\n");
        int[] value_place = new int[5];
        int sum = 0;
        for (String line : lines) {
            String[] contents = line.split(TableSniffer.ELEMENT_DIVIDOR, -1);
            String regex = "([0-9]{0,}[,]?[0-9]{1,}[,]?[0-9]{0,}[,]?[0-9]{0,}[/.]?[0-9]{0,})|([0-9]{1,}[-][0-9]{1,}[-][0-9]{1,})";
            for (int i = 0; i < contents.length; i++) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(contents[i]);
                boolean b = matcher.matches();
                if (b && i < 5) {
                    value_place[sum++] = i;
                    break;
                }
            }
            if(sum>4)
                break;
        }
        int compare = value_place[0];
        for (int i = 0; i < value_place.length; i++) {
            if (value_place[i] != compare)
                result[1] = -1;
            else if (i == 4) {
                result[1] = compare;
            }
        }
        return result;
    }

    @Override
    public String[] generateEntityJson(String content) {
        return generateEntityJson(content, ConsolidatedProfits.class, 2, 3);
    }

    private static final String[] CPKeyWords = {
            "利息收入",
            "已赚保费",
            "手续费及佣金收入",
            "提取保险合同准备金净额",
            "保单红利支出",
            "财务费用",
            "资产减值损失",
            "加：营业外收入",
            "减：所得税费用"
    };
}

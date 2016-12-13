package com.shubo.sniff.report;

import com.shubo.annotation.Todd;
import com.shubo.entity.report.ConsolidatedCashFlow;
import com.shubo.sniff.Sniffer;
import com.shubo.sniff.TableSniffer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by horseman on 2016/11/25.
 */
@Todd(key = "ConsolidatedCashFlow",
        index = 1,
        suffix = ".ccf",
        folder = "合并现金流量表",
        title = {"合并现金流量表"})
public class ConsolidatedCashFlowSniffer extends Sniffer {

    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content, CCFKeyWords, 3);
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
     * 需要完善
     *
     * @param table
     * @return
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
            if (sum > 4)
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
        return generateEntityJson(content, ConsolidatedCashFlow.class, 2, 3);
    }

    private static final String[] CCFKeyWords = {
            "客户存款和同业存放款项净增加额",
            "向中央银行借款净增加额",
            "收到原保险合同保费取得的现金",
            "收到再保险业务现金净额",
            "保户储金及投资款净增加额",
            "拆入资金净增加额",
            "回购业务资金净增加额"
    };
}

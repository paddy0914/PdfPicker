package com.shubo.sniff.report;

import com.shubo.annotation.Todd;
import com.shubo.entity.report.ConsolidatedCashFlow;
import com.shubo.sniff.Sniffer;
import com.shubo.sniff.TableSniffer;

import java.util.ArrayList;
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

    /**
     * @param table
     * @return
     */
    @Override
    public ColResult getColCnt(String table, Class clazz) {
        ColResult colResult = new ColResult();
        int result[] = null;
        int[] colWhere;
        if (Sniffer.isReportEntity(clazz)) {
            colResult.isList = true;
        } else {
            colResult.isList = false;
        }
        result = getColWhere(table);
        colResult.colCnt = result[0];
        colWhere = new int[result.length - 1];
        for (int i = 0; i < result.length - 1; i++) {
            colWhere[i] = result[i + 1];
        }
        colResult.where = colWhere;
        int maxNum = colWhere[0];
        for (int i = 1; i < colWhere.length; i++) {
            maxNum = colWhere[i] > maxNum ? colWhere[i] : maxNum;
        }
        colResult.maxCol = maxNum;

        return colResult;
    }

    public int[] getColWhere(String table) {
        String lines[] = table.split("\n");

        int[] value_place = new int[5];
        int sum = 0;
        //找到每行数据中是数字的那一列，保存在value_place中
        for (int j = 1; j < lines.length; j++) {
            String[] contents = lines[j].split(TableSniffer.ELEMENT_DIVIDOR, -1);
            String regex = "[0-9]{0,}[,]?[0-9]{0,}[,]?[0-9]{0,}[,]?[0-9]{3,}[/.]?[0-9]{0,}";
            for (int i = 0; i < contents.length; i++) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(contents[i]);
                boolean b = matcher.matches();
                if (b && i < contents.length) {
                    value_place[sum++] = i;
                    if (sum > 4)
                        break;
                }
            }
            if (sum > 4)
                break;
        }

        ArrayList al_flag = new ArrayList();
        ArrayList al_result = new ArrayList();

        al_flag.add(value_place[0]);
        al_result.add(value_place[0]);

        //找到value_place中数字不同的数，并将他们保存在al_result中，这些数就是有用数据在contents中的列数
        for (int i = 0; i < value_place.length; i++) {
            boolean flag = true;
            for (int j = 0; j < al_flag.size(); j++) {
                if (value_place[i] == (int) al_flag.get(j))
                    flag = false;
            }
            if (flag) {
                al_result.add(value_place[i]);
                al_flag.add(value_place[i]);
            }
        }

        int[] result = new int[al_result.size() + 1];
        result[0] = al_result.size();
        for (int i = 0; i < al_result.size(); i++) {
            result[i + 1] = (int) al_result.get(i);
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

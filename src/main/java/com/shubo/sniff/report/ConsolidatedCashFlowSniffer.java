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
    public int[] getColCnt(String table) {
        ArrayList arrayList=new ArrayList();
        String lines[] = table.split("\n");

        int[] value_place = new int[5];
        int sum = 0;
        for (int j = 1; j < lines.length; j++) {
            String[] contents = lines[j].split(TableSniffer.ELEMENT_DIVIDOR, -1);
            String regex = "[0-9]{0,}[,]?[0-9]{0,}[,]?[0-9]{0,}[,]?[0-9]{1,}[/.]?[0-9]{0,}";
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

        ArrayList alvalue =new ArrayList();
        alvalue.add(value_place[0]);
        arrayList.add(value_place[0]);

        for(int i=0;i<value_place.length;i++){
            boolean flag=true;
            for(int j=0;j<alvalue.size();j++){
                if(value_place[i]==(int)alvalue.get(j))
                    flag=false;
            }
            if(flag){
                arrayList.add(value_place[i]);
                alvalue.add(value_place[i]);
            }
        }
        int[] result =new int[arrayList.size()+1];
        result[0]=arrayList.size();
        for(int i=0;i<arrayList.size();i++){
            result[i+1]=(int)arrayList.get(i);
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

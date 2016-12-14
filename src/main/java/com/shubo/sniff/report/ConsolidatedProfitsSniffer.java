package com.shubo.sniff.report;

import com.shubo.annotation.Todd;
import com.shubo.entity.report.ConsolidatedProfits;
import com.shubo.sniff.Sniffer;
import com.shubo.sniff.TableSniffer;

import java.util.ArrayList;
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

    /**
     * @param table
     * @return int[2] : 1,需要几列数据; 2, 数据在哪几列
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

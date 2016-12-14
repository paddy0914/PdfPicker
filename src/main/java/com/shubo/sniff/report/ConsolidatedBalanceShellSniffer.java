package com.shubo.sniff.report;

import com.shubo.annotation.Todd;
import com.shubo.entity.report.ConsolidatedBalanceSheet;
import com.shubo.sniff.Sniffer;
import com.shubo.sniff.TableSniffer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by horseman on 2016/11/28.
 */
@Todd(key = "ConsolidatedBalanceShell",
        index = 0,
        suffix = ".cbs",
        folder = "合并资产负债表",
        title = {"合并资产负债表"})
public class ConsolidatedBalanceShellSniffer extends Sniffer {

    @Override
    public boolean sniff(String content) {
        return sniffByKeywords(content, CBSKeyWords, 7);
    }

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
        return generateEntityJson(content, ConsolidatedBalanceSheet.class, 2, 3);
    }

    private static final String[] CBSKeyWords = {
            "固定资产清理固定资产清理",
            "生产性生物资产",
            "长期待摊费用",
            "吸收存款及同业存放",
            "衍生金融负债",
            "卖出回购金融资产款",
            "应付职工薪酬",
            "划分为持有待售的负债",
            "递延所得税负债",
            "永续债"
    };
}

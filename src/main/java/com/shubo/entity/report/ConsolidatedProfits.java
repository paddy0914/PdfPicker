package com.shubo.entity.report;

import com.shubo.annotation.Horseman;

/**
 * Created by horseman on 2016/11/25.
 */
public class ConsolidatedProfits {
    @Horseman(keys = {"一、营业总收入"})
    public String yyyzsr;

    @Horseman(keys = {"其中：营业收入"})
    public String qzyysr;

    @Horseman(keys = {"利息收入"})
    public String lxsr;

    @Horseman(keys = {"已赚保费"})
    public String yzbf;

    @Horseman(keys = {"手续费及佣金收入"})
    public String sxfjyjsr;

    @Horseman(keys = {"二、营业总成本"})
    public String eyyzcb;

    @Horseman(keys = {"其中：营业成本"})
    public String qzyycb;

    @Horseman(keys = {"利息支出"})
    public String lxzc;

    @Horseman(keys = {"手续费及佣金支出"})
    public String sxfjyjzc;

    @Horseman(keys = {"退保金"})
    public String tbj;

    @Horseman(keys = {"赔付支出净额"})
    public String pfzcje;

    @Horseman(keys = {"提取保险合同准备金净额"})
    public String tqbxhtzbjje;

    @Horseman(keys = {"保单红利支出"})
    public String bdhlzc;

    @Horseman(keys = {"分保费用"})
    public String fbfy;

    @Horseman(keys = {"营业税金及附加"})
    public String yysjjfj;

    @Horseman(keys = {"销售费用"})
    public String xsfy;

    @Horseman(keys = {"管理费用"})
    public String glfy;

    @Horseman(keys = {"财务费用"})
    public String cwfy;

    @Horseman(keys = {"资产减值损失"})
    public String zcjzss;

    @Horseman(keys = {"加：公允价值变动收益（损失以“－”号填列）"})
    public String jgyjzbdsyssyhtl;

    @Horseman(keys = {"投资收益（损失以“－”号填列）"})
    public String tzsyssyhtl;

    @Horseman(keys = {"其中：对联营企业和合营企业的投资收益"})
    public String qzdlyqyhhyqydtzsy;

    @Horseman(keys = {"汇兑收益（损失以“-”号填列）"})
    public String hdsyssyhtl;

    @Horseman(keys = {"三、营业利润（亏损以“－”号填列）"})
    public String syylrksyhtl;

    @Horseman(keys = {"加：营业外收入"})
    public String jyywsr;

    @Horseman(keys = {"其中：非流动资产处置利得"})
    public String qzfldzcczld;

    @Horseman(keys = {"减：营业外支出"})
    public String jyywzc;

    @Horseman(keys = {"其中：非流动资产处置损失"})
    public String qzfldzcczss;

    @Horseman(keys = {"四、利润总额（亏损总额以“－”号填列）"})
    public String slrzekszeyhtl;

    @Horseman(keys = {"减：所得税费用"})
    public String jsdsfy;

    @Horseman(keys = {"五、净利润（净亏损以“－”号填列"})
    public String wjlrjksyhtl;

    @Horseman(keys = {"归属于母公司所有者的净利润"})
    public String gsymgssyzdjlr;

    @Horseman(keys = {"少数股东损益"})
    public String ssgdsy;

    @Horseman(keys = {"六、其他综合收益的税后净额"})
    public String lqtzhsydshje;

    @Horseman(keys = {"归属母公司所有者的其他综合收益的税后净额"})
    public String gsmgssyzdqtzhsydshje;

    @Horseman(keys = {"（一）以后不能重分类进损益的其他综合收益"})
    public String yyhbnzfljsydqtzhsy;

    @Horseman(keys = {"1.重新计量设定受益计划净负债或净资产的变动"})
    public String zxjlsdsyjhjfzhjzcdbd;

    @Horseman(keys = {"2.权益法下在被投资单位不能重分类进损益的其他综合收益中享有的份额"})
    public String qyfxzbtzdwbnzfljsydqtzhsyzxydfe;

    @Horseman(keys = {"（二）以后将重分类进损益的其他综合收益"})
    public String eyhjzfljsydqtzhsy;

    @Horseman(keys = {"1.权益法下在被投资单位以后将重分类进损益的其他综合收益中享有的份额"})
    public String qyfxzbtzdwyhjzfljsydqtzhsyzxydfe;

    @Horseman(keys = {"2.可供出售金融资产公允价值变动损益"})
    public String kgcsjrzcgyjzbdsy;

    @Horseman(keys = {"3.持有至到期投资重分类为可供出售金融资产损益"})
    public String cyzdqtzzflwkgcsjrzcsy;

    @Horseman(keys = {"4.现金流量套期损益的有效部分"})
    public String xjlltqsydyxbf;

    @Horseman(keys = {"5.外币财务报表折算差额"})
    public String wbcwbbzsce;

    @Horseman(keys = {"6.其他"})
    public String qt;

    @Horseman(keys = {"归属于少数股东的其他综合收益的税后净额"})
    public String gsyssgddqtzhsydshje;

    @Horseman(keys = {"七、综合收益总额"})
    public String qzhsyze;

    @Horseman(keys = {"归属于母公司所有者的综合收益总额"})
    public String gsymgssyzdzhsyze;

    @Horseman(keys = {"归属于少数股东的综合收益总额"})
    public String gsyssgddzhsyze;

    @Horseman(keys = {"八、每股收益："})
    public String bmgsy;

    @Horseman(keys = {"（一）基本每股收益"})
    public String yjbmgsy;

    @Horseman(keys = {"（二）稀释每股收益"})
    public String exsmgsy;
}

package com.saigyouji.calculator;


import java.util.ArrayList;
import java.util.List;

import static com.saigyouji.calculator.Service.Figure_Service.calculate;
import static com.saigyouji.calculator.Service.Figure_Service.expressionToList;
import static com.saigyouji.calculator.Service.Figure_Service.parseToPostfixExpression;

public class Out
{
    private static List<String> sign=new ArrayList<>();
    static {
        sign.add("+");
        sign.add("-");
        sign.add("*");
        sign.add("/");
    }
    private String out;
    /**
     * @param out 计算的中缀表达式
     */
    public Out(String out)
    {
        this.out = out;
    }

    public String getOut()
    {
        return out;
    }

    /**
     * @return 中缀表达式转化
     */
    public String getAnswer()
    {
        List<String> stringList = expressionToList(this.out);
        return calculate(parseToPostfixExpression(stringList));
    }

}

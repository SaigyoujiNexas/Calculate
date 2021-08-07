package com.saigyouji.calculator.Service;

import android.util.Log;
import android.widget.ListView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Locale;
import java.util.Stack;
import java.util.concurrent.BlockingDeque;

import static com.saigyouji.calculator.Service.Common.getLastNumber;
import static com.saigyouji.calculator.Service.Common.isBrackets;
import static com.saigyouji.calculator.Service.Common.isLeftBracket;
import static com.saigyouji.calculator.Service.Common.isNumber;
import static com.saigyouji.calculator.Service.Common.isOperator;
import static com.saigyouji.calculator.Service.Common.isPercent;
import static com.saigyouji.calculator.Service.Common.isRightBracket;

public class Figure_Service
{
    private static final String TAG = "Figure_Service";




    /**
     * transform the string to a list
     * @param expression the expression string
     * @return the list of expression
     */
    public  static List<String> expressionToList(String expression)
    {
        // index to locate the string codepoint
        int index = 0;
        List<String> list = new ArrayList<>();
        do{
            char ch = expression.charAt(index);
            if(index == expression.length() - 1 && ch == '(')
            {
                list.clear();
                list.add("Error");
                return list;
            }

            if(ch == 's')
            {
                if(index >= expression.length() -3 ||expression.charAt(index + 1) != 'i' || expression.charAt(index + 2) != 'n')
                {
                    list.clear();
                    list.add("Error");
                    return list;
                }
                index += 4;
                int temp = index;
                int right_bracket = 1;
                while(temp < expression.length() && right_bracket != 0)
                {
                    if(expression.charAt(temp) == '(')
                        right_bracket++;
                    else if(expression.charAt(temp) == ')')
                        right_bracket--;
                    temp++;
                    if(temp == expression.length() - 1 && expression.charAt(temp) == '(')
                    {
                        list.clear();
                        list.add("Error");
                        return  list;
                    }

                }
                String res = calculate(parseToPostfixExpression(expressionToList(expression.substring(index, temp))));
                if(res.equals("NaN"))
                {
                    list.clear();
                    list.add("NaN");
                    return list;
                }
                if(res.equals("运算式错误！"))
                {
                    list.clear();
                    list.add("Error");
                    return list;
                }
                double test = Double.valueOf(res) / StrictMath.PI / 0.5;

                if(test %2.0 == 0) {
                    list.add("0");
                }
                else
                    list.add(String.valueOf(StrictMath.sin(Double.parseDouble(res))));
                index = temp+ 1;
                continue;
            }
            if(ch == 'c')
            {
                if(index >= expression.length() -3 ||expression.charAt(index + 1) != 'o' || expression.charAt(index + 2) != 's')
                {
                    list.clear();
                    list.add("Error");
                    return list;
                }
                index += 4;
                int temp = index;
                int right_bracket = 1;
                while(temp < expression.length() && right_bracket != 0)
                {
                    if(expression.charAt(temp) == '(')
                        right_bracket++;
                    else if(expression.charAt(temp) == ')')
                        right_bracket--;
                    temp++;
                    if(temp == expression.length() - 1 && (expression.charAt(temp) == '('|| isOperator(String.valueOf(expression.charAt(temp)))))
                    {
                        list.clear();
                        list.add("Error");
                        return  list;
                    }

                }
                if(temp == expression.length() - 1)
                    temp++;
                String res = calculate(parseToPostfixExpression(expressionToList(expression.substring(index, temp))));
                if(res.equals("NaN"))
                {
                    list.clear();
                    list.add("NaN");
                    return list;
                }
                if(res.equals("运算式错误！"))
                {
                        list.clear();
                        list.add("Error");
                        return list;

                }
                double test = Double.valueOf(res) / StrictMath.PI / 0.5;

                if(test %2.0 == 1)
                {
                    list.add("0");
                }
               else
                    list.add(String.valueOf(StrictMath.cos(Double.valueOf(res))));
                index = temp+ 1;
                continue;
            }
            if(ch == 't')
            {
                if(index >= expression.length() -3 ||expression.charAt(index + 1) != 'a' || expression.charAt(index + 2) != 'n')
                {
                    list.clear();
                    list.add("Error");
                    return list;
                }
                int right_bracket = 1;
                index += 4;
                int temp = index;
                while(temp < expression.length() && right_bracket != 0)
                {
                    if(expression.charAt(temp) == '(')
                        right_bracket++;
                    else if(expression.charAt(temp) == ')')
                        right_bracket--;
                    temp++;
                    if(temp == expression.length() - 1 && expression.charAt(temp) == '(')
                    {
                        list.clear();
                        list.add("Error");
                        return  list;
                    }

                }
                String res = calculate(parseToPostfixExpression(expressionToList(expression.substring(index, temp))));
                try {
                    if (Double.isNaN(Double.parseDouble(res))) {
                        list.clear();
                        list.add("NaN");
                        return list;
                    }
                }
                catch (NumberFormatException e)
                {
                    list.clear();
                    list.add("Error");
                    return list;
                }
                double test = Double.valueOf(res) / StrictMath.PI / 0.5;

                if(test %2 == 0)
                {
                    list.add("0");
                }
                else if(test  %2.0 == 1)
                {
                    list.clear();
                    list.add("NaN");
                    return list;
                }
                else if(test % 2.0 == -1)
                {
                    list.clear();
                    list.add("NaN");
                }
                else {
                    var answer =String.valueOf(StrictMath.tan(Double.parseDouble(res)));
                    list.add(answer);
                }
                index = temp+ 1;
                continue;
            }

            if(ch == '.')
            {
                char temp;
                Log.d(TAG, "expressionToList: found point!");
                if(expression.length() > index + 1)
                    temp = expression.charAt(index + 1);
                else {
                    list.clear();
                    list.add("Error");
                    return list;
                }
                if(isBrackets(String.valueOf(ch)) || isOperator(String.valueOf(ch)))
                {
                    list.clear();
                    list.add("Error");
                    return list;
                }
            }
            if(isOperator(String.valueOf(ch)))       //the char is neither the number nor the point
            {
                char check;
                if(ch == '-')
                {
                    if(index == 0)
                        check = '(';
                    else
                        check = expression.charAt(index - 1);
                    // check this sign whether is negative sign
                    if (isOperator(String.valueOf(check)) || isLeftBracket(String.valueOf(check)))
                    {
                        index++;
                        // is negative sign
                        list.add("(");
                        list.add("-1");
                        list.add("*");
                        continue;
                    }
                }
                // add to the list directly
                index++ ;
                list.add(String.valueOf(ch));
            }
            else if(isPercent(String.valueOf(ch)))
            {
                if(index != 0 && (isNumber(list.get(list.size()-1)) || isRightBracket(list.get(list.size() - 1))))
                {
                    // find out the last point of oprator
                    int test = list.size() - 1;
                    for(; test >= -1; test--)
                    {
                        if(test == -1)
                            break;
                        if(isOperator(list.get(test)))
                        {
                            break;
                        }
                    }
                    list.add(test + 1, "(");
                    index++;
                    list.add("/");
                    list.add("100");
                    list.add(")");
                }
                else
                {
                    list.clear();
                    list.add("Percent First");
                    return  list;
                }
            }
            else if(isBrackets(String.valueOf(ch)))
            {
                Log.d(TAG, "expressionToList: is Brackets: " + ch);
                index++;
                list.add(String.valueOf(ch));
            }
            else if(isNumber(String.valueOf(ch)))
            {
                // is number, build a new string to storage the char util it does not a number
                int test = addNumber(list, expression.substring(index));
                if(test == -1)
                    return list;
                index += test;
            }
            // Error input
            else
            {
                 list.clear();
                 list.add("Error");
            }
        }while (index < expression.length());
        Log.d(TAG, "expressionToList: the middle expression is: " + list);
        return list;
    }
    /**
     * add number into the list
     * @param list the list of infix expression
     * @param expression the source expression
     * @return the length of number length, -1 is expression error, 0 is empty
     */
    private static int addNumber(List<String> list, String expression) {
        int index = 0;
        StringBuilder str = new StringBuilder();
        char ch = expression.charAt(index);
        while (index < expression.length() && !isOperator(String.valueOf(ch = expression.charAt(index)))
                && !isBrackets(String.valueOf(ch)) && !isPercent(String.valueOf(ch))) {
            Log.d(TAG, "expressionToList: the number char is: " + ch);
            str.append(ch);
            if (ch == '.') {
                char temp;
                Log.d(TAG, "expressionToList: found point!");
                if (expression.length() > index + 1)
                    temp = expression.charAt(index + 1);
                else {
                    list.clear();
                    list.add("Error");
                    return -1;
                }
                if (isBrackets(String.valueOf(temp)) || isOperator(String.valueOf(temp))) {
                    list.clear();
                    list.add("Error");
                    return -1;
                }
            }
            index++;
        }
        list.add(str.toString());
        return index;
    }

    /**
     * transmit the infix expressions into the postfix expression
     * @param expressionList   the infix expression
     * @return the suffix expression
     */
    public static List<String> parseToPostfixExpression(List<String> expressionList)
    {
        //创建一个栈用于保存操作符
        Stack<String> opStack = new Stack<>();
        //创建一个list用于保存后缀表达式
        List<String> suffixList = new ArrayList<>();

        for(String item : expressionList)
        {
            if(isOperator(item))
            {
                //是操作符 判断操作符栈是否为空
                if(opStack.isEmpty() || "(".equals(opStack.peek()) || priority(item) > priority(opStack.peek()))
                    //为空或者栈顶元素为左括号或者当前操作符大于栈顶操作符直接压栈
                    opStack.push(item);
                else
                {
                    //否则将栈中元素出栈如队，直到遇到大于当前操作符或者遇到左括号时
                    while (!opStack.isEmpty() && !"(".equals(opStack.peek()))
                    {
                        if(priority(item) <= priority(opStack.peek()))
                        {
                            suffixList.add(opStack.pop());
                        }
                    }
                    //当前操作符压栈
                    opStack.push(item);
                }
            }
            else if(isNumber(item))
            {
                Log.d(TAG, "parseToPostfixExpression: the suffix number is: " + item);
                suffixList.add(item);
            }
            else if("(".equals(item))
                //是左括号，压栈
                opStack.push(item);

            else if(")".equals(item))
            {
                //是右括号 ，将栈中元素弹出入队，直到遇到左括号，左括号出栈，但不入队
                while (!opStack.isEmpty() && !"(".equals(opStack.peek()))
                        suffixList.add(opStack.pop());

                if(!opStack.isEmpty())
                    opStack.pop();
            }
            else
            {
                opStack.empty();
                suffixList.clear();
                suffixList.add(item);
                return suffixList;
            }
        }
        //循环完毕，如果操作符栈中元素不为空，将栈中元素出栈入队
        while (!opStack.isEmpty())
        {
            if(opStack.peek().equals("("))
                opStack.pop();
            else
                suffixList.add(opStack.pop());
        }
        Log.d(TAG, "parseToPostfixExpression: the Postfix expression is" + suffixList);
        return suffixList;
    }

    /**
     * get the priority of the operator
     * @param op the source operator
     * @return 1 if the operator is * or /, else is 0
     */
    public static int priority(String op)
    {
        if(op.equals("^"))
            return 2;
        if(op.equals("*") || op.equals("/"))
            return 1;
        else if(op.equals("+") || op.equals("-"))
            return 0;
        // is brackets
        else
            return -1;
    }
    /**
     * 根据后缀表达式list计算结果
     * @param list the Postfix expression
     * @return the answer of calculation
     */
    public  static String calculate(List<String> list)
    {
        Stack<String> stack = new Stack<>();
        for(String item: list)
        {
            if(isNumber(item))
            {
                Log.d(TAG, "calculate:  the parse double is " + item);
                //是数字
                try {
                    stack.push(item);
                }
                catch(NumberFormatException e)
                {
                    return "运算式错误！";
                }
                catch(NullPointerException e)
                {
                    return "错误";
                }
            }
            else if(isOperator(item))
            {
                BigDecimal num2;
                BigDecimal num1;
                //是操作符，取出栈顶两个元素
                try {
                     num2 = new BigDecimal(stack.pop());
                     num1 = new BigDecimal(stack.pop());
                }
                catch(EmptyStackException e)
                {
                    return "运算式错误！";
                }
                BigDecimal res;
                switch (item)
                {
                    case "^":
                        res = BigDecimal.ONE;
                        int split  = num2.intValue();
                        if(num2.intValue() < 0)
                        {

                            res = BigDecimal.valueOf(StrictMath.pow(num1.doubleValue(), num2.intValue()));
                        }
                        else
                            {
                                if(split > 50)
                                {
                                    while(split  > 50)
                                    {
                                        res = res.multiply(num1.pow(50));
                                        split -= 50;
                                        if(res.toString().length() > 60)
                                            return "值过大";
                                    }
                                    res = res.multiply(num1.pow(split));
                                }
                                else
                                    res = num1.pow(num2.intValue()).stripTrailingZeros();
                        }
                        break;
                    case "+":
                        res = num1.add(num2);
                        break;
                    case "-":
                        res = num1.subtract(num2);
                        break;
                    case "*":
                        res = num1.multiply(num2);
                        break;
                    case "/":
                        if(num2.equals(new BigDecimal(0)))
                            return "除数不能为零！";
                        else
                            try {
                                res = num1.divide(num2, 20, BigDecimal.ROUND_HALF_UP);
                            }
                            catch (ArithmeticException e)
                            {
                                return "除数不能为零！";
                            }
                        break;
                    case "Percent First":
                        return "运算式错误！";
                    case "Error":
                        return "运算式错误！";
                    case "NaN":
                        return "NaN";
                    case "Big":
                        return  "值过大";
                    default:
                        return "运算式错误！";
                }
                stack.push(String.valueOf(res));
            }
            else if(item.equals("Percent First"))
                return "运算式错误！";
            else if(item.equals("Error"))
                return "运算式错误！";
            else if(item.equals("NaN"))
                return "NaN";
            else if (item.equals("Big"))
                return "无穷大";
            else
                return "运算式错误！";
        }
        if(stack.isEmpty()) {
            return "运算式错误！";
        }
           Log.d(TAG, "calculate: the final answer is " + stack.peek());
        return NumberFormat(stack.pop());
    }

    /**
     * change number format that make sence
     * @param number the source number that may be with point or zero
     * @return the number that make sence
     */
    private static String NumberFormat(String number)
    {
        var format = new DecimalFormat();

        String style = "0.00E0";
        format.setMaximumFractionDigits(30);
        format.setGroupingUsed(false);
        if(number.length() == 1)
            return number;
        if(number.charAt(0) == '0' && number.charAt(1) == 'E')
            return "0";
        if(number.length() > 30)
        {

            var dec = new BigDecimal(number);
            Log.d(TAG, "NumberFormat: the processing: " + dec);
            format.applyPattern(style);
            return format.format(dec);
        }
        //if the number is decimal
        if(number.matches("^[\\\\+\\\\-]?([0-9]*[.][0-9]*)$"))
        {
                int n = number.length() - 1;
                while(n >= 0 && (number.charAt(n)) =='0')
                {
                    n--;
                }
                if(number.charAt(n) == '.')
                    n--;

                number = number.substring(0, n + 1);
        }
        return number;
    }
}

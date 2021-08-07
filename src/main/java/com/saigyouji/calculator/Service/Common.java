package com.saigyouji.calculator.Service;

import android.util.Log;

import java.util.Stack;


public class Common
{
    private static final String TAG = "Common";

    /**
     * return the string whether is constant
     * @param str the source string
     * @return  true if the string is constant sign , else return false
     */
    public static boolean isConstant(String str)
    {
        return str.equals("e") ||str.equals("\u03c0") || str.equals("G");
    }
    /**
     * filter out the last number from the string.
     * @param str the source string
     * @return the number string from the source string
     */
    public static String getLastNumber(String str)
    {

        Stack<Character> stack = new Stack<>();
        int index = str.length() - 1;
        for (int i = index; i >=  0; i--)
        {
            char ch = str.charAt(i);
            if(isNumber(String.valueOf(ch)) || ch == '.')
                stack.push(ch);
            else
                break;
        }
        StringBuilder ret = new StringBuilder();
        while(!stack.isEmpty())
        {
            ret.append(stack.pop());
        }
        Log.d(TAG, "getLastNumber: the last number is: " + ret);
        return ret.toString();

    }
    /**
     * juide whether the string is a operator
     * @param op the source string
     * @return true if the string is operator, else is false
     */
    public static boolean isOperator(String op)
    {
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("^");
    }
    /**
     * judge the string whether is the number
     * @param num the source number
     * @return true if the string is number, else return false
     */
    public static boolean isNumber(String num)
    {
        if(num.length() > 0)
        {
            char ch = num.charAt(0);
            if(ch == '-')
                return !num.equals("-");
            else
                return num.charAt(0) >= 48 && num.charAt(0) <= 57;
        }
        return false;
    }

    /**
     * judge the string whether is the brackets
     * @param str the source string
     * @return true if the string is brackets , else is false
     */
    public static boolean isBrackets(String str)
    {
        return isRightBracket(str)|| isLeftBracket(str);
    }
    public static boolean isRightBracket(String str)
    {
        return str.equals(")");
    }
    public static boolean isLeftBracket(String str)
    {
        return str.equals("(");
    }
    /**
     * judge the string whether a percent sign
     * @param str the source string
     * @return ture if the string is percent sign, else is false
     */
    public static boolean isPercent(String str)
    {
        return str.equals("%");
    }

}

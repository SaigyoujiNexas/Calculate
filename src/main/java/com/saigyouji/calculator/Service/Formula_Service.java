package com.saigyouji.calculator.Service;

import java.util.Stack;

public class Formula_Service {
    /**
     * To find match left brackets
     *
     * @param str the source string
     * @return false if not match, true if match
     */
    public static boolean Brackets_Match(String str) {
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            String temp = str.substring(i, i + 1);
            if (temp.equals("("))
                stack.push(temp);
            else if (temp.equals(")"))
                stack.pop();
        }
        return !stack.isEmpty();
    }

}

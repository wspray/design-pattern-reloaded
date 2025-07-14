package algorithm;

import java.util.Stack;

public class Classic {
    //我们需要编写一个Java算法，输入一个包含数字和运算符（+, -, *, /）的字符串表达式，计算并返回该表达式的结果。例如，输入 "3+5*2-8/4"，应返回 10（因为 3 + 10 - 2 = 10）。
    //解决思路
    //为了计算一个包含加减乘除的表达式字符串，我们可以使用**栈（Stack）**来辅助处理运算符的优先级。具体步骤如下：
    //初始化：
    //一个栈 numStack 用于存储数字。
    //一个栈 opStack 用于存储运算符。
    //一个变量 num 用于临时存储当前解析的数字。
    //一个变量 op 用于临时存储当前解析的运算符。
    //遍历字符串：
    //如果当前字符是数字，继续读取直到遇到非数字字符，将完整的数字压入 numStack。
    //如果当前字符是运算符：
    //如果 opStack 为空，直接将当前运算符压入 opStack。
    //如果 opStack 不为空，比较当前运算符与栈顶运算符的优先级：
    //如果当前运算符优先级高于栈顶运算符，直接压入 opStack。
    //否则，从 numStack 弹出两个数字，从 opStack 弹出一个运算符，计算结果后压入 numStack，然后将当前运算符压入 opStack。
    //注意处理多位数和负数的情况。
    //处理剩余运算符：
    //遍历完字符串后，如果 opStack 不为空，依次弹出运算符和数字进行计算，直到 opStack 为空。
    //返回结果：
    //最后 numStack 中剩下的唯一数字就是表达式的结果。
    //运算符优先级
    //* 和 / 的优先级高于 + 和 -。
    //相同优先级的运算符从左到右计算。
    public static int calculate(String expression) {
        Stack<Integer> numStack = new Stack<>();
        Stack<Character> opStack = new Stack<>();
        int num = 0;
        char op = '+'; // 初始运算符设为 '+'，方便处理第一个数字

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }

            if (!Character.isDigit(c) && c != ' ' || i == expression.length() - 1) {
                if (op == '+') {
                    numStack.push(num);
                } else if (op == '-') {
                    numStack.push(-num);
                } else if (op == '*') {
                    numStack.push(numStack.pop() * num);
                } else if (op == '/') {
                    numStack.push(numStack.pop() / num);
                }
                op = c;
                num = 0;
            }
        }

        int result = 0;
        while (!numStack.isEmpty()) {
            result += numStack.pop();
        }
        return result;
    }

    public static void main(String[] args) {
        String expression = "3+5*2-8/4";
        System.out.println("Result: " + calculate(expression)); // 输出: 10
    }
}

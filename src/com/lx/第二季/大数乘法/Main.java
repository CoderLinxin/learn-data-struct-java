package com.lx.第二季.大数乘法;

public class Main {
    // 大数乘法结果去除首部的 0
    public static String multiply(String num1, String num2) {
        return trimStart(mul(num1, num2));
    }

    // 乘法统一调用此方法: 支持负整数的乘法
    private static String mul(String num1, String num2) {
        if (num1 == null || num2 == null || num1.length() == 0 || num2.length() == 0) return "";

        int isNegative1 = num1.indexOf("-");
        int isNegative2 = num2.indexOf("-");

        String result = "";

        // num1为负,num2为正,乘法结果为负
        if (isNegative1 == 0 && isNegative2 < 0) {
            num1 = num1.substring(1);
            result = "-" + karatsuba(num1, num2);
        } else if (isNegative1 < 0 && isNegative2 == 0) { // num1为正,num2为负,乘法结果为负
            num2 = num2.substring(1);
            result = "-" + karatsuba(num1, num2);
        } else if (isNegative1 == 0 && isNegative2 == 0) { // num1、num2都为负,乘法结果为正
            num1 = num1.substring(1);
            num2 = num2.substring(1);
            result = karatsuba(num1, num2);
        } else { // num1、num2 都为正,乘法结果为正
            result = karatsuba(num1, num2);
        }

        return result;
    }

    /**
     * 使用分治思想配合 karatsuba 算法计算 num1 * num2
     * num1、num2 要求是正整数的字符串
     * 时间复杂度: 3T(n/2) + 3O(n) = 3T(n/2) + O(n) = O(n^(log2(3)))
     *
     * @param num1 乘数1
     * @param num2 乘数2
     * @return 乘法结果
     */
    private static String karatsuba(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();

        if (len1 <= 1 && len2 <= 1) // 一位乘法直接得出解
            return String.valueOf(Integer.parseInt(num1) * Integer.parseInt(num2));

        /* 将两个乘数的位数统一 */

        int interval = num1.length() - num2.length(); // 计算两个字符串的位数差
        if (interval > 0)  // num1 较长
            num2 = preFill(num2, interval);
        else if (interval < 0)  // num2 较长
            num1 = preFill(num1, -interval);

        // 将 num1、num2 都拆分成两半
        int half = len1 >> 1;
        String a = num1.substring(0, half);
        String b = num1.substring(half);
        String c = num2.substring(0, half);
        String d = num2.substring(half);

        String ac = mul(a, c); // 递归调用以计算 a * c,// T(n/2)
        String bd = mul(b, d); // 递归调用以计算 b * d,// T(n/2)
        // 递归调用以计算 (a-b)(c-d): 注意这里直接计算 a-b、c-d 即可,虽然 a 处于数 ab 的高位,b 处于数 ab 的低位,但是直接相减即可
        String a_bc_d = mul(subtraction(a, b), subtraction(c, d)); // T(n/2)

        // 计算 ac + bd, ac 需要在后面补 b.length+d.length 个 0 的大小
        String result1 = addition(backFill(ac, b.length() + d.length()), bd); // O(n)
        // 计算 ac + bd - (a-b)(c-d),最终结果需要在后面补 b.length 或 d.length (b.length == d.length)个 0,以便后续与 result1 相加
        String result2 = backFill(subtraction(addition(ac, bd), a_bc_d), b.length()); // O(n)

        // 将 result1 + result2  即乘法最终结果
        return addition(result1, result2); // O(n)
    }

    // 加法统一调用此方法: 支持负整数的加法
    private static String addition(String num1, String num2) {
        if (num1 == null || num2 == null || num1.length() == 0 || num2.length() == 0) return "";

        int isNegative1 = num1.indexOf("-");
        int isNegative2 = num2.indexOf("-");

        String result = "";

        // num1为负,num2为正: num2 - num1
        if (isNegative1 == 0 && isNegative2 < 0) {
            num1 = num1.substring(1);
            result = subtraction(num2, num1);
        } else if (isNegative1 < 0 && isNegative2 == 0) { // num1为正,num2为负:num1 - num2
            num2 = num2.substring(1);
            result = subtraction(num1, num2);
        } else if (isNegative1 == 0 && isNegative2 == 0) { // num1、num2都为负
            num1 = num1.substring(1);
            num2 = num2.substring(1);
            result = "-" + add(num1, num2);
        } else { // num1、num2 都为正
            result = add(num1, num2);
        }

        return result;
    }

    /**
     * 字符串大整数加法: num1 + num2, num1、num2 可以是任意位数的字符串
     * 仅支持整数加法
     * num1、num2 必须都是正数
     *
     * @param num1 加数1
     * @param num2 加数2
     * @return 结果
     */
    private static String add(String num1, String num2) {
        int interval = num1.length() - num2.length(); // 计算两个字符串的位数差

        if (interval > 0)  // num1 较长
            num2 = preFill(num2, interval);
        else if (interval < 0)  // num2 较长
            num1 = preFill(num1, -interval);

        int carry = 0; // 标记是否进位
        StringBuilder result = new StringBuilder(); // 存放 num1 + num2 的结果

        for (int i = num1.length() - 1; i >= 0; i--) { // 逐位相加
            int addend1 = Integer.parseInt(String.valueOf(num1.charAt(i)));
            int addend2 = Integer.parseInt(String.valueOf(num2.charAt(i)));

            int sum = addend1 + addend2 + carry;

            if (sum >= 10) { // 产生进位
                result.insert(0, sum - 10);
                carry = 1;
            } else { // 不产生进位
                carry = 0;
                result.insert(0, sum);
            }
        }

        if (carry == 1) // 加上最后一位加法产生的进位
            result.insert(0, carry);

        return result.toString();
    }

    /**
     * 对 str 前面填充 interval 个 0
     *
     * @param str      源字符
     * @param interval 待填充的 0 个数
     * @return 处理后的字符串
     */
    private static String preFill(String str, int interval) {
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < interval; i++)
            prefix.append("0");
        return prefix.append(str).toString();
    }

    /**
     * 对 str 后面填充 interval 个 0
     *
     * @param str      源字符
     * @param interval 待填充的 0 个数
     * @return 处理后的字符串
     */
    private static String backFill(String str, int interval) {
        StringBuilder prefix = new StringBuilder();
        prefix.append(str);
        for (int i = 0; i < interval; i++)
            prefix.append("0");
        return prefix.toString();
    }

    // 减法统一调用此方法: 支持负整数的减法
    private static String subtraction(String num1, String num2) {
        if (num1 == null || num2 == null || num1.length() == 0 || num2.length() == 0) return "";

        int isNegative1 = num1.indexOf("-");
        int isNegative2 = num2.indexOf("-");

        String result = "";

        // num1为负,num2为正
        if (isNegative1 == 0 && isNegative2 < 0) {
            num1 = num1.substring(1);
            result = "-" + add(num2, num1);
        } else if (isNegative1 < 0 && isNegative2 == 0) { // num1为正,num2为负
            num2 = num2.substring(1);
            result = add(num1, num2);
        } else if (isNegative1 == 0 && isNegative2 == 0) { // num1、num2都为负
            num1 = num1.substring(1);
            num2 = num2.substring(1);
            result = sub(num2, num1);
        } else { // num1、num2 都为正
            result = sub(num1, num2);
        }

        return result;
    }

    /**
     * 字符串大整数减法: num1 - num2, num1、num2 可以是任意位数的字符串
     * 仅支持整数减法
     * num1、num2 必须都是正的
     *
     * @param num1 被减数
     * @param num2 减数
     * @return 结果
     */
    private static String sub(String num1, String num2) {
        int interval = num1.length() - num2.length(); // 计算两个字符串的位数差

        // 填充至相同位数
        if (interval > 0)  // num1 较长
            num2 = preFill(num2, interval);
        else if (interval < 0)  // num2 较长
            num1 = preFill(num1, -interval);

        boolean isSwitch = false; // 记录被减数是否交换了

        // 判断是否需要交换被减数:
        // 大 - 小: 可直接计算
        // 小 - 大 = -(大 - 小)
        for (int i = 0; i < num1.length(); i++) {
            int position1 = Integer.parseInt(String.valueOf(num1.charAt(i)));
            int position2 = Integer.parseInt(String.valueOf(num2.charAt(i)));

            if (position1 < position2) { // 被减数较小, 则交换被减数
                String temp = num1;
                num1 = num2;
                num2 = temp;
                isSwitch = true;
                break;
            } else if (position1 > position2) { // 被减数较大直接跳出
                break;
            }
        }

        int carry = 0;
        StringBuilder result = new StringBuilder(); // 存放 num1 - num2 的结果

        for (int i = num1.length() - 1; i >= 0; i--) { // 逐位相减
            int sub1 = Integer.parseInt(String.valueOf(num1.charAt(i)));
            int sub2 = Integer.parseInt(String.valueOf(num2.charAt(i)));

            int subTract = sub1 - sub2 + carry;

            if (subTract >= 0) { // 不产生借位
                result.insert(0, subTract);
                carry = 0;
            } else { // 产生借位
                carry = -1;
                result.insert(0, subTract + 10);
            }
        }

        if (isSwitch)
            result.insert(0, "-"); // 被减数交换了则说明结果为负

        return result.toString();
    }

    /**
     * 去除字符串前面的 0
     *
     * @param str 源字符串
     * @return 处理后的字符串
     */
    private static String trimStart(String str) {
        boolean isStart = false;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (!isStart && c != '0')
                isStart = true;

            if (isStart)
                result.append(c);
        }

        return result.toString();
    }

    public static void main(String[] args) {
//        System.out.println(("adc").substring(0, 0)); // [begin, end)
//        System.out.println(multiply("3", "211"));
//        System.out.println(multiply("211", "3"));
//        System.out.println(multiply("210", "003"));
//        System.out.println(multiply("200", "3"));
//        System.out.println(multiply("200", "300"));
//        System.out.println(multiply("-200", "300"));
//        System.out.println(multiply("-200", "-300"));
//        System.out.println(multiply("1561894819981654561568948948", "0594898491555489484848948918911515156156"));
//        System.out.println("-------------" + multiply("1561894819981654561568948948", ""));

        System.out.println(addition("0", "5"));
    }
}

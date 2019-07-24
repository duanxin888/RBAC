package com.duanxin.utils;

import java.util.Date;
import java.util.Random;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName PasswordUtil
 * @Description 密码生成工具类
 * @date 2019/7/19 17:44
 */
public class PasswordUtil {

    private static final String[] word = {
              "a", "b", "c", "d", "e", "f",
              "g", "h", "j", "k", "m", "n",
              "p", "q", "r", "s", "t", "u",
              "v", "w", "x", "y", "z",
              "A", "B", "C", "D", "E", "F",
              "G", "H", "J", "K", "M", "N",
              "P", "Q", "R", "S", "T", "U",
              "V", "W", "X", "Y", "Z",
    };

    private static final String[] number = {
            "2", "3", "4", "5", "6", "7", "8", "9"
    };

    /**
     * @description 随机生成字母数字反复的9-11位密码
     * @param []
     * @date 2019/7/19 17:57
     * @return java.lang.String
     **/
    public static String randomPassword() {
        StringBuffer stringBuffer = new StringBuffer();
        Random random = new Random(System.currentTimeMillis());
        boolean flag = false;
        int len = random.nextInt(12) + 6;

        // 进行password赋值
        for (int i = 0; i < len; ++i) {
            if (flag) {
                // 赋数字
                stringBuffer.append(number[random.nextInt(number.length)]);
            } else {
                // 赋字母
                stringBuffer.append(word[random.nextInt(word.length)]);
            }
            // flag取反
            flag = !flag;
        }

        return stringBuffer.toString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(randomPassword());
        Thread.sleep(2000);
        System.out.println(randomPassword());
        Thread.sleep(2000);
        System.out.println(randomPassword());
    }

}

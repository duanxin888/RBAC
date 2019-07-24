package com.duanxin.utils;

import com.google.common.base.Splitter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName StringUtil
 * @Description String解析工具类
 * @date 2019/7/22 15:33
 */
public class StringUtil {

    /**
     * @description 将【1，2，3，， 】转化为Integer集合
     * @param [str]
     * @date 2019/7/22 15:38
     * @return java.util.List<java.lang.Integer>
     **/
    public static List<Integer> splitToIntList(String str) {
        List<String> strList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(str);
        List<Integer> list = strList.stream().map(strItem -> Integer.parseInt(strItem)).collect(Collectors.toList());
        return list;
    }
}

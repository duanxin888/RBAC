package com.duanxin.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName LevelUtil
 * @Description 计算当前的层次
 * @date 2019/7/17 18:03
 */
public class LevelUtil {

    /**
     * 层级之间的分隔符
     * */
    public static final String SEPARATOR = ".";

    public static final String ROOT = "0";

    /**
     * @description 计算规则
     * @param [parentLevel, parentId] 上一层的level，上一层的id
     * @date 2019/7/17 18:07
     * @return java.lang.String
     **/
    public static String calculateLevel(String parentLevel, Integer parentId) {
        // 表示是第一层
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }
}

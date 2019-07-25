package com.duanxin.params;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SearchLogParam
 * @Description 查找日志参数类
 * @date 2019/7/24 17:15
 */
@Getter
@Setter
@ToString
public class SearchLogParam {

    private Integer type;

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private String fromTime;

    private String toTime;
}

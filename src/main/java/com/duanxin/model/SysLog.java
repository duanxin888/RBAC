package com.duanxin.model;

import lombok.Data;

import java.util.Date;

/**
 * @title
 * @description 日志实体类
 * @author duanxin
 * @date 2019/7/17 7:10
 **/
@Data
public class SysLog {
    private Integer id;

    private Integer type;

    private Integer targetId;

    private String operator;

    private Date operateTime;

    private String operateIp;

    private Integer status;

}
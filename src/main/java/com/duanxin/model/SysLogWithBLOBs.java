package com.duanxin.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @title
 * @description 日志实体类
 * @author duanxin
 * @date 2019/7/17 7:11
 **/
@Setter
@Getter
@ToString
public class SysLogWithBLOBs extends SysLog {
    private String oldValue;

    private String newValue;
}
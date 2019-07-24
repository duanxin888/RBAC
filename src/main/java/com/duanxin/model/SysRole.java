package com.duanxin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @title
 * @description 角色实体类
 * @author duanxin
 * @date 2019/7/17 7:12
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRole {
    private Integer id;

    private String name;

    private Integer type;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;

}
package com.duanxin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @title
 * @description 用户实体类
 * @author duanxin
 * @date 2019/7/17 7:14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysUser {
    private Integer id;

    private String username;

    private String telephone;

    private String email;

    private String password;

    private Integer deptId;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;

}
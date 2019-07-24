package com.duanxin.model;

import lombok.*;

import java.util.Date;

/**
 * @title
 * @description 角色用户关联实体类
 * @author duanxin
 * @date 2019/7/17 7:13
 **/
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleUser {
    private Integer id;

    private Integer roleId;

    private Integer userId;

    private String operator;

    private Date operateTime;

    private String operateIp;

}
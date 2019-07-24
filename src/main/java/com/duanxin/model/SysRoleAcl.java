package com.duanxin.model;

import lombok.*;

import java.util.Date;

/**
 * @title
 * @description 角色权限关联实体类
 * @author duanxin
 * @date 2019/7/17 7:12
 **/
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRoleAcl {
    private Integer id;

    private Integer roleId;

    private Integer aclId;

    private String operator;

    private Date operateTime;

    private String operateIp;

}
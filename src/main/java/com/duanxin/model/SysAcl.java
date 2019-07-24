package com.duanxin.model;

import lombok.*;

import java.util.Date;

/**
 * @title
 * @description 权限实体类
 * @author duanxin
 * @date 2019/7/17 7:08
 **/
@Setter
@Getter
@EqualsAndHashCode(of = {"id"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysAcl {
    private Integer id;

    private String code;

    private String name;

    private Integer aclModuleId;

    private String url;

    private Integer type;

    private Integer status;

    private Integer seq;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;


}
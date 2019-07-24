package com.duanxin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @title
 * @description 权限模块实体类
 * @author duanxin
 * @date 2019/7/17 7:09
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysAclModule {
    private Integer id;

    private String name;

    private Integer parentId;

    private String level;

    private Integer seq;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;


}
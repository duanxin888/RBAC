package com.duanxin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @title SysDept
 * @description 部门实体类
 * @author duanxin
 * @date 2019/7/16 19:02
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysDept {
    private Integer id;

    private String name;

    private Integer parentId;

    private String level;

    private Integer seq;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;

}
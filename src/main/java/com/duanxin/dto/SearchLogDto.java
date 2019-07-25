package com.duanxin.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName SearchLogDto
 * @Description 查找日志适配器
 * @date 2019/7/24 17:19
 */
@Setter
@Getter
@ToString
public class SearchLogDto {

    private Integer type;

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private Date fromTime;

    private Date toTime;
}

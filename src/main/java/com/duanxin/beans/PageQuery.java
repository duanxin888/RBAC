package com.duanxin.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName PageQuery
 * @Description 用于分页展示的基本数据
 * @date 2019/7/20 7:23
 */
public class PageQuery {

    @Setter
    @Getter
    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1;

    @Getter
    @Setter
    @Min(value = 1, message = "每页展示数量不合法")
    private int pageSize = 10;

    @Setter
    private int offset;

    /**
     * @description 获取偏移量
     * @param []
     * @date 2019/7/20 7:27
     * @return int
     **/
    public int getOffset(){
        return (pageNo - 1) * pageSize;
    }
}

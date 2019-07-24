package com.duanxin.beans;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


/**
 * @author duanxin
 * @version 1.0
 * @ClassName PageResult
 * @Description 查询分页后的数据
 * @date 2019/7/20 7:29
 */
@Getter
@Setter
@ToString
@Builder
public class PageResult<T> {

    private List<T> data = Lists.newArrayList();

    private int total = 0;
}

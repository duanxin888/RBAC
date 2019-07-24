package com.duanxin.dto;

import com.duanxin.model.SysDept;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName DeptLevelDto
 * @Description 部门层级适配类
 * @date 2019/7/17 18:29
 */
@Setter
@Getter
public class DeptLevelDto extends SysDept {

    private List<DeptLevelDto> deptList = Lists.newArrayList();

    public static DeptLevelDto adapt(SysDept dept) {
        DeptLevelDto dto = new DeptLevelDto();
        BeanUtils.copyProperties(dept, dto);

        return dto;
    }
}

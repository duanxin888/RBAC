package com.duanxin.params;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName DeptParam
 * @Description 部门参数类
 * @date 2019/7/17 17:46
 */
@Setter
@Getter
@ToString
public class DeptParam {

    private Integer id;

    @NotBlank(message = "部门名称不能为空")
    @Length(max = 15, min = 2, message = "部门名称长度需要在2-15个字之间")
    private String name;

    private Integer parentId = 0;

    @NotNull(message = "展示顺序不可以为空")
    private Integer seq;

    @Length(max = 200, message = "备注长度需要在200字之内")
    private String remark;
}

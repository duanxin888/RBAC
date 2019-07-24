package com.duanxin.params;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName AclModuleParam
 * @Description 权限模块参数类
 * @date 2019/7/20 11:23
 */
@Setter
@Getter
public class AclModuleParam {

    private Integer id;

    @NotBlank(message = "权限模块名称不为空")
    @Length(min = 2, max = 20, message = "权限模块名称长度在2-20字以内")
    private String name;

    private Integer parentId = 0;

    @NotNull(message = "展示顺序不可以为空")
    private Integer seq;

    @NotNull(message = "权限模块状态不可以为空")
    @Min(value = 0, message = "权限模块状态不合法")
    @Max(value = 1, message = "权限模块状态不合法")
    private Integer status;

    @Length(max = 200, message = "备注长度需要在200字以内")
    private String remark;
}

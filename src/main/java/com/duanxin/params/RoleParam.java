package com.duanxin.params;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName RoleParam
 * @Description 角色参数类
 * @date 2019/7/21 11:36
 */
@Setter
@Getter
@ToString
public class RoleParam {

    private Integer id;

    @NotBlank(message = "角色名称不可以为空")
    @Length(min = 2, max = 20, message = "角色名称长度在2-20字以内")
    private String name;

    @NotNull(message = "角色类型不可以为空")
    @Min(value = 1, message = "角色类型不合法")
    @Max(value = 2, message = "角色类型不合法")
    private Integer type = 1;

    @NotNull(message = "角色状态不可以为空")
    @Min(value = 1, message = "角色状态不合法")
    @Max(value = 2, message = "角色状态不合法")
    private Integer status ;

    @Length(max = 200, message = "备注长度在200字以内")
    private String remark;
}

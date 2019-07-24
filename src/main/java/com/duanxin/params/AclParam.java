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
 * @ClassName AclParam
 * @Description 权限参数类
 * @date 2019/7/21 9:33
 */
@Getter
@Setter
@ToString
public class AclParam {

    private Integer id;

    @NotBlank(message = "权限名称不可以为空")
    @Length(min = 2, max = 20, message = "权限名称在2-20字之间")
    private String name;

    @NotNull(message = "权限所属的权限模块id不可以为空")
    private Integer aclModuleId = 0;

    @Length(min = 6, max = 100, message = "权限的url的长度在6-100字之间")
    private String url;

    @NotNull(message = "权限的类型不可以为空")
    @Min(value = 1, message = "权限类型不合法")
    @Max(value = 3, message = "权限类型不合法")
    private Integer type;

    @NotNull(message = "权限的状态不可以为空")
    @Min(value = 0, message = "权限状态不合法")
    @Max(value = 1, message = "权限状态不合法")
    private Integer status;

    @NotNull(message = "权限的次序不可以为空")
    private Integer seq;

    @Length(max = 200, message = "权限的备注长度在200字以内")
    private String remark;
}

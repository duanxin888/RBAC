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
 * @ClassName UserParam
 * @Description 用户参数类
 * @date 2019/7/19 17:09
 */
@Setter
@Getter
public class UserParam {

    private Integer id;

    @NotBlank(message = "用户名不可以为空")
    @Length(min = 1, max = 20, message = "用户名长度在20字以内")
    private String username;

    @NotBlank(message = "用户电话不可以为空")
    @Length(min = 1, max = 13, message = "用户电话长度在13字以内")
    private String telephone;

    @NotBlank(message = "用户邮箱不可以为空")
    @Length(min = 5, max = 50, message = "用户邮箱长度在50字以内")
    private String email;

    @NotNull(message = "用户所在部门不可以为空")
    private Integer deptId;

    @NotNull(message = "用户状态不可以为空")
    @Min(value = 0, message = "用户状态不合法")
    @Max(value = 2, message = "用户状态不合法")
    private Integer status;

    @Length(max = 200, message = "用户备注长度在150字以内")
    private String remark;
}

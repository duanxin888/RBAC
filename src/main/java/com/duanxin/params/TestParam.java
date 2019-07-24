package com.duanxin.params;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName TestParam
 * @Description TODO
 * @date 2019/7/17 11:36
 */
@Getter
@Setter
public class TestParam {

    @NotBlank
    private String msg;

    @NotNull
    @Max(value = 10, message = "id不能大于10")
    @Min(value = 0, message = "id至少大于等于0")
    private Integer id;

//    @NotEmpty
//    private List<?> list;
}

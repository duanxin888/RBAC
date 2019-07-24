package com.duanxin.dto;

import com.duanxin.model.SysAcl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName AclDto
 * @Description 权限适配器
 * @date 2019/7/21 17:37
 */
@Setter
@Getter
@ToString
public class AclDto extends SysAcl {

    /**
     * 是否要默认选中
     * */
    private boolean checked = false;

    /**
     * 是否有操作的权限
     * */
    private boolean hasAcl = false;

    /**
     * @description 进行适配
     * @param [sysAcl]
     * @date 2019/7/22 7:08
     * @return com.duanxin.dto.AclDto
     **/
    public static AclDto adapt(SysAcl sysAcl) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(sysAcl, dto);
        return dto;
    }
}

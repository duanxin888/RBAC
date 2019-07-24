package com.duanxin.dto;

import com.duanxin.model.SysAclModule;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author duanxin
 * @version 1.0
 * @ClassName AclModuleLevelDto
 * @Description 权限模块层级适配器
 * @date 2019/7/20 15:44
 */
@Setter
@Getter
@ToString
public class AclModuleLevelDto extends SysAclModule {

    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();

    private List<AclDto> aclList = Lists.newArrayList();

    public static AclModuleLevelDto adapt(SysAclModule sysAclModule) {
        AclModuleLevelDto dto = new AclModuleLevelDto();
        BeanUtils.copyProperties(sysAclModule, dto);

        return dto;
    }
}

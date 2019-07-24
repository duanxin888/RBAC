package com.duanxin.dao;

import com.duanxin.model.SysRoleAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title
 * @description 角色权限类持久层层接口
 * @author duanxin
 * @date 2019/7/17 7:30
 * @throws
 **/
public interface SysRoleAclMapper {
    /**
     * @description 根据id删除数据
     * @param [id]
     * @date 2019/7/22 7:36
     * @return int
     **/
    int deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * @description 添加数据
     * @param [record]
     * @date 2019/7/22 7:36
     * @return int
     **/
    int insert(@Param("record") SysRoleAcl record);

    /**
     * @description 判断式添加数据
     * @param [record]
     * @date 2019/7/22 7:36
     * @return int
     **/
    int insertSelective(@Param("record") SysRoleAcl record);

    /**
     * @description 根据id查询数据
     * @param [id]
     * @date 2019/7/22 7:37
     * @return com.duanxin.model.SysRoleAcl
     **/
    SysRoleAcl selectByPrimaryKey(@Param("id") Integer id);

    /**
     * @description 判断式更新数据
     * @param [record]
     * @date 2019/7/22 7:37
     * @return int
     **/
    int updateByPrimaryKeySelective(@Param("record") SysRoleAcl record);

    /**
     * @description 更新数据
     * @param [record]
     * @date 2019/7/22 7:37
     * @return int
     **/
    int updateByPrimaryKey(@Param("record") SysRoleAcl record);

    /**
     * @description 根据角色id集合获取权限id集合
     * @param [roleId]
     * @date 2019/7/22 7:40
     * @return java.util.List<java.lang.Integer>
     **/
    List<Integer> getAclIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);

    /**
     * @description 根据角色id删除之前的权限
     * @param [roleId]
     * @date 2019/7/22 15:54
     **/
    void deleteByRoleId(@Param("roleId") Integer roleId);

    /**
     * @description 批量添加数据
     * @param [roleAclList]
     * @date 2019/7/22 16:05
     **/
    void batchInsert(@Param("roleAclList") List<SysRoleAcl> roleAclList);


    /**
     * @description 根据权限id获取角色id集合
     * @param [aclId]
     * @date 2019/7/23 9:51
     * @return java.util.List<java.lang.Integer>
     **/
    List<Integer> getRoleIdListByAclId(@Param("aclId") Integer aclId);
}
package com.duanxin.dao;

import com.duanxin.model.SysRoleUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title
 * @description 角色用户类持久层层接口
 * @author duanxin
 * @date 2019/7/17 7:30
 * @throws
 **/
public interface SysRoleUserMapper {
    /**
     * @description 根据id删除信息
     * @param [id]
     * @date 2019/7/22 7:27
     * @return int
     **/
    int deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * @description 添加数据
     * @param [record]
     * @date 2019/7/22 7:28
     * @return int
     **/
    int insert(@Param("record") SysRoleUser record);

    /**
     * @description 判断式添加数据
     * @param [record]
     * @date 2019/7/22 7:28
     * @return int
     **/
    int insertSelective(@Param("record") SysRoleUser record);

    /**
     * @description 根据id查询数据
     * @param [id]
     * @date 2019/7/22 7:28
     * @return com.duanxin.model.SysRoleUser
     **/
    SysRoleUser selectByPrimaryKey(@Param("id") Integer id);

    /**
     * @description 判断式更新数据
     * @param [record]
     * @date 2019/7/22 7:28
     * @return int
     **/
    int updateByPrimaryKeySelective(@Param("record") SysRoleUser record);

    /**
     * @description 更新数据
     * @param [record]
     * @date 2019/7/22 7:29
     * @return int
     **/
    int updateByPrimaryKey(@Param("record") SysRoleUser record);

    /**
     * @description 根据用户id获取角色id集合
     * @param [userId]
     * @date 2019/7/22 7:33
     * @return java.util.List<java.lang.Integer>
     **/
    List<Integer> getRoleIdListByUserId(@Param("userId") Integer userId);

    /**
     * @description 通过角色id获取用户id集合
     * @param [roleId]
     * @date 2019/7/22 16:56
     * @return java.util.List<java.lang.Integer>
     **/
    List<Integer> getUserIdListByRoleId(@Param("roleId") Integer roleId);

    /**
     * @description 根据角色id删除数据
     * @param [roleId]
     * @date 2019/7/22 18:05
     **/
    void deleteByRoleId(@Param("roleId") Integer roleId);

    /**
     * @description 批量添加数据
     * @param [sysRoleUsers]
     * @date 2019/7/22 18:10
     **/
    void batchInsert(@Param("roleUserList") List<SysRoleUser> sysRoleUsers);

    /**
     * @description 根据角色id集合获取用户id集合
     * @param [roleIdList]
     * @date 2019/7/23 9:34
     * @return java.util.List<java.lang.Integer>
     **/
    List<Integer> getUserIdListByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);
}
package com.duanxin.dao;

import com.duanxin.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title
 * @description 角色类持久层层接口
 * @author duanxin
 * @date 2019/7/17 7:30
 * @throws
 **/
public interface SysRoleMapper {
    /**
     * @description 根据id删除角色
     * @param [id]
     * @date 2019/7/21 15:26
     * @return int
     **/
    int deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * @description 添加角色
     * @param [record]
     * @date 2019/7/21 15:26
     * @return int
     **/
    int insert(@Param("record") SysRole record);

    /**
     * @description 判断式添加角色
     * @param [record]
     * @date 2019/7/21 15:26
     * @return int
     **/
    int insertSelective(@Param("record") SysRole record);

    /**
     * @description 根据id查询
     * @param [id]
     * @date 2019/7/21 15:26
     * @return com.duanxin.model.SysRole
     **/
    SysRole selectByPrimaryKey(@Param("id") Integer id);

    /**
     * @description 判断式更新角色
     * @param [record]
     * @date 2019/7/21 15:27
     * @return int
     **/
    int updateByPrimaryKeySelective(@Param("record") SysRole record);

    /**
     * @description 更新角色
     * @param [record]
     * @date 2019/7/21 15:27
     * @return int
     **/
    int updateByPrimaryKey(@Param("record") SysRole record);

    /**
     * @description 根据id和名称查询
     * @param [name, id]
     * @date 2019/7/21 15:35
     * @return int
     **/
    int countRoleByName(@Param("name") String name,
                        @Param("id") Integer id);

    /**
     * @description 查询所有
     * @param []
     * @date 2019/7/21 15:45
     * @return java.util.List<com.duanxin.model.SysRole>
     **/
    List<SysRole> getAll();

    /**
     * @description 根据id集合获取角色集合
     * @param [roleIdList]
     * @date 2019/7/23 8:59
     * @return java.util.List<com.duanxin.model.SysRole>
     **/
    List<SysRole> getByIdList(@Param("roleIdList") List<Integer> roleIdList);
}
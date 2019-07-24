package com.duanxin.dao;

import com.duanxin.beans.PageQuery;
import com.duanxin.model.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title
 * @description 权限类持久层层接口
 * @author duanxin
 * @date 2019/7/17 7:24
 **/
public interface SysAclMapper {
    /**
     * @description 根据id删除权限
     * @param [id]
     * @date 2019/7/21 9:55
     * @return int
     **/
    int deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * @description 添加权限信息
     * @param [record]
     * @date 2019/7/21 9:55
     * @return int
     **/
    int insert(@Param("record") SysAcl record);

    /**
     * @description 判断式添加权限信息
     * @param [record]
     * @date 2019/7/21 9:55
     * @return int
     **/
    int insertSelective(@Param("record") SysAcl record);

    /**
     * @description 根据id查询
     * @param [id]
     * @date 2019/7/21 9:55
     * @return com.duanxin.model.SysAcl
     **/
    SysAcl selectByPrimaryKey(@Param("id") Integer id);

    /**
     * @description 判断式的更新权限信息
     * @param [record]
     * @date 2019/7/21 9:56
     * @return int
     **/
    int updateByPrimaryKeySelective(@Param("record") SysAcl record);

    /**
     * @description 更新权限信息
     * @param [record]
     * @date 2019/7/21 9:56
     * @return int
     **/
    int updateByPrimaryKey(@Param("record") SysAcl record);

    /**
     * @description 根据权限模块id，权限名称，权限id查询
     * @param [aclModuleId, name, id]
     * @date 2019/7/21 10:01
     * @return int
     **/
    int countAclByName(@Param("aclModuleId") Integer aclModuleId,
                       @Param("name") String name,
                       @Param("id") Integer id);

    /**
     * @description 分页查询权限信息
     * @param [aclModuleId, pageQuery]
     * @date 2019/7/21 10:31
     * @return java.util.List<com.duanxin.model.SysAcl>
     **/
    List<SysAcl> getAclListByAclModuleId(@Param("aclModuleId") Integer aclModuleId,
                                         @Param("page") PageQuery pageQuery);

    /**
     * @description 根据权限模块id查询权限总数
     * @param [aclModuleId]
     * @date 2019/7/21 10:39
     * @return int
     **/
    int countAclByAclModuleId(@Param("aclModuleId") Integer aclModuleId);

    /**
     * @description 获取所有权限
     * @param []
     * @date 2019/7/22 7:25
     * @return java.util.List<com.duanxin.model.SysAcl>
     **/
    List<SysAcl> getAll();

    /**
     * @description 根据id集合获取数据
     * @param [idList]
     * @date 2019/7/22 7:48
     * @return java.util.List<com.duanxin.model.SysAcl>
     **/
    List<SysAcl> getByIdList(@Param("idList") List<Integer> idList);

    /**
     * @description 根据url获取权限集合
     * @param [url]
     * @date 2019/7/23 10:45
     * @return java.util.List<com.duanxin.model.SysAcl>
     **/
    List<SysAcl> getByUrl(@Param("url") String url);
}
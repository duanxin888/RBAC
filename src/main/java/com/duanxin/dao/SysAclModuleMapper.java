package com.duanxin.dao;

import com.duanxin.model.SysAcl;
import com.duanxin.model.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title
 * @description 权限模块类持久层层接口
 * @author duanxin
 * @date 2019/7/17 7:28
 **/
public interface SysAclModuleMapper {
    /**
     * @description 根据id删除权限模块
     * @param [id]
     * @date 2019/7/20 11:48
     * @return int
     **/
    int deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * @description 保存权限模块
     * @param [record]
     * @date 2019/7/20 11:48
     * @return int
     **/
    int insert(@Param("record") SysAclModule record);

    /**
     * @description 判断式的保存权限模块
     * @param [record]
     * @date 2019/7/20 11:49
     * @return int
     **/
    int insertSelective(@Param("record") SysAclModule record);

    /**
     * @description 根据id查询信息
     * @param [id]
     * @date 2019/7/20 11:49
     * @return com.duanxin.model.SysAclModule
     **/
    SysAclModule selectByPrimaryKey(@Param("id") Integer id);

    /**
     * @description 判断式的更新权限模块
     * @param [record]
     * @date 2019/7/20 11:50
     * @return int
     **/
    int updateByPrimaryKeySelective(@Param("record") SysAclModule record);

    /**
     * @description 更新权限模块
     * @param [record]
     * @date 2019/7/20 11:50
     * @return int
     **/
    int updateByPrimaryKey(@Param("record") SysAclModule record);

    /**
     * @description 校验
     * @param [parentId, name, id]
     * @date 2019/7/20 11:55
     * @return int
     **/
    int countAclModelByName(@Param("parentId") Integer parentId,
                            @Param("name") String name,
                            @Param("id") Integer id);

    /**
     * @description 根据level进行查询
     * @param [level]
     * @date 2019/7/20 14:49
     * @return java.util.List<com.duanxin.model.SysAclModule>
     **/
    List<SysAclModule> getChildListByLevel(@Param("level") String level);

    /**
     * @description 批量更新
     * @param [modules]
     * @date 2019/7/20 15:06
     **/
    void batchUpdateLevel(@Param("modules") List<SysAclModule> modules);

    /**
     * @description 获取权限模块所有信息
     * @param []
     * @date 2019/7/20 15:57
     * @return java.util.List<com.duanxin.model.SysAclModule>
     **/
    List<SysAclModule> getAclModuleList();

    /**
     * @description 统计子模块总数
     * @param [parentId]
     * @date 2019/7/23 7:58
     * @return int
     **/
    int countByParentId(@Param("parentId") Integer parentId);
}
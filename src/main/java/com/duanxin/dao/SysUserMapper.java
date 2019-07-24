package com.duanxin.dao;

import com.duanxin.beans.PageQuery;
import com.duanxin.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title
 * @description 用户类持久层层接口
 * @author duanxin
 * @date 2019/7/17 7:30
 * @throws
 **/
public interface SysUserMapper {

    /**
     * @description 获取分页数据
     * @param [deptId, page]
     * @date 2019/7/20 7:42
     * @return java.util.List<com.duanxin.model.SysUser>
     **/
    List<SysUser> getPageByDeptId(@Param("deptId") Integer deptId, @Param("page") PageQuery page);

    /**
     * @description 查询总数量
     * @param [deptId]
     * @date 2019/7/20 7:40
     * @return int
     **/
    int countByDeptId(@Param("deptId") Integer deptId);

    /**
     * @description 根据id删除用户
     * @param [id]
     * @date 2019/7/19 19:03
     * @return int
     **/
    int deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * @description 插入数据
     * @param [record]
     * @date 2019/7/19 19:03
     * @return int
     **/
    int insert(@Param("record") SysUser record);

    /**
     * @description 判断式插入数据
     * @param [record]
     * @date 2019/7/19 19:03
     * @return int
     **/
    int insertSelective(@Param("record") SysUser record);

    /**
     * @description 查询数据
     * @param [id]
     * @date 2019/7/19 19:03
     * @return com.duanxin.model.SysUser
     **/
    SysUser selectByPrimaryKey(@Param("id") Integer id);

    /**
     * @description 判断式更新数据
     * @param [record]
     * @date 2019/7/19 19:04
     * @return int
     **/
    int updateByPrimaryKeySelective(@Param("record") SysUser record);

    /**
     * @description 更新数据
     * @param [record]
     * @date 2019/7/19 19:04
     * @return int
     **/
    int updateByPrimaryKey(@Param("record") SysUser record);

    /**
     * @description 根据telephone或email查询数据
     * @param [keyword]
     * @date 2019/7/19 19:04
     * @return com.duanxin.model.SysUser
     **/
    SysUser findByKeyword(@Param("keyword") String keyword);

    /**
     * @description 根据telephone和id查询
     * @param [telephone, id]
     * @date 2019/7/19 19:07
     * @return int
     **/
    int countByTelephone(@Param("telephone") String telephone);

    /**
     * @description 根据email和id查询
     * @param [email, id]
     * @date 2019/7/19 19:08
     * @return int
     **/
    int countByEmail(@Param("email") String email);

    /**
     * @description 通过id集合获取用户集合
     * @param [idList]
     * @date 2019/7/22 17:01
     * @return java.util.List<com.duanxin.model.SysUser>
     **/
    List<SysUser> getByIdList(@Param("idList") List<Integer> idList);

    /**
     * @description 获取所有用户数据
     * @param []
     * @date 2019/7/22 17:11
     * @return java.util.List<com.duanxin.model.SysUser>
     **/
    List<SysUser> getAll();
}
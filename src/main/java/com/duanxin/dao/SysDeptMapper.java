package com.duanxin.dao;

import com.duanxin.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title
 * @description 部门类持久层层接口
 * @author duanxin
 * @date 2019/7/17 7:29
 **/
public interface SysDeptMapper {
    /**
     * @description 根据id删除信息
     * @param [id]
     * @date 2019/7/18 9:13
     * @return int
     **/
    int deleteByPrimaryKey(Integer id);

    /**
     * @description 插入数据
     * @param [record]
     * @date 2019/7/18 9:14
     * @return int
     **/
    int insert(SysDept record);

    /**
     * @description 判断式插入数据
     * @param [record]
     * @date 2019/7/18 9:14
     * @return int
     **/
    int insertSelective(SysDept record);

    /**
     * @description 根据id查询数据
     * @param [id]
     * @date 2019/7/18 9:14
     * @return com.duanxin.model.SysDept
     **/
    SysDept selectByPrimaryKey(Integer id);

    /**
     * @description 判断式更新数据
     * @param [record]
     * @date 2019/7/18 9:15
     * @return int
     **/
    int updateByPrimaryKeySelective(@Param("record") SysDept record);

    /**
     * @description 更新数据
     * @param [record]
     * @date 2019/7/18 9:15
     * @return int
     **/
    int updateByPrimaryKey(SysDept record);

    /**
     * @description 获取所有数据
     * @param []
     * @date 2019/7/18 9:15
     * @return java.util.List<com.duanxin.model.SysDept>
     **/
    List<SysDept> getDeptList();

    /**
     * @description 根据level获取所有子部门数据
     * @param [level]
     * @date 2019/7/18 9:16
     * @return java.util.List<com.duanxin.model.SysDept>
     **/
    List<SysDept> getChildListByLevel(String level);

    /**
     * @description 批量更新数据
     * @param [depts]
     * @date 2019/7/18 9:16
     **/
    void batchUpdateLevel(@Param("depts") List<SysDept> depts);

    /**
     * @description 根据父部门id，自己name和id进行
     * @param [parentId, name, id]
     * @date 2019/7/18 9:22
     * @return int
     **/
    int countByNameAndParentId(@Param("parentId") Integer parentId,
                               @Param("name") String name,
                               @Param("id") Integer id);

    /**
     * @description 统计存在的子部门数
     * @param [parentId]
     * @date 2019/7/23 7:46
     * @return int
     **/
    int countByParentId(@Param("parentId") Integer parentId);
}
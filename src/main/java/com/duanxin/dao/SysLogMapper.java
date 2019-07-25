package com.duanxin.dao;

import com.duanxin.beans.PageQuery;
import com.duanxin.dto.SearchLogDto;
import com.duanxin.model.SysLog;
import com.duanxin.model.SysLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @title
 * @description 日志类类持久层层接口
 * @author duanxin
 * @date 2019/7/17 7:29
 * @throws
 **/
public interface SysLogMapper {
    /**
     * @description 根据id删除数据
     * @param [id]
     * @date 2019/7/24 17:32
     * @return int
     **/
    int deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * @description 添加数据
     * @param [record]
     * @date 2019/7/24 17:32
     * @return int
     **/
    int insert(@Param("record") SysLogWithBLOBs record);

    /**
     * @description 判断式添加数据
     * @param [record]
     * @date 2019/7/24 17:33
     * @return int
     **/
    int insertSelective(@Param("record") SysLogWithBLOBs record);

    /**
     * @description 根据id查询数据
     * @param [id]
     * @date 2019/7/24 17:33
     * @return com.duanxin.model.SysLogWithBLOBs
     **/
    SysLogWithBLOBs selectByPrimaryKey(@Param("id") Integer id);

    /**
     * @description 判断式更新数据
     * @param [record]
     * @date 2019/7/24 17:33
     * @return int
     **/
    int updateByPrimaryKeySelective(@Param("record") SysLogWithBLOBs record);

    /**
     * @description 更新数据
     * @param [record]
     * @date 2019/7/24 17:33
     * @return int
     **/
    int updateByPrimaryKeyWithBLOBs(@Param("record") SysLogWithBLOBs record);

    /**
     * @description 根据dto查询匹配数据
     * @param [dto]
     * @date 2019/7/24 17:38
     * @return int
     **/
    int countBySearchDto(@Param("dto") SearchLogDto dto);

    /**
     * @description 分页查询
     * @param [dto, page]
     * @date 2019/7/24 17:47
     * @return java.util.List<com.duanxin.model.SysLog>
     **/
    List<SysLogWithBLOBs> getPageListBySearchDto(@Param("dto") SearchLogDto dto, @Param("page") PageQuery page);
}
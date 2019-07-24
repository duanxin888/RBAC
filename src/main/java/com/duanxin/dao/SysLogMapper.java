package com.duanxin.dao;

import com.duanxin.model.SysLog;
import com.duanxin.model.SysLogWithBLOBs;

/**
 * @title
 * @description 日志类类持久层层接口
 * @author duanxin
 * @date 2019/7/17 7:29
 * @throws
 **/
public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLogWithBLOBs record);

    int insertSelective(SysLogWithBLOBs record);

    SysLogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    int updateByPrimaryKey(SysLog record);
}
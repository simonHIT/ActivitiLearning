package com.simon.sys.mapper;

import java.util.List;

import com.simon.sys.domain.LogInfo;
import com.simon.sys.vo.LogInfoVo;

public interface LogInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogInfo record);

    int insertSelective(LogInfo record);

    LogInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LogInfo record);

    int updateByPrimaryKey(LogInfo record);
    
    //全查询
    List<LogInfo> queryAllLogInfo(LogInfoVo logInfoVo);
}
package com.simon.sys.mapper;

import java.util.List;

import com.simon.sys.domain.LeaveBill;
import com.simon.sys.vo.LeaveBillVo;

public interface LeaveBillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LeaveBill record);

    int insertSelective(LeaveBill record);

    LeaveBill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LeaveBill record);

    int updateByPrimaryKey(LeaveBill record);
    
    List<LeaveBill> queryAllLeaveBill(LeaveBillVo leaveBillVo);
}
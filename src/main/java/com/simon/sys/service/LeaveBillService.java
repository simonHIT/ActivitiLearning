package com.simon.sys.service;

import com.simon.sys.domain.LeaveBill;
import com.simon.sys.utils.DataGridView;
import com.simon.sys.vo.LeaveBillVo;

public interface LeaveBillService {
	
	/**
	 * 查询所有请假单返回DataGridView
	 */
	public DataGridView queryAllLeaveBills(LeaveBillVo leaveBillVo);

	/**
	 * 添加请假单
	 * @param leaveBillVo
	 */
	public void addLeaveBill(LeaveBillVo leaveBillVo);

	/**
	 * 根据ID查询请假单
	 * @param id
	 * @return
	 */
	public LeaveBill queryLeaveBillById(Integer id);

	/**
	 * 修改请假单信息
	 * @param leaveBillVo
	 */
	public void updateLeaveBill(LeaveBillVo leaveBillVo);

	/**
	 * 删除
	 * @param
	 */
	public void deleteLeaveBill(Integer id);
	
}

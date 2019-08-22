package com.simon.sys.service;

import com.simon.sys.domain.Notice;
import com.simon.sys.utils.DataGridView;
import com.simon.sys.vo.NoticeVo;

public interface NoticeService {
	
	
	/**
	 * 查询所有公告返回DataGridView
	 */
	public DataGridView queryAllNotices(NoticeVo noticeVo);

	/**
	 * 添加公告
	 * @param noticeVo
	 */
	public void addNotice(NoticeVo noticeVo);

	/**
	 * 根据ID查询公告
	 * @param id
	 * @return
	 */
	public Notice queryNoticeById(Integer id);

	/**
	 * 修改公告信息
	 * @param noticeVo
	 */
	public void updateNotice(NoticeVo noticeVo);

	/**
	 * 删除
	 * @param
	 */
	public void deleteNotice(Integer id);
	
}

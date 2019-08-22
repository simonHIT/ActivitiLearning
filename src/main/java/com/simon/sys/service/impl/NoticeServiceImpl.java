package com.simon.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.simon.sys.domain.Notice;
import com.simon.sys.mapper.NoticeMapper;
import com.simon.sys.service.NoticeService;
import com.simon.sys.utils.DataGridView;
import com.simon.sys.vo.NoticeVo;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;
	

	@Override
	public DataGridView queryAllNotices(NoticeVo noticeVo) {
		Page<Object> page=PageHelper.startPage(noticeVo.getPage(), noticeVo.getLimit());
		List<Notice> data=this.noticeMapper.queryAllNotice(noticeVo);
		DataGridView view=new DataGridView(page.getTotal(), data);
		return view;
	}

	/**
	 * 添加部门
	 */
	@Override
	public void addNotice(NoticeVo noticeVo) {
		this.noticeMapper.insert(noticeVo);
	}

	@Override
	public Notice queryNoticeById(Integer id) {
		
		return this.noticeMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateNotice(NoticeVo noticeVo) {
		this.noticeMapper.updateByPrimaryKey(noticeVo);
	}

	@Override
	public void deleteNotice(Integer id) {
		this.noticeMapper.deleteByPrimaryKey(id);
	}
}

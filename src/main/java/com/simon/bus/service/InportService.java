package com.simon.bus.service;

import java.util.List;

import com.simon.bus.domain.Inport;
import com.simon.bus.vo.InportVo;
import com.simon.sys.utils.DataGridView;

public interface InportService {

	List<Inport> queryAllInport(InportVo inportVo);

	DataGridView loadInport(InportVo inportVo);

	void addInport(InportVo inportVo);

	Inport queryInportById(Integer id);

	void updateInport(InportVo inportVo);

	void deleteInport(InportVo inportVo);

}

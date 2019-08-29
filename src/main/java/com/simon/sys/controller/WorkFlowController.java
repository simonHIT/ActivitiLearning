package com.simon.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.simon.sys.service.WorkFlowService;
import com.simon.sys.utils.DataGridView;
import com.simon.sys.vo.WorkFlowVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作流的控制器
 * @author LJH
 *
 */
@Controller
@RequestMapping("workFlow")
public class WorkFlowController {

	@Autowired
	private WorkFlowService workFlowService;
	
	
	
	/**
	 * 跳转到流程管理的页面
	 */
	@RequestMapping("toWorkFlowManager")
	public String toWorkFlowManager() {
		return "sys/workFlow/workFlowManager";
	}
	
	/**
	 * 加载部署信息数据
	 */
	@RequestMapping("loadAllDeployment")
	@ResponseBody
	public DataGridView  loadAllDeployment(WorkFlowVo workFlowVo) {
		return this.workFlowService.queryProcessDeploy(workFlowVo);
	}
	
	/**
	 * 加载流程定义信息数据
	 */
	@RequestMapping("loadAllProcessDefinition")
	@ResponseBody
	public DataGridView  loadAllProcessDefinition(WorkFlowVo workFlowVo) {
		return this.workFlowService.queryAllProcessDefinition(workFlowVo);
	}

	/**
	 * 跳转到流程添加界面
	 * @return
	 */
	@RequestMapping("toAddWorkFlow")
	public String toAddWorkFlow(){
		return "sys/workFlow/workFlowAdd";
	}

	@RequestMapping("addWorkFlow")
	@ResponseBody
	public Map<String,Object> addWorkFlow(MultipartFile mf,String deploymentName){
		Map<String,Object> map=new HashMap<>();
		try {
			this.workFlowService.addWorkFlow(mf.getInputStream(),deploymentName);
			map.put("msg","部署成功");
		} catch (IOException e) {
			map.put("msg","部署失败");
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping("deleteWorkFlow")
	@ResponseBody
	public Map<String,Object> deleteWorkFlow(WorkFlowVo workFlowVo){
		Map<String,Object> map=new HashMap<>();
		workFlowService.deleteWorkFlow(workFlowVo.getDeploymentId());
		map.put("msg","删除成功");
		return map;
	}

	@RequestMapping("batchDeleteWorkFlow")
	@ResponseBody
	public Map<String,Object> deleteWorkFlowS(WorkFlowVo workFlowVo){

		String[] deploymentIds = workFlowVo.getIds();
		for (String id:deploymentIds
			 ) {
			workFlowService.deleteWorkFlow(id);
		}
		Map<String,Object> map=new HashMap<>();
		map.put("msg","删除成功");
		return map;
	}
}

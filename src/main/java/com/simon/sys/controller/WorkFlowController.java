package com.simon.sys.controller;

import com.simon.sys.domain.LeaveBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.simon.sys.service.WorkFlowService;
import com.simon.sys.utils.DataGridView;
import com.simon.sys.vo.WorkFlowVo;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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

    /**
     * 跳转流程图查看页面
     * @param workFlowVo
     * @return
     */
    @RequestMapping("toViewProcessImage")
	public String toViewProcessImage(WorkFlowVo workFlowVo,Model model){

        if (workFlowVo.getTaskId()!=null&&!workFlowVo.getTaskId().equals("")){
            Map<String, Object> coordinate = this.workFlowService.queryCoordinateByTaskId(workFlowVo.getTaskId());
            model.addAttribute("coordinate",coordinate);
        }
        return "sys/workFlow/viewProcessImage";

    }

    /**
     * 查看流程图图片
     * @param workFlowVo
     */
    @RequestMapping("viewProcessImage")
    public void viewProcessImage(WorkFlowVo workFlowVo, HttpServletResponse httpServletResponse){
        InputStream stream = this.workFlowService.queryProcessDeploymentImage(workFlowVo.getDeploymentId());
        try {
            BufferedImage image= ImageIO.read(stream);
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();

            ImageIO.write(image,"png",outputStream);
            outputStream.close();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 查看流程图图片
     * @param workFlowVo
     */
    @RequestMapping("viewProcessImageByTaskId")
    public void viewProcessImageByTaskId(WorkFlowVo workFlowVo, HttpServletResponse httpServletResponse,Model model){
        InputStream stream = this.workFlowService.queryProcessDeploymentImageByTaskId(workFlowVo.getTaskId());
        try {
            BufferedImage image= ImageIO.read(stream);
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();

            ImageIO.write(image,"png",outputStream);
            outputStream.close();
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 启动流程
     * @param workFlowVo
     * @return
     */
    @RequestMapping("startProcess")
    @ResponseBody
    public Map<String,Object> startProcess(WorkFlowVo workFlowVo){

        this.workFlowService.startProcess(workFlowVo.getId());
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("msg","启动成功");

        return resultMap;
    }

    /**
     * 跳转用户任务管理界面
     * @return
     */
    @RequestMapping("toTaskManager")
    public String toTaskManager(){
        return "sys/workFlow/taskManager";
    }

    /**
     * 查询当前用户的待办任务
     * @param workFlowVo
     * @return
     */
    @RequestMapping("loadCurrentUserTask")
    @ResponseBody
    public DataGridView loadCurrentUserTask(WorkFlowVo workFlowVo){
        return this.workFlowService.queryCurrentUserTask(workFlowVo);
    }

    /**
     * 跳转办理任务界面
     * @param workFlowVo
     * @return
     */
    @RequestMapping("toDoTask")
    public String toDoTask(WorkFlowVo workFlowVo, Model model){

        //根据任务id查请假单id
        LeaveBill leaveBill = this.workFlowService.queryLeaveBillByTaskId(workFlowVo.getTaskId());
        model.addAttribute("leaveBill",leaveBill);
        //根据任务id查询连线id
        List<String> outcomes = this.workFlowService.queryOutcomesByTaskId(workFlowVo.getTaskId());
        model.addAttribute("outcomes",outcomes);
        //根据任务id查询批注信息
        return "sys/workFlow/doTaskManager";
    }

    /**
     * 根据任务id查历史批注信息
     * @param workFlowVo
     * @return
     */
    @RequestMapping("loadAllCommentByTaskId")
    @ResponseBody
    public DataGridView loadAllCommentByTaskId(WorkFlowVo workFlowVo){

        return this.workFlowService.queryAllCommentByTaskId(workFlowVo.getTaskId());
    }

    @RequestMapping("doTask")
    @ResponseBody
    public Map<String,Object> doTask(WorkFlowVo workFlowVo){
        HashMap<String, Object> resultMap = new HashMap<>();

        try {
            this.workFlowService.completeTask(workFlowVo);
            resultMap.put("msg","任务完成成功");
        }catch (Exception e){
            resultMap.put("msg","任务完成失败");
            e.printStackTrace();
        }
        return resultMap;
    }


}

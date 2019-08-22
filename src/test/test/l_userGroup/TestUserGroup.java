package test.l_userGroup;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;


/**
 * @program: activiti
 * @description: 用户组测试
 * @author: simon
 * @create: 2019-08-21 16:47
 **/
public class TestUserGroup {

    private ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();

    /**
     * 创建用户和用户组
     */
    @Test
    public void createUserAndUserGroup(){
        IdentityService identityService = processEngine.getIdentityService();

        /**
         * 保存到group表
         */
        identityService.saveGroup(new GroupEntity("部门经理"));
        identityService.saveGroup(new GroupEntity("总经理"));

        /**
         * 保存到user表
         */
        identityService.saveUser(new UserEntity("小明"));
        identityService.saveUser(new UserEntity("小王"));
        identityService.saveUser(new UserEntity("小张"));

        /**
         * 建立用户与组织间的关系表 membership
         */
        identityService.createMembership("小明","部门经理");
        identityService.createMembership("小张","部门经理");
        identityService.createMembership("小王","总经理");

        System.out.println("保存完成");
        System.out.println("保存完成");
    }

}

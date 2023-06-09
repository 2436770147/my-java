package com.test;

import org.activiti.engine.*;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.task.Task;

import java.util.List;

//Activiti工作流
public class ActivitiTest {

    public static void main(String[] args) {
        //生成mysql表
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();

        //部署流程
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource("test.bpmn");//bpmn文件的名称
        builder.deploy();

        //启动流程
        RuntimeService runtimeService = processEngine.getRuntimeService();
        runtimeService.startProcessInstanceByKey("test");//流程的名称，也可以使用ByID来启动流程

        //查看任务列表
        TaskService taskService = processEngine.getTaskService();
        //根据assignee(代理人)查询任务
        String assignee = "student";
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();

        //首次运行的时候这个没有输出，因为第一次运行的时候扫描act_ru_task的表里面是空的，但第一次运行完成之后里面会添加一条记录，之后每次运行里面都会添加一条记录
        for (Task task : tasks) {
            System.out.println("taskId:" + task.getId() +
                    ",taskName:" + task.getName() +
                    ",assignee:" + task.getAssignee() +
                    ",createTime:" + task.getCreateTime());
        }
    }
}

package org.flowable;

import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HistoryServiceApiTest{

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private static ApplicationContext applicationContext ;


    //部署流程
    @Test
    public void deployProcess(){
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("test.bpmn20.xml")
                .deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());
    }

    //开启实例
    @Test
    public void startProcess(){
        ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("test");
        Assert.assertNotNull(processInstance);
    }

    //查询当前用户的待办任务
    @Test
    public void queryTask(){
        //办理人优先级：Assignee>Candidate users>Candidate groups
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("tom").list();
        System.out.println("You have " + tasks.size() + " tasks:");
        for (int i=0; i<tasks.size(); i++) {
            System.out.println((i+1) + ") " + tasks.get(i).getName());
        }
    }

    //完成任务
    @Test
    public void finish(){
        taskService.complete("32505");
    }

    //查询任务是否已结束
    @Test
    public void isFinished(){
        historyService.createHistoricProcessInstanceQuery().finished().list();
    }

}

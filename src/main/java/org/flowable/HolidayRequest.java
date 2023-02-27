package org.flowable;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HolidayRequest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

//    public static void main(String[] args) {
//        //初始化
//        ProcessEngineConfiguration cfg=new StandaloneProcessEngineConfiguration()
//                //设置默认在本数据库建表 解决数据库建表报错问题
//                .setJdbcUrl("jdbc:mysql://127.0.0.1:13306/flowable?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&nullCatalogMeansCurrent=true")
//                .setJdbcUsername("root")
//                .setJdbcPassword("root")
//                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
//                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//
//        ProcessEngine processEngine = cfg.buildProcessEngine();
//
//        //流程定义部署
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        Deployment deployment = repositoryService.createDeployment()
//                .addClasspathResource("holiday-request.bpmn20.xml")
//                .deploy();
////
////        //流程定义查询
////        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
////                .deploymentId(deployment.getId())
////                .singleResult();
////        System.out.println("Found process definition : " + processDefinition.getName());
//
//        //启动流程实例 ACT_RE_PROCDEF ACT_RE_DEPLOYMENT ACT_GE_BYTEARRAY
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//
//        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("employee", "tom");
//        variables.put("nrOfHolidays", "10");
//        variables.put("description", "have a rest");
//        ProcessInstance processInstance =
//                runtimeService.startProcessInstanceByKey("holidayRequest", variables);
//    }
//
//    //根据任务时长查询待办任务
//    @Test
//    public void query(){
//        ProcessEngineConfiguration cfg=new StandaloneProcessEngineConfiguration()
//                //设置默认在本数据库建表 解决数据库建表报错问题
//                .setJdbcUrl("jdbc:mysql://127.0.0.1:13306/flowable?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&nullCatalogMeansCurrent=true")
//                .setJdbcUsername("root")
//                .setJdbcPassword("root")
//                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
//                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//
//        ProcessEngine processEngine = cfg.buildProcessEngine();
//        TaskService taskService = processEngine.getTaskService();
//        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
//        System.out.println("You have " + tasks.size() + " tasks:");
//        for (int i=0; i<tasks.size(); i++) {
//            System.out.println((i+1) + ") " + tasks.get(i).getName());
//        }
//
//        Task task = tasks.get(0);
//        Map<String, Object> processVariables = taskService.getVariables(task.getId());
//        System.out.println(processVariables.get("employee") + " wants " +
//                processVariables.get("nrOfHolidays") + " of holidays. Do you approve this?");
//
//        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("approved", true);
//        taskService.complete(task.getId(), variables);
//    }

    /**
     * 根据候选组获取待办
     */
    @Test
    public void test(){
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("You have " + tasks.size() + " tasks:");
        for (int i=0; i<tasks.size(); i++) {
            System.out.println((i+1) + ") " + tasks.get(i).getName());
        }
    }

    @Test
    public void generateImg(){
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("holidayRequest")
                .list().get(0);

        String diagramResourceName = processDefinition.getDiagramResourceName();
        InputStream imageStream = repositoryService.getResourceAsStream(
                processDefinition.getDeploymentId(), diagramResourceName);

    }


    @Test
    public void startProcess(){
        //启动流程实例
//        ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("holidayRequest");
//        System.out.println(processInstance);
//        assertNotNull(processInstance);
        List<Task> list;
        //申领任务
//        list=taskService.createTaskQuery().taskCandidateGroup("managers").list();
//        list.forEach(task->{
//           System.out.println(task);
//        });
//        taskService.claim("17505","tom");

        //验证是否成功申领了任务
        list=taskService.createTaskQuery().taskAssignee("tom").list();
        list.forEach(task->{
            System.out.println(task);
        });
    }
}

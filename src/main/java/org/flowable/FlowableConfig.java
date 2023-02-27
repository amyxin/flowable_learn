package org.flowable;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.spring.ProcessEngineFactoryBean;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


/**
 * 流程全局配置
 */
@Configuration
public class FlowableConfig {

    /**
     * 数据源
     */
    @Autowired
    protected DataSource dataSource;
    /**
     * 事物管理
     */
    @Autowired
    protected PlatformTransactionManager transactionManager;

    /**
     * 初始化引擎
     *
     * @return
     */
    @Bean(name = {"processEngine"})
    public ProcessEngine processEngine() {
        try {
            return processEngineFactoryBean().getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化工厂化Bean
     *
     * @return
     */
    @Bean(name = {"processEngineFactoryBean"})
    public ProcessEngineFactoryBean processEngineFactoryBean() {
        ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
        factoryBean.setProcessEngineConfiguration(processEngineConfiguration());
        return factoryBean;
    }

    /**
     * 初始化具体的配置
     *
     * @return
     */
    @Bean(name = {"processEngineConfiguration"})
    public ProcessEngineConfigurationImpl processEngineConfiguration() {

        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();

        processEngineConfiguration.setDataSource(this.dataSource);
        processEngineConfiguration
                .setTransactionManager(this.transactionManager);
        return processEngineConfiguration;
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 仓库接口
     * 管理流程定义
     *
     * @return
     */
    @Bean
    public RepositoryService repositoryService() {
        return processEngine().getRepositoryService();
    }

    /**
     * 运行时接口
     * 管理正在运行的实例
     *
     * @return
     */
    @Bean
    public RuntimeService runtimeService() {
        return processEngine().getRuntimeService();
    }

    /**
     * 任务接口
     * 管理任务
     *
     * @return
     */
    @Bean
    public TaskService taskService() {
        return processEngine().getTaskService();
    }

    /**
     * 历史接口
     * 管理流程的所有历史数据
     *
     * @return
     */
    @Bean
    public HistoryService historyService() {
        return processEngine().getHistoryService();
    }

    /**
     * 人员接口
     *
     * @return
     */
    @Bean
    public IdentityService identityService() {
        return processEngine().getIdentityService();
    }

    /**
     * 引擎管理接口
     *
     * @return
     */
    @Bean
    public ManagementService managementService() {
        return processEngine().getManagementService();
    }
}

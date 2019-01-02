package com.znv.framework.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Mysql数据源配置
 * 支持多数据源
 * Created by MaHuiming on 2018/8/13.
 */
@Configuration
@MapperScan(basePackages = {"com.znv.framework.dao.mapper"},sqlSessionFactoryRef = "MySqlSessionFactory")
public class MysqlDatasource {

    /**
     * 数据源
     * @return
     */
    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.mysql")
    public javax.sql.DataSource dataSource() {
        //TODO 考虑如何使用druid的数据源
        //使用数据库连接池
        return new org.apache.tomcat.jdbc.pool.DataSource();
        //return DataSourceBuilder.create().build();
        //return new DruidDataSource();
    }

    /**
     * sessionFactory
     * @return
     * @throws Exception
     */
    @Bean(name = "MySqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver
        .getResources("classpath:/mapper/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 事务管理
     * @return
     */
    @Bean(name = "mysqlPlatformTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}

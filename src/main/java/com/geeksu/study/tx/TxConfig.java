package com.geeksu.study.tx;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 *  1 �������ϱ�ע@Transactional��ʾ��ǰ������һ�����񷽷�
 *  2 @EnableTransactionManagement ��������ע������������
 *  3 �����������������������
 *  @Bean
	public PlatformTransactionManager transactionManager() throws PropertyVetoException
 * 
 * ԭ��
 * 	1�� @EnableTransactionManagement 
 * 			����TransactionManagementConfigurationSelector �������лᵼ�����
 * 			�����������
 * 			AutoProxyRegistrar��ProxyTransactionManagementConfiguration
 *  2) AutoProxyRegistrar:��������ע��һ�� InfrastructureAdvisorAutoProxyCreator ���
 * 			InfrastructureAdvisorAutoProxyCreator����
 * 			���ú��ô����������ڶ��󴴽��Ժ󣬰�װ���󣬷���һ�����������ǿ�������������ִ�з������������������е��ã�
 * 	
 *  3) ProxyTransactionManagementConfiguration ����ʲô��
 *  	1�� ��������ע��������ǿ����
 *  		1��������ǿ��Ҫ������ע����Ϣ��AnnotationTransactionAttributeSource ��������ע��
 *  		2�� ����������
 *  			TransactionInterceptor�� ����������������Ϣ�������������
 *  			����һ��MethodInterceptor��
 *  			��Ŀ�귽��ִ�е�ʱ��
 *  				ִ����������
 *  				������������
 *  					1�� �Ȼ�ȡ������ص�����
 *  					2�� �ٻ�ȡ PlatformTransactionManager,�������û�����ָ���κ�transactionManager
 *  					   ���ջ�������а������ͻ�ȡһ��	PlatformTransactionManager 
 *  					3�� ִ��Ŀ�귽��
 *  						����쳣����ȡ���������������������������ع�����
 *  						�����������������������ύ����
 */
@EnableTransactionManagement
@ComponentScan("com.geeksu.study.tx")
@Configuration
public class TxConfig {

	@Bean
	public DataSource dataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser("root");
		dataSource.setPassword("1234");
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test");
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() throws PropertyVetoException {
		return new JdbcTemplate(dataSource());
	}
	
	//ע�������������������
	@Bean
	public PlatformTransactionManager transactionManager() throws PropertyVetoException {
		return new DataSourceTransactionManager(dataSource());
	}
}

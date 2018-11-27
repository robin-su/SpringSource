package com.geeksu.study.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 自动装配： Spring利用依赖注入（DI）,完成对IOC容器中各个组件的依赖关系赋值
 * AutowiredAnnotationBeanPostProcessor ApplicationContextAwareProcessor
 */
@PropertySource("classpath:/dbConfig.properties")
@Configuration
public class MainConfigOfAutowired implements EmbeddedValueResolverAware {
	
	@Value("${db.user}")
	private String user;
	
	private StringValueResolver valueResolver;

	@Bean
	public DataSource dataSourceTest(@Value("${db.passowrd}") String pwd) throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(pwd);
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
		String resolveStringValue = valueResolver.resolveStringValue("${db.driverClass}");
		dataSource.setDriverClass(resolveStringValue);
		return dataSource;
	}

	@Bean
	public DataSource dataSourceDev(@Value("${db.passowrd}") String pwd) throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(pwd);
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/ssm_crud");
		String resolveStringValue = valueResolver.resolveStringValue("${db.driverClass}");
		dataSource.setDriverClass(resolveStringValue);
		return dataSource;
	}

	@Bean
	public DataSource dataSourceProd(@Value("${db.passowrd}") String pwd) throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(pwd);
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/scw_0515");
		String resolveStringValue = valueResolver.resolveStringValue("${db.driverClass}");
		dataSource.setDriverClass(resolveStringValue);
		return dataSource;
	}
	
	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.valueResolver = resolver;
	}

}

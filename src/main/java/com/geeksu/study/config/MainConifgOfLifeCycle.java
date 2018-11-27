package com.geeksu.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.geeksu.study.bean.Blue;

/**
 * BeanPostProcessor:bean的后置处理器
 * 	在Bean的初始化前后进行处理工作
 * 		postProcessBeforeInitialization：在初始化之前进行后置处理工作
 *  	postProcessAfterInitialization：在初始化之后进行后置处理工作
   *    遍历得到容器中所有的BeanPostProcessor,挨个执行beforeInitialization，一旦返回null,跳出for循,不会执行后面的BeanPostProcessor
 *populateBean(beanName, mbd, instanceWrapper) 给Bean进行属性赋值
 *initializeBean {
 *  applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 * 	invokeInitMethods(beanName, wrappedBean, mbd); 执行自定义初始化
 * 	applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 * }
 * 
 * Spring底层对BeanPostProcessor
 *    bean赋值，注入其他组件，@Autowired,生命周期注解功能，@Async,xxx BeanPostProcessor
 */
@ComponentScan(basePackages=("com.geeksu.study"))
@Configuration
public class MainConifgOfLifeCycle {
	
	@Bean
	public Blue getBlue() {
		return new Blue();
	}

}

package com.geeksu.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.geeksu.study.bean.Blue;

/**
 * BeanPostProcessor:bean�ĺ��ô�����
 * 	��Bean�ĳ�ʼ��ǰ����д�����
 * 		postProcessBeforeInitialization���ڳ�ʼ��֮ǰ���к��ô�����
 *  	postProcessAfterInitialization���ڳ�ʼ��֮����к��ô�����
   *    �����õ����������е�BeanPostProcessor,����ִ��beforeInitialization��һ������null,����forѭ,����ִ�к����BeanPostProcessor
 *populateBean(beanName, mbd, instanceWrapper) ��Bean�������Ը�ֵ
 *initializeBean {
 *  applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 * 	invokeInitMethods(beanName, wrappedBean, mbd); ִ���Զ����ʼ��
 * 	applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 * }
 * 
 * Spring�ײ��BeanPostProcessor
 *    bean��ֵ��ע�����������@Autowired,��������ע�⹦�ܣ�@Async,xxx BeanPostProcessor
 */
@ComponentScan(basePackages=("com.geeksu.study"))
@Configuration
public class MainConifgOfLifeCycle {
	
	@Bean
	public Blue getBlue() {
		return new Blue();
	}

}

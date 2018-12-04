package com.geeksu.study.ext;

import java.util.concurrent.Executor;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import com.geeksu.study.bean.Blue;

/**
 * 
 * ��չԭ��	
 * BeanPostProcessor:bean���ô�������bean���������ʼ��ǰ���������
 * BeanFactoryPostProcessor:beanFactory�ĺ��ô�����
 * 		��beanFactory��׼��ʼ��֮����ã������ƺ��޸�BeanFactory�е����ݣ�
 * 		���е�bean�����Ѿ�������ص�beanFactory�У�����bean��ʵ����δ������
 * 
 * 1��ioc������������
 * 2��invokeBeanFactoryPostProcessors(beanFactory);ִ��BeanFactoryPostProcessors
 * 		����ҵ����е�BeanFactoryPostProcessor��ִ�����ǵķ���
 * 		1�� ֱ����BeanFactory���ҵ�����������BeanFactoryPostProcessor���������ִ�����ǵķ���
 * 		2�� �ڳ�ʼ�������������ǰ��ִ��
 * 
 * 2.interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 * 		postProcessBeanDefinitionRegistry();
 * 		������bean������Ϣ��Ҫ�����أ�beanʵ����δ��������
 * 
 * 		������BeanFactoryPostProcessorִ�У�����BeanDefinitionRegistryPostProcessor���������ٶ������һЩ�����
 * 
 * ԭ��
 * 	 1��IOC��������
 * 	 2��refresh()->invokeBeanFactoryPostProcessors(beanFactory);
 * 	 3) �������л�ȡ�����е� BeanDefinitionRegistryPostProcessor �����
 * 		1.���δ������е�postProcessBeanDefinitionRegistry()����
 * 		2.��������postProcessBeanFactory()����BeanFactoryPostProcessor
 * 
 * 
 * 3.ApplicationListener:���������з������¼����¼�����ģ�Ϳ�����
 * 	 	public interface ApplicationListener<E extends ApplicationEvent> extends EventListener
 * 		 ���� ApplicationEvent ������������¼�
 * 		
 * 		���裺
 * 			1��дһ������������ĳ���¼���ApplicationEvent�������ࣩ
 * 			@EventListener;
 * 			ԭ��ʹ��EventListenerMethodProcessor�����������������ϵ�@EventListenerע��
 * 			2���Ѽ��������뵽�����У�
 * 			3��ֻҪ������������¼��ķ��������Ǿ��ܼ���������¼�
 * 				ContextRefreshedEvent������ˢ����ɣ�����bean����ȫ�������ᷢ������¼�
 * 				ContextClosedEvent���ر������ᷢ������¼�
 * 			4�� ����һ���¼�
 * 				applicationContext.publishEvent()
 * 
 * ԭ��
 * 		ContextRefreshedEvent,IOCTest_Ext$1[source=�ҷ������¼�],ContextClosedEvent
 * 1) ContextRefreshedEvent�¼���
 * 		1) ������������refresh()
 * 		2) finishRefresh();����ˢ����ɷ���ContextRefreshedEvent�¼�
 * 
 * 2) �Լ������¼�
 * 3�������رջᷢ��ContextClosedEvent��
 * 		
 * 			���¼��������̡���
 * 			3) publishEvent(new ContextRefreshedEvent(this));
 * 				1) ��ȡʱ��Ķಥ�����ɷ�������getApplicationEventMulticaster()
 * 				2) multicastEvent�ɷ��¼�:
 * 				3) ��ȡ�����е�ApplicationListener
 * 					for (final ApplicationListener<?> listener : getApplicationListeners(event, type))
 * 					1) ����Executor������֧��ʹ��Executor�����첽�ɷ���
 * 						Executor executor = getTaskExecutor();
 * 					2) ����ͬ���ķ�ʽֱ��ִ��listener������ invokeListener(listener, event);
 * 					�õ�listener�ص�onApplicationEvent����
 * ���¼��ಥ�����ɷ�������	
 * 		1) ������������refresh()
 * 		2) initApplicationEventMulticaster();��ʼ��ApplicationEventMulticaster
 * 			1) ��ȥ����������û��id="applicationEventMulticaster"�����
 * 			2) ���û��this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
 * 			���Ҽ��뵽�����У����ǾͿ������������Ҫ�ɷ��¼���ʱ���Զ�ע�����applicationEventMulticaster 
 * ������������Щ��������
 * 		1) ������������refresh()
 * 		2) registerListeners();ע�������
 * 			���������õ����еü�������������ע�ᵽapplicationEventMulticaster��
 * 			//��listenerע�ᵽApplicationEventMulticaster��
 * 			getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
 * 
 *	SmartInitializingSingletonԭ��
 *		1) IOC������������refresh
 *		2) finishBeanFactoryInitialization(beanFactory); ��ʼ��ʣ�µĵ�ʵ��bean
 *			1) �ȴ������еĵ�ʵ��Bean;getBean()
 *			2) ��ȡ���д����õĵ�ʵ��bean���ж��Ƿ���SmartInitializingSingleton���ͣ�
 *				����Ǿ͵���smartSingleton.afterSingletonsInstantiated();
 */
@ComponentScan("com.geeksu.study.ext")
@Configuration
public class ExtConfig {
	
	@Bean
	public Blue blue() {
		return new Blue();
	}
	
	

}

package com.geeksu.study.aop;

import java.util.List;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * AOP����̬����:
 * 		ָ�ڳ��������ڼ䶯̬�Ľ�ĳ�δ������뵽ָ������λ�ý������еı��ģʽ
 *
 * 1.����aopģ�飬String AOP:(spring-aspects)
 * 2.����һ��ҵ���߼��ࣺMathCalculator,��ҵ���߼����е�ʱ����־���д�ӡ(����֮ǰ���������н��������������쳣)
 * 3.����һ����־�����ࣨLogAspects��:����������ķ�����Ҫ��̬��֪MathCalculator.div���е�����
 * 		֪ͨ������
 * 			ǰ��֪ͨ(@Before)��logStart����Ŀ�귽������֮ǰ����
 * 			����֪ͨ(@After)��logEnd:��Ŀ�귽������֮������
 * 			����֪ͨ(@AfterReturning)��logReturn����Ŀ�귽����������֮������
 * 			�쳣֪ͨ(@AfterThrowing)��logException����Ŀ�귽�����г����쳣�Ժ�����
 * 			����֪ͨ(@Around): ��̬�����ֶ��ƽ�Ŀ�귽�����У�joinPoint.proceed()��
 * 4.���������Ŀ�귽����ע��ʱ�ε����У�֪ͨע�⣩
 * 5.���������ҵ���߼��ࣨĿ�귽�������ࣩ�����뵽������
 * 6.�������Spring�ĸ����������ࣨ���������ϼ�һ��ע�⣩
 * 7.���������м�@EnableAspectJAutoProxy����������ע���AOPģʽ��
 * 		��Spring�кܶ��@EnableXxx����
 * 
 *  3����
 *  	1����ҵ���߼�����������඼���뵽�����У�����spring�ĸ��������ࣨ@Aspect��
 *      2) ���������ϵ�ÿ��һ�����ϱ�עע�⣬����spring��ʱ�ε����У��������ʽ��
 *      3����������ע���aopģʽ��@EnableAspectJAutoProxy
 * 
 * AOPԭ��������������ע����ʲô�����������ʲôʱ�����������������Ĺ�����ʲô?��
 * 1.@EnableAspectJAutoProxy��ʲô��
 * 		@Import(AspectJAutoProxyRegistrar.class):�������е���AspectJAutoProxyRegistrar
 * 			����AspectJAutoProxyRegister�Զ���������ע��bean:BeanDefinietion
 * 			internalAutoProxyCreator = AnnotationAwareAspectJAutoProxyCreator
 *   	
 *   	��������ע��һ��AnnotationAwareAspectJAutoProxyCreator:
 *  
 * 2.AnnotationAwareAspectJAutoProxyCreator
 * 		AnnotationAwareAspectJAutoProxyCreator 
 * 			-> AspectJAwareAdvisorAutoProxyCreator
 * 				-> AbstractAdvisorAutoProxyCreator
 * 					-> AbstractAutoProxyCreator
 * 						implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 * 						��ע���ô���������Bean��ʼ�����ǰ���������飩���Զ�װ��BeanFactory
 * 
 * AbstractAutoProxyCreator.setBeanFactory()
 * AbstractAutoProxyCreator �к��ô��������߼���
 * 
 * AbstractAdvisorAutoProxyCreator.setBeanFactory() -> initBeanFactory()
 * AspectJAwareAdvisorAutoProxyCreator.
 * 
 * AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()
 * 
 * ���̣�
 * 	1.���������ഴ��IOC����
 *  2.ע�������࣬����refresh() ˢ������
 *  3.registerBeanPostProcessors(beanFactory);ע��bean�ĺ��ô���������������bean�Ĵ���
 *  	1��. �Ȼ�ȡioc�����Ѿ������˵���Ҫ�������������BeanPostProcessor
 *  		String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);
 *   	2)  �������мӱ��BeanPostProcessor
 *   	3������ע��ʵ����PriorityOrdered�ӿڵ�BeanPostProcessor
 *   	4) �ٸ�������ע��ʵ����Ordered�ӿڵ�BeanPostProcessor 
 *   	5�� ע��ûʵ�����ȼ��ӿڵ�BeanPostProcessor
 *   	6) ע��BeanPostProcessor��ʵ���Ͼ��Ǵ���BeanPostProcessor���󣬱�����������
 *   	����internalAutoProxyCreator��BeanPostProcessor��AnnotationAwareAspectJAutoProxyCreator��
 *         1������Bean��ʵ��
 *         2��populateBean(beanName,mbd,instanceWrapper):��Bean�ĸ������Ը�ֵ
 *         3��initializeBean:��ʼ��Bean
 *         		1) invokeAwareMethods():����Aware�ӿڵķ����ص�
 *         		2�� applyBeanPostProcessorsBeforeInitialization��Ӧ�ú��ô�������BeforeInitialization.postProcessBeforeInitialization
 *         		3) invokeInitMethods():ִ���Զ���ĳ�ʼ������
 *         		4) applyBeanPostProcessorsAfterInitialization():ִ�к��ô�������postProcessAfterInitialization
 *         4��BeanBostProcessor(AnnotationAwareAspectJAutoProxyCreator):�����ɹ� -- aspectJAdvisorBuilder
 *       7) ��BeanPostProcessorע�ᵽBeanFactory�У�
 *       	beanFactory.addBeanPostProcessor(postProcessor)
 *       
 *===== �����Ǵ�����ע��AnnotationAwareAspectJAutoProxyCreator�Ĺ���
 *		AnnotationAwareAspectJAutoProxyCreator => InstantiationAwareBeanPostProcessor
 *		4) finishBeanFactoryInitialization(beanFactory);���BeanFactory��ʼ������������ʣ�µĵ�ʵ��Bean
 *			1)������ȡ�����е����е�Bean,���δ�������getBean(beanName);
 *			2)getBean(beanName) -> doGetBean() -> getSingleton()
 *			3)����Bean
 *				��AnnotationAwareAspectJAutoProxyCreator������bean����֮ǰ����һ�����أ�InstantiationAwareBeanPostProcessor�����postProcessBeforeInstantiation��
 *				1) �ȴӻ����л�ȡ��ǰBean,����ܻ�ȡ����˵��Bean��֮ǰ�Ѿ����������ģ�ֱ��ʹ�ã������ٴ���
 *				  ֻҪ�����õ�Bean���ᱻ��������
 *				2��createBean()������bean AnnotationAwareAspectJAutoProxyCreator�����κ�Bean����֮ǰ�ȳ��Է���Bean��ʵ��
 *					��BeanPostProcessor����Bean���󴴽���ɳ�ʼ��ǰ����õġ�
 *					��InstantiationAwareBeanPostProcessor���ڴ���Beanʵ��֮ǰ�ȳ����ú��ô��������ض���
 *					1) resolveBeforeInstantiation(beanName,mbdToUse);����BeforeInstantiation
 *						ϣ�����ô������ڴ��ܷ���һ�������������ܷ��ش�������ʹ�ã�������ܾͼ���
 *						1�� ���ô������ȳ��Է��ض���
 *						bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 *							�õ����к��ô������������ InstantiationAwareBeanPostProcessor
 *							��ִ��postProcessBeforeInstantiation
						if (bean != null) {
							bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
						}
 *					2�� doCreateBean(beanName,mbdToUse,args);����ȥ����һ��beanʵ����������3.6����һ��
 *					3��
 *	AnnotationAwareAspectJAutoProxyCreator��AnnotationAwareAspectJAutoProxyCreator�������ã�
 *	1�� ÿ��Bean����֮ǰ������postProcessBeforeInstantiation()
 *		����MathCalculator��LogAspect�Ĵ���
 *		1�� �жϵ�ǰBean�Ƿ���adviseBeans�У�����������Ҫ����ǿ��bean��
 *		2)   �жϵ�ǰbean�Ƿ��ǻ������͵�
 *			boolean retVal = Advice.class.isAssignableFrom(beanClass) ||
				Pointcut.class.isAssignableFrom(beanClass) ||
				Advisor.class.isAssignableFrom(beanClass) ||
				AopInfrastructureBean.class.isAssignableFrom(beanClass)
		   �����Ƿ������棨@Aspect��
		3) �Ƿ���Ҫ����
			1����ȡ��ѡ����ǿ����������֪ͨ��������List<Advisor> candidateAdvisors��
			      ÿ����װ��֪ͨ��������ǿ����InstantiationModelAwarePointcutAdvisor
		                �ж�ÿһ����ǿ���Ƿ��� AspectJPointcutAdvisor ���͵�
		    2�� ��Զ����false
	2)��������
	postProcessAfterInitialization
		return wrapIfNecessary(bean,beanName,cacheKey);��װ�����Ҫ�Ļ�
		1����ȡ��ǰbean��������ǿ����֪ͨ������Object[] specificInterceptors
			1.�ҵ���ѡ�����е���ǿ��������Щ֪ͨ��������Ҫ���뵱ǰbean�ķ�����
			2.��ȡ������beanʹ�õ���ǿ��
			3.����ǿ������
		2�����浱ǰbean��advisedBeans��
		3�������ǰbean��Ҫ��ǿ��������ǰbean�Ĵ������
			1�� ��ȡ��ǰbean��������ǿ����֪ͨ������
			2�� ���浽proxyFactory
			3)  �����������:Spring�Զ�����
				JdkDynamicAopProxy(config);jdk��̬����
				ObjenesisCglibAopProxy(config)��cglib�Ķ�̬����
		4���������з��ص�ǰ���ʹ��cglib��ǿ�˵Ĵ������
		5�� �Ժ������л�ȡ���ľ����������Ĵ������ִ��Ŀ�귽����ʱ�򣬴������ͻ�ִ��֪ͨ����������
	
	
	3��Ŀ�귽��ִ�У�
		�����б���������Ĵ������cglib��ǿ��Ķ���,����������汣������ϸ��Ϣ��������ǿ����Ŀ�����
		1�� CglibAopProxy.intercept();����Ŀ�귽����ִ��
		2�� ����ProxyFactory�����ȡ��Ҫִ�е�Ŀ�귽������������
			List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
			1) List<Object> interceptorList ��������������5
				һ��Ĭ�ϵ�ExposeInvocationInterceptor��4����ǿ��
			2���������е���ǿ��������ת��ΪInterceptor��
					registry.getInterceptors(advisor);
			3������ǿ��ת��ΪList<MethodInterceptor>��
				�����MethodInterceptorֱ�Ӽ��뵽������
				������ǣ�ʹ��AdvisorAdapter����ǿ��תΪMethodInterceptor
				ת�����MethodInterceptor����
				
		3�� ���û������������ֱ��ִ��Ŀ�귽��
				����������ÿһ��֪ͨ�����ֱ���װΪ����������������MethodInterceptor���ƣ�
		4�� �������������������Ҫִ�е�Ŀ�����Ŀ�귽����������������Ϣ���봴��һ��CglibMethodInvocation����
			������Object returnVak = mi.proceed();
		5�� ���������Ĵ�������
			1�� ���û��������ֱ��ִ��Ŀ�귽��������������������������������-1��Сһ����ָ���������һ����������ִ��Ŀ�귽��
			2����ʽ��ȡÿһ����������������ִ��invoke������ÿһ�����������ǵȴ���һ��������ִ����ɷ����Ժ�����ִ��
				���������Ļ��ƣ���֤֪ͨ������Ŀ�귽����ִ��˳��
	�ܽ᣺
		1��@EnableAspectJAutoProxy ����AOP�Ĺ���
		2��@EnableAspectJAutoProxy ���������ע��AnnotationAwareAspectJAutoProxyCreator
		3��AnnotationAwareAspectJAutoProxyCreator ��һ�����ô�����
		4�������Ĵ������̣�
			1��registerBeanPostProcessors(beanFactory);ע����ô�����������AnnotationAwareAspectJAutoProxyCreator
			2��finishBeanFactoryInitialization()��ʼ��ʣ�µĵ�ʵ��bean
				1������ҵ���߼�������������
				2��AnnotationAwareAspectJAutoProxyCreator��������Ĵ�������
				3�������������֮���ж�����Ƿ���Ҫ��ǿ��
					�ǣ������֪ͨ��������װ����ǿ����Advisor��;��ҵ���߼��������һ���������cglib��
					
		5��ִ��Ŀ�귽����
			1)�������ִ��Ŀ�귽��
			2)CglibAopProxy.intercept()
				1)�õ�Ŀ�귽��������������ǿ����װ��������MethodInterceptor��
				2)��������������ʽ���ƣ����ν���ÿ������������ִ��
				3��Ч����
					����ִ�У�ǰ��֪ͨ --> Ŀ��֪ͨ --> ����֪ͨ --> ����֪ͨ
					�����쳣��ǰ��֪ͨ --> Ŀ��֪ͨ --> ����֪ͨ --> �쳣֪ͨ

 */
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAop {
	
	//ҵ���߼������������
	@Bean
	public MathCalculator calculator() {
		return new MathCalculator();
	}
	
	//��������뵽������
	@Bean
	public LogAspects logAspects() {
		return new LogAspects();
	}

}


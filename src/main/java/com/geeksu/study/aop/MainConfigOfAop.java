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
 * AOP【动态代理】:
 * 		指在程序运行期间动态的将某段代码切入到指定方法位置进行运行的编程模式
 *
 * 1.导入aop模块，String AOP:(spring-aspects)
 * 2.定义一个业务逻辑类：MathCalculator,在业务逻辑运行的时候将日志进行打印(方法之前，方法运行结束，方法出现异常)
 * 3.定义一个日志切面类（LogAspects）:切面类里面的方法需要动态感知MathCalculator.div运行到哪里
 * 		通知方法：
 * 			前置通知(@Before)：logStart：在目标方法运行之前运行
 * 			后置通知(@After)：logEnd:在目标方法运行之后运行
 * 			返回通知(@AfterReturning)：logReturn：在目标方法正常返回之后运行
 * 			异常通知(@AfterThrowing)：logException：在目标方法运行出现异常以后运行
 * 			环绕通知(@Around): 动态代理，手动推进目标方法运行（joinPoint.proceed()）
 * 4.给切面类的目标方法标注何时何地运行（通知注解）
 * 5.将切面类和业务逻辑类（目标方法所在类）都加入到容器中
 * 6.必须告诉Spring哪个类是切面类（给切面类上加一个注解）
 * 7.给配置类中加@EnableAspectJAutoProxy【开启基于注解的AOP模式】
 * 		在Spring中很多的@EnableXxx功能
 * 
 *  3步：
 *  	1）将业务逻辑组件和切面类都加入到容器中，告诉spring哪个是切面类（@Aspect）
 *      2) 在切面类上的每个一方法上标注注解，告诉spring何时何地运行（切入点表达式）
 *      3）开启基于注解的aop模式：@EnableAspectJAutoProxy
 * 
 * AOP原理：【看给容器中注册了什么组件，这个组件什么时候工作，这个组件工作的功能是什么?】
 * 1.@EnableAspectJAutoProxy是什么？
 * 		@Import(AspectJAutoProxyRegistrar.class):给容器中倒入AspectJAutoProxyRegistrar
 * 			利用AspectJAutoProxyRegister自定义容器中注册bean:BeanDefinietion
 * 			internalAutoProxyCreator = AnnotationAwareAspectJAutoProxyCreator
 *   	
 *   	给容器中注册一个AnnotationAwareAspectJAutoProxyCreator:
 *  
 * 2.AnnotationAwareAspectJAutoProxyCreator
 * 		AnnotationAwareAspectJAutoProxyCreator 
 * 			-> AspectJAwareAdvisorAutoProxyCreator
 * 				-> AbstractAdvisorAutoProxyCreator
 * 					-> AbstractAutoProxyCreator
 * 						implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 * 						关注后置处理器（再Bean初始化完成前后做的事情），自动装配BeanFactory
 * 
 * AbstractAutoProxyCreator.setBeanFactory()
 * AbstractAutoProxyCreator 有后置处理器的逻辑；
 * 
 * AbstractAdvisorAutoProxyCreator.setBeanFactory() -> initBeanFactory()
 * AspectJAwareAdvisorAutoProxyCreator.
 * 
 * AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()
 * 
 * 流程：
 * 	1.传入配置类创建IOC容器
 *  2.注册配置类，调用refresh() 刷新容器
 *  3.registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建
 *  	1）. 先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor
 *  		String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);
 *   	2)  给容器中加别的BeanPostProcessor
 *   	3）优先注册实现了PriorityOrdered接口的BeanPostProcessor
 *   	4) 再给容器中注册实现了Ordered接口的BeanPostProcessor 
 *   	5） 注册没实现优先级接口的BeanPostProcessor
 *   	6) 注册BeanPostProcessor，实际上就是创建BeanPostProcessor对象，保存再容器中
 *   	创建internalAutoProxyCreator的BeanPostProcessor【AnnotationAwareAspectJAutoProxyCreator】
 *         1）创建Bean的实例
 *         2）populateBean(beanName,mbd,instanceWrapper):给Bean的各种属性赋值
 *         3）initializeBean:初始化Bean
 *         		1) invokeAwareMethods():处理Aware接口的方法回调
 *         		2） applyBeanPostProcessorsBeforeInitialization，应用后置处理器的BeforeInitialization.postProcessBeforeInitialization
 *         		3) invokeInitMethods():执行自定义的初始化方法
 *         		4) applyBeanPostProcessorsAfterInitialization():执行后置处理器的postProcessAfterInitialization
 *         4）BeanBostProcessor(AnnotationAwareAspectJAutoProxyCreator):创建成功 -- aspectJAdvisorBuilder
 *       7) 把BeanPostProcessor注册到BeanFactory中：
 *       	beanFactory.addBeanPostProcessor(postProcessor)
 *===== 以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程
 *		AnnotationAwareAspectJAutoProxyCreator => InstantiationAwareBeanPostProcessor
 *		4) finishBeanFactoryInitialization(beanFactory);完成BeanFactory初始化工作，创建剩下的单实例Bean
 *			1)遍历获取容器中的所有的Bean,依次创建对象getBean(beanName);
 *			2)getBean(beanName) -> doGetBean() -> getSingleton()
 *			3)创建Bean
 *				【AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会有一个拦截，InstantiationAwareBeanPostProcessor会调用postProcessBeforeInstantiation】
 *				1) 先从缓存中获取当前Bean,如果能获取到，说明Bean是之前已经被创建过的，直接使用，否则再创建
 *				  只要创建好的Bean都会被缓存起来
 *				2）createBean()。创建bean AnnotationAwareAspectJAutoProxyCreator会在任何Bean创建之前先尝试返回Bean的实例
 *					【BeanPostProcessor是在Bean对象创建完成初始化前后调用的】
 *					【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象】
 *					1) resolveBeforeInstantiation(beanName,mbdToUse);解析BeforeInstantiation
 *						希望后置处理器在此能返回一个代理对象；如果能返回代理对象就使用，如果不能就继续
 *						1） 后置处理器先尝试返回对象
 *						bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 *							拿到所有后置处理器，如果是 InstantiationAwareBeanPostProcessor
 *							就执行postProcessBeforeInstantiation
						if (bean != null) {
							bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
						}
 *					2） doCreateBean(beanName,mbdToUse,args);真正去创建一个bean实例，和流程3.6流程一样
 *					3）
 *	AnnotationAwareAspectJAutoProxyCreator【AnnotationAwareAspectJAutoProxyCreator】的作用：
 *	1） 每个Bean创建之前，调用postProcessBeforeInstantiation()
 *		关心MathCalculator和LogAspect的创建
 *		1） 判断当前Bean是否在adviseBeans中（保存了锁需要的增强的bean）
 *		2)   判断当前bean是否是基础类型的
 *			boolean retVal = Advice.class.isAssignableFrom(beanClass) ||
				Pointcut.class.isAssignableFrom(beanClass) ||
				Advisor.class.isAssignableFrom(beanClass) ||
				AopInfrastructureBean.class.isAssignableFrom(beanClass)
		   或者是否是切面（@Aspect）
		3) 是否需要跳过
			1）获取候选的增强器（切面中通知方法）【List<Advisor> candidateAdvisors】
			      每个封装的通知方法的增强器是InstantiationModelAwarePointcutAdvisor
		                判断每一个增强器是否是 AspectJPointcutAdvisor 类型的
		    2） 永远返回false
	2)创建对象
	postProcessAfterInitialization
		return wrapIfNecessary(bean,beanName,cacheKey);包装如果需要的话
		1） 获取当前bean的所有增强器（通知）
 */
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAop {
	
	//业务逻辑类加入容器中
	@Bean
	public MathCalculator calculator() {
		return new MathCalculator();
	}
	
	//切面类加入到容器中
	@Bean
	public LogAspects logAspects() {
		return new LogAspects();
	}

}


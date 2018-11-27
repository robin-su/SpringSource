package com.geeksu.study.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 告诉Spring这个类是个切面类
 */
@Aspect
public class LogAspects {
	
	//抽取公共的切入点表达式
	//1.如果在本类引用
	@Pointcut("execution(public int com.geeksu.study.aop.MathCalculator.*(..))")
	public void pointCut() {};
	
	@Before("pointCut()")
	public void logStart(JoinPoint joinPoint) {
		System.out.println(joinPoint.getSignature().getName() + "运行。。。参数列表，" + Arrays.asList(joinPoint.getArgs()));
	}
	
	@After("pointCut()")
	public void logEnd(JoinPoint joinPoint) {
		System.out.println(joinPoint.getSignature().getName() + "结束。。。");
	}
	
	//拿到返回值
	@AfterReturning(value="pointCut()",returning="result")
	public void logReturn(JoinPoint joinPoint,Object result) {
		System.out.println(joinPoint.getSignature().getName() + "结束。。。运行结果：{"+ result +"}");
	}
	
	@AfterThrowing(value="pointCut()",throwing="exception")
	public void logException(JoinPoint joinPoint,Exception exception) {
		
		System.out.println(joinPoint.getSignature().getName() + "异常信息。。。。。：{"+  exception+"}");
	}
}

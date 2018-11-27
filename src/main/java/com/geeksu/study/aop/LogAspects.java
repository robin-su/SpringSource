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
 * ����Spring������Ǹ�������
 */
@Aspect
public class LogAspects {
	
	//��ȡ�������������ʽ
	//1.����ڱ�������
	@Pointcut("execution(public int com.geeksu.study.aop.MathCalculator.*(..))")
	public void pointCut() {};
	
	@Before("pointCut()")
	public void logStart(JoinPoint joinPoint) {
		System.out.println(joinPoint.getSignature().getName() + "���С����������б�" + Arrays.asList(joinPoint.getArgs()));
	}
	
	@After("pointCut()")
	public void logEnd(JoinPoint joinPoint) {
		System.out.println(joinPoint.getSignature().getName() + "����������");
	}
	
	//�õ�����ֵ
	@AfterReturning(value="pointCut()",returning="result")
	public void logReturn(JoinPoint joinPoint,Object result) {
		System.out.println(joinPoint.getSignature().getName() + "�������������н����{"+ result +"}");
	}
	
	@AfterThrowing(value="pointCut()",throwing="exception")
	public void logException(JoinPoint joinPoint,Exception exception) {
		
		System.out.println(joinPoint.getSignature().getName() + "�쳣��Ϣ������������{"+  exception+"}");
	}
}

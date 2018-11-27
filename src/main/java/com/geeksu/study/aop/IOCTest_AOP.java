package com.geeksu.study.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_AOP {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MainConfigOfAop.class);
		
		MathCalculator bean = annotationConfigApplicationContext.getBean(MathCalculator.class);
		bean.div(1, 0);
		
		annotationConfigApplicationContext.close();
		
	}
	
}

package com.geeksu.study.ext;

import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Ext {
	
	@Test
	public void test01() {
		AnnotationConfigApplicationContext context 
			= new AnnotationConfigApplicationContext(ExtConfig.class);
		
		//发布了事件
		context.publishEvent(new ApplicationEvent(new String("我发布的事件")) {
		});	
		
		context.close();
	}

}

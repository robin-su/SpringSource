package com.geeksu.study.ext;

import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Ext {
	
	@Test
	public void test01() {
		AnnotationConfigApplicationContext context 
			= new AnnotationConfigApplicationContext(ExtConfig.class);
		
		//�������¼�
		context.publishEvent(new ApplicationEvent(new String("�ҷ������¼�")) {
		});	
		
		context.close();
	}

}

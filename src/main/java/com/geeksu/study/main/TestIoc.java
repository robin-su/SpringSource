package com.geeksu.study.main;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.geeksu.study.config.MainConifgOfLifeCycle;

public class TestIoc {
	
	@Test
	public void test01() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConifgOfLifeCycle.class);
		System.out.println("����������ɡ�����");
		applicationContext.close();
	}

}

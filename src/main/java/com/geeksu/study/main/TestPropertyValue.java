package com.geeksu.study.main;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.geeksu.study.bean.Person;
import com.geeksu.study.config.MainConfigOfPropertyValues;

public class TestPropertyValue {
	
	AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);
	
	@Test
	public void test1() {
		printBeans(applicationContext);
		System.out.println("===================");
		Person person = (Person)applicationContext.getBean("person");
		System.out.println(person);
		
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		String property = environment.getProperty("person.nickName");
		System.out.println(property);
		applicationContext.close();
	}
	
	private void printBeans(AnnotationConfigApplicationContext applicationContext) {
		String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
		for(String name:beanDefinitionNames) {
			System.out.println(name);
		}
		
	}
	
}

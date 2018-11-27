package com.geeksu.study.beanFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.ResolvableType;

//创建一个spring定义FactoryBean
public class ColorFactroyBean implements BeanFactory {

	@Override
	public Object getBean(String name) throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getBean(Class<T> requiredType) throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> ObjectProvider<T> getBeanProvider(Class<T> requiredType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> ObjectProvider<T> getBeanProvider(ResolvableType requiredType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsBean(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAliases(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}

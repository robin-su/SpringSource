package com.geeksu.study.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

	//�������з������¼�����������
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		System.out.println("�յ��¼���" + event);
		
	}


}

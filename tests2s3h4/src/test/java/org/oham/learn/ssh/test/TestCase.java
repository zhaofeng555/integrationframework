package org.oham.learn.ssh.test;

import org.junit.Test;
import org.oham.learn.ssh.controllers.TestStrutsAnnotationAction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCase {
	@Test
	public void testCAll() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"spring/applicationContext.xml");
		
		TestStrutsAnnotationAction bean = (TestStrutsAnnotationAction) context
				.getBean("testStrutsAnnotationAction");
		bean.testJ();
	}
}

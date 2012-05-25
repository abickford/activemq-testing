package com.softrek.activemq;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Broker {

	private static final Logger logger = LoggerFactory.getLogger(Broker.class);

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
		System.out.println("Press any key to stop broker");
		try {
			System.in.read();
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
}

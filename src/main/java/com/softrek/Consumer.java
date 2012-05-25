package com.softrek;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

	private ConnectionFactory factory;
	private Connection connection;
	private Session session;

	public Consumer() {
		try {
			initClient();
			Destination destination = this.session.createQueue("STUFF");
			MessageConsumer messageConsumer = this.session.createConsumer(destination);
			messageConsumer.setMessageListener(this);
		} catch (JMSException ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	private void initClient() throws JMSException {
		this.factory = new ActiveMQConnectionFactory();
		connection = factory.createConnection("admin", "secret");
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public static void main(String[] args) {
		new Consumer();
	}

	@Override
	public void onMessage(Message msg) {
		try {
			System.out.println(((ObjectMessage) msg).getObject());
		} catch (JMSException ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

}

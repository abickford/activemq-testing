package com.softrek.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer {

	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	
	private String brokerURL = "tcp://192.168.13.50:61616";
	private ConnectionFactory factory;
	private Connection connection;
	private Session session;
	private MessageProducer producer;

	public Producer() throws JMSException {
		factory = new ActiveMQConnectionFactory(brokerURL);
		connection = factory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		producer = session.createProducer(null);
		producer.setTimeToLive(30000);
	}

	public static void main(String[] args) throws JMSException {
		Producer producer = new Producer();
		producer.sendMessage();
		producer.close();
	}

	public void sendMessage() throws JMSException {
		Destination destination = session.createQueue("Major_Gifts");
		logger.info("Sending message 'Hello there'");
		Message message = session.createTextMessage("Hello there!");
		producer.send(destination, message);
	}

	public void close() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}

}

package com.softrek;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

	private String brokerURL = "tcp://localhost:61616";
	private ConnectionFactory factory;
	private Connection connection;
	private Session session;
	private MessageProducer producer;

	public Producer() throws JMSException {
		factory = new ActiveMQConnectionFactory(brokerURL);
		connection = factory.createConnection("admin", "secret");
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		producer = session.createProducer(null);
		producer.setTimeToLive(10000);
	}

	public static void main(String[] args) throws JMSException {
		Producer producer = new Producer();
		producer.sendMessage();
		producer.close();
	}

	public void sendMessage() throws JMSException {
		Destination destination = session.createQueue("STUFF");
		System.out.println("Sending message 'Hello there'");
		Message message = session.createObjectMessage("Hello there");
		producer.send(destination, message);
	}

	public void close() throws JMSException {
		if (connection != null) {
			connection.close();
		}
	}

}

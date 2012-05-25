package com.softrek;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.security.AuthenticationUser;
import org.apache.activemq.security.SimpleAuthenticationPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Broker {

	private static final Logger logger = LoggerFactory.getLogger(Broker.class);

	public static void main(String[] args) {
		BrokerService broker = new BrokerService();
		broker.setBrokerName("myBroker");
		broker.setDataDirectory("data/");
		SimpleAuthenticationPlugin authPlugin = new SimpleAuthenticationPlugin();
		List<AuthenticationUser> users = new ArrayList<AuthenticationUser>();
		users.add(new AuthenticationUser("admin", "secret",
				"admins,publishers,consumers"));
		authPlugin.setUsers(users);
		broker.setPlugins(new BrokerPlugin[] { authPlugin });
		try {
			broker.addConnector("tcp://localhost:61616");
			broker.start();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		System.out.println();
		System.out.println("Press any key to stop the broker");
		System.out.println();

		try {
			System.in.read();
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
}

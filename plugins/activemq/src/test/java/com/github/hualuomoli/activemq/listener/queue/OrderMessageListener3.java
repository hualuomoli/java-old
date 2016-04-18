package com.github.hualuomoli.activemq.listener.queue;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.mq.listener.MessageListener;
import com.github.hualuomoli.activemq.entity.Order;

public class OrderMessageListener3 implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(OrderMessageListener3.class);

	@Override
	public boolean onMessage(Serializable serializable) {
		Order order = (Order) serializable;
		logger.info("receive message {}", order.getId());
		// reload spring this class's hashCode no update
		logger.info("receive message {}", this.hashCode());
		return true;
	}

	@Override
	public String getDestinationName() {
		return "order";
	}

}
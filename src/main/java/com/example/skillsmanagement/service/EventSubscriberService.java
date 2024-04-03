package com.example.skillsmanagement.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public interface EventSubscriberService extends MessageListener {

    public void onMessage(Message message, byte[] pattern);
}

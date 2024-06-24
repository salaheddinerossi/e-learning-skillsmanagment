package com.example.skillsmanagement.config;

import com.example.skillsmanagement.service.EventSubscriberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisMessageListenerConfig {

    private final RedisConnectionFactory connectionFactory;

    public RedisMessageListenerConfig(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    RedisMessageListenerContainer container(EventSubscriberService eventSubscriberService) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(eventSubscriberService, new ChannelTopic("studentSkillTopic"));
        return container;
    }

}

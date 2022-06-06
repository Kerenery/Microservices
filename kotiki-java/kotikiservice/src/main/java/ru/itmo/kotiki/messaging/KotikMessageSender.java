package ru.itmo.kotiki.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class KotikMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageConverter messageConverter;

    @PostConstruct
    private void initRabbit() {
        rabbitTemplate.setMessageConverter(messageConverter);
    }

    public void sendMessage(String queueName, Object event){
        rabbitTemplate.convertAndSend(queueName, event);
    }
}

package ru.itmo.kotiki.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.Arrays;
import java.util.List;

import static ru.itmo.kotiki.messaging.MQQueues.*;

@Configuration
@EnableRabbit
public class OwnerMessageConfig implements RabbitListenerConfigurer {
    @Autowired
    public ConnectionFactory connectionFactory;

    @Bean
    public FanoutExchange fanoutExchange () {
        return new FanoutExchange(FANOUT_EXCHANGE.getQueueName());
    }

    @Bean
    public Queue queueKotikiService(){
        return new Queue(KOTIKI_SERVICE.getQueueName());
    }

    @Bean
    public Queue queueOwnerService(){
        return new Queue(OWNER_SERVICE.getQueueName());
    }

    @Bean
    public Queue ownerAllQueue(){
        return new Queue(OWNER_ALL.getQueueName());
    }

    @Bean
    public Queue ownerGetQueue(){
        return new Queue(OWNER_ID.getQueueName());
    }

    @Bean
    public Queue ownerEditQueue(){
        return new Queue(OWNER_EDIT.getQueueName());
    }

    @Bean
    public Queue ownerDeleteQueue(){
        return new Queue(OWNER_DELETE.getQueueName());
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter(objectMapper());
    }

    @Bean
    public ObjectMapper objectMapper(){
        var objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }

    @Bean
    public List<Binding> binding(){
        return Arrays.asList(
                BindingBuilder.bind(queueKotikiService()).to(fanoutExchange()),
                BindingBuilder.bind(queueOwnerService()).to(fanoutExchange())
        );
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }

    @Bean
    public OwnerMessageHandler eventResultHandler() {
        return new OwnerMessageHandler();
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
    }

    @Bean
    public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(mappingJackson2MessageConverter());
        return factory;
    }

    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
        MappingJackson2MessageConverter jacksonMessageConverter = new MappingJackson2MessageConverter();
        jacksonMessageConverter.setObjectMapper(objectMapper());
        jacksonMessageConverter.setSerializedPayloadClass(String.class);
        jacksonMessageConverter.setStrictContentTypeMatch(true);
        return jacksonMessageConverter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}

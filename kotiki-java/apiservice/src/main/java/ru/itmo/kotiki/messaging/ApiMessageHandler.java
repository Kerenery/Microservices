package ru.itmo.kotiki.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.itmo.kotiki.dto.OwnerDto;

// could be used to get notifications mb
@Component
public class ApiMessageHandler {
}

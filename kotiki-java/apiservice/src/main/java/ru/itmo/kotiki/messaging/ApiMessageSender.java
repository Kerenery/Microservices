package ru.itmo.kotiki.messaging;

import com.google.common.primitives.Bytes;
import org.apache.catalina.LifecycleState;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.ArrayUtils;
import ru.itmo.kotiki.dto.KotikDto;
import ru.itmo.kotiki.dto.KotikiFriendsPayload;
import ru.itmo.kotiki.dto.OwnerDto;
import ru.itmo.kotiki.entity.Kotik;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class ApiMessageSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageConverter messageConverter;

    @PostConstruct
    private void initRabbit() {
        rabbitTemplate.setMessageConverter(messageConverter);
    }

    public void sendMessage(String queueName, Object event) {
        rabbitTemplate.convertAndSend(queueName, event);
    }

    public List<OwnerDto> sendMessageToGetOwners(String queueName, Object event){
        byte[] response = rabbitTemplate.convertSendAndReceiveAsType(queueName, event,
                new ParameterizedTypeReference<>() {
                            });

        return ((List<OwnerDto>) SerializationUtils.deserialize(response));
    }

    public OwnerDto sendMessageToGetOwner(String queueName, Long ownerId){
        byte[] response = rabbitTemplate.convertSendAndReceiveAsType(queueName, ownerId,
                new ParameterizedTypeReference<>() {
                });

        return ((OwnerDto) SerializationUtils.deserialize(response));
    }

    public void sendMessageToEditOwner(String queueName, OwnerDto ownerDto){
        rabbitTemplate.convertAndSend(queueName, ownerDto);
    }

    public void sendMessageToDeleteOwner(String queueName, Long ownerId){
        rabbitTemplate.convertAndSend(queueName, ownerId);
    }

    public List<KotikDto> sendMessageToGetKotiki(String queueName, Long userId){
        byte[] response = rabbitTemplate.convertSendAndReceiveAsType(queueName, userId,
                new ParameterizedTypeReference<>() {
                });

        return ((List<KotikDto>) SerializationUtils.deserialize(response));
    }

    public KotikDto sendMessageToGetKotik(String queueName, Long kotikId){
        byte[] response = rabbitTemplate.convertSendAndReceiveAsType(queueName, kotikId,
                new ParameterizedTypeReference<>() {
                });

        return ((KotikDto) SerializationUtils.deserialize(response));
    }

    public void sendMessageToEditKotik(String queueName, KotikDto kotikDto){
        rabbitTemplate.convertAndSend(queueName, kotikDto);
    }

    public void sendMessageToDeleteKotik(String queueName, Long kotikId){
        rabbitTemplate.convertAndSend(queueName, kotikId);
    }

    public void sendMessageToAddFriend(String queueName, Long firstKotikId, Long secondKotikId){
        rabbitTemplate.convertAndSend(queueName, new KotikiFriendsPayload(firstKotikId, secondKotikId));
    }
}

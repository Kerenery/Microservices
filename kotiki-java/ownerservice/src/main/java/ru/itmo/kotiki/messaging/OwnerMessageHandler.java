package ru.itmo.kotiki.messaging;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.itmo.kotiki.dto.KotikDto;
import ru.itmo.kotiki.dto.OwnerDto;
import ru.itmo.kotiki.entity.Owner;
import ru.itmo.kotiki.service.OwnerService;
import ru.itmo.kotiki.tool.ServiceException;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class OwnerMessageHandler {
    @Autowired
    private OwnerService ownerService;


    @RabbitListener(queues = MQQueues.Constants.OWNER_SERVICE_VALUE)
    public void handleAddOwnerRequest(@Payload OwnerDto ownerDto){
        ownerService.add(ownerDto);
    }

    @RabbitListener(queues = MQQueues.Constants.OWNER_ALL)
    public List<OwnerDto> handleAllOwnersRequest(String event){
        return ownerService.getAll();
    }

    @RabbitListener(queues = MQQueues.Constants.OWNER_ID)
    public OwnerDto handleGetOwnerRequest(Long id){
        try {
            return ownerService.getById(id);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = MQQueues.Constants.OWNER_EDIT)
    public void handleOwnerEditRequest(@Payload OwnerDto ownerDto){
        try {
            ownerService.update(ownerDto);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = MQQueues.Constants.OWNER_DELETE)
    public void handleDeleteOwnerRequest(Long id){
        try {
            ownerService.remove(id);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}

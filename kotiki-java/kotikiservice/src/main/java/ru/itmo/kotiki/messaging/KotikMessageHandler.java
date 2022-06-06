package ru.itmo.kotiki.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.itmo.kotiki.dto.KotikDto;
import ru.itmo.kotiki.entity.Kotik;
import ru.itmo.kotiki.service.KotikService;
import ru.itmo.kotiki.tool.ServiceException;

import java.util.List;

import static ru.itmo.kotiki.messaging.MQQueues.*;

@Component
public class KotikMessageHandler {

    @Autowired
    private KotikService kotikService;

    @RabbitListener(queues = Constants.KOTIK_SERVICE_VALUE)
    public void handleAddKotikRequest(@Payload KotikDto kotikDto){
        kotikService.add(kotikDto);
    }

    @RabbitListener(queues = Constants.KOTIKI_ALL)
    public List<KotikDto> handleAllKotikiRequest(Long userId){
        return kotikService.getAll(userId);
    }

    @RabbitListener(queues = Constants.KOTIK_ID)
    public KotikDto handleKotikIdRequest(Long kotikId){
        try {
            return kotikService.getById(kotikId);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = Constants.KOTIK_EDIT)
    public void handleKotikEditRequest(@Payload KotikDto kotikDto){
        try {
            kotikService.update(kotikDto);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = Constants.KOTIK_DELETE)
    public void handlerKotikDeleteRequest(Long kotikId){
        try {
            kotikService.remove(kotikId);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}

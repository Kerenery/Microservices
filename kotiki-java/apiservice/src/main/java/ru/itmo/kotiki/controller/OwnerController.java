package ru.itmo.kotiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiki.dto.OwnerDto;
import ru.itmo.kotiki.messaging.ApiMessageSender;

import static ru.itmo.kotiki.messaging.MQQueues.*;

@RestController
@RequestMapping(path = "owner")
public class OwnerController {

    private final ApiMessageSender messageSender;

    @Autowired
    public OwnerController(ApiMessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(){
        var owners = messageSender.sendMessageToGetOwners(OWNER_ALL.getQueueName(), "all");
        if (owners.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(owners, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getOwnerById(@PathVariable long id){
        var ownerDto = messageSender.sendMessageToGetOwner(OWNER_ID.getQueueName(), id);
        return ownerDto == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(ownerDto, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/edit/{id}")
    public ResponseEntity<?> updateOwnerById(@RequestBody OwnerDto owner){
        messageSender.sendMessageToEditOwner(OWNER_EDIT.getQueueName(), owner);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewOwner(@RequestBody OwnerDto ownerDto){
        messageSender.sendMessage(OWNER_SERVICE.getQueueName(), ownerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "delete/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable long id){
        messageSender.sendMessageToDeleteOwner(OWNER_DELETE.getQueueName(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

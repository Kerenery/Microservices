package ru.itmo.kotiki.controller;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiki.dto.KotikDto;
import ru.itmo.kotiki.entity.User;
import ru.itmo.kotiki.enums.KotikColor;
import ru.itmo.kotiki.messaging.ApiMessageSender;
import ru.itmo.kotiki.tool.ServiceException;


import static ru.itmo.kotiki.messaging.MQQueues.*;

@RestController
@RequestMapping(path = "kotik")
public class KotikController {

    private ApiMessageSender apiMessageSender;

    @Autowired
    public KotikController(ApiMessageSender apiMessageSender) {
        this.apiMessageSender = apiMessageSender;
    }

    @PreAuthorize("hasAnyAuthority('OWNER', 'ADMIN')")
    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll(Authentication authentication) {

        var principal = authentication.getPrincipal();
        Long userId;

        if (principal instanceof User) {
            userId = ((User) principal).getId();
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        var kotiki = apiMessageSender.sendMessageToGetKotiki(KOTIKI_ALL.getQueueName(),userId);
        if (kotiki.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(kotiki, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getKotikById(@PathVariable long id){
        var kotikDto = apiMessageSender.sendMessageToGetKotik(KOTIK_ID.getQueueName(), id);
        return new ResponseEntity<>(kotikDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/edit/{id}")
    public ResponseEntity<?> updateKoitkById(@PathVariable long id,
                                             @RequestBody KotikDto kotikDto){
        apiMessageSender.sendMessageToEditKotik(KOTIK_EDIT.getQueueName(), kotikDto);
        return new ResponseEntity<>(kotikDto, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewKotik(@RequestBody KotikDto kotikDto){
        apiMessageSender.sendMessageToEditKotik(KOTIKI_SERVICE.getQueueName(), kotikDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteKotik(@PathVariable long id){
        apiMessageSender.sendMessageToDeleteKotik(KOTIK_DELETE.getQueueName(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping(path = "addFriend/{firstKotikId}/{secondKotikId}")
    public ResponseEntity<?> addFriend(@PathVariable long firstKotikId,
                                       @PathVariable long secondKotikId){
        apiMessageSender
                .sendMessageToAddFriend(KOTIKI_FRIENDS.getQueueName(),
                firstKotikId,
                secondKotikId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
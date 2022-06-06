package ru.itmo.kotiki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiki.dto.RegistrationPayload;
import ru.itmo.kotiki.enums.UserRole;
import ru.itmo.kotiki.service.UserService;
import ru.itmo.kotiki.service.impl.UserServiceImpl;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAuthority;

@RestController
@RequestMapping(path = "user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("all")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> addNewUser(@RequestBody RegistrationPayload registrationPayload) {
        return new ResponseEntity<>(userService.addUser(registrationPayload),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("dress/{id}/{role}")
    public ResponseEntity<?> addRoleToUser(@PathVariable Long id,
                                           @PathVariable UserRole role){
        userService.updateRole(id, role);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
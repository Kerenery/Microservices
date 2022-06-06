package ru.itmo.kotiki.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.kotiki.dto.RegistrationPayload;
import ru.itmo.kotiki.dto.UserDto;
import ru.itmo.kotiki.dto.UserDtoBuilder;
import ru.itmo.kotiki.entity.User;
import ru.itmo.kotiki.entity.UserBuilder;
import ru.itmo.kotiki.enums.UserRole;
import ru.itmo.kotiki.repository.UserRepository;
import ru.itmo.kotiki.service.UserService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null){
            throw new UsernameNotFoundException(String.format("User with %s not found", username));
        }

        return user;
    }

    @Override
    public UserDto addUser(RegistrationPayload registrationPayload) {
        var username = registrationPayload.getUsername();
        User user = userRepository.findByUsername(username);

        if (user != null) {
            throw new AuthenticationServiceException(String.format("User with %s already exists", username));
        }

        var userEnt = UserBuilder.anUser()
                .withUsername(registrationPayload.getUsername())
                .withPassword(passwordEncoder.encode(registrationPayload.getPassword()))
                .build();

        return mapToDto(userRepository.save(userEnt));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> mapToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id)
    {
        return mapToDto(userRepository.findById(id).get());
    }

    @Override
    public void updateUser(UserDto userDto) {
        var userEntityOpt = userRepository.findById(userDto.getId());

        if (userEntityOpt.isEmpty()){
            throw new UsernameNotFoundException("such owner does not exist");
        }

        var userEntity = userEntityOpt.get();
        userEntity = mapToEntity(userDto);
        userRepository.save(userEntity);
    }

    @Override
    public void removeUser(Long id) {
        var userEntityOpt = userRepository.findById(id);

        if (userEntityOpt.isEmpty()){
            throw new UsernameNotFoundException("such owner does not exist");
        }

        userRepository.deleteById(id);
    }

    @Override
    public void updateRole(Long userIdToBeUpdated, UserRole role) {
        var user = userRepository.getById(userIdToBeUpdated);
        user.setRole(role);
        userRepository.save(user);
    }

    private UserDto mapToDto(User user){
        return UserDtoBuilder.anUserDto()
                .withUsername(user.getUsername()).build();
    }

    private User mapToEntity(UserDto userDto){
        return UserBuilder.anUser()
                .withUsername(userDto.getUsername())
                .withPassword(userDto.getPassword())
                .build();
    }
}
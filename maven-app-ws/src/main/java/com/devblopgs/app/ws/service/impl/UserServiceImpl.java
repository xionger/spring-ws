package com.devblopgs.app.ws.service.impl;

import com.devblopgs.app.ws.io.entity.UserEntity;
import com.devblopgs.app.ws.io.repository.UserRepository;
import com.devblopgs.app.ws.service.UserService;
import com.devblopgs.app.ws.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto user) {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email record already exists!");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword("test");
        userEntity.setUserId("testUserId");

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, userDto);

        return userDto;
    }
}


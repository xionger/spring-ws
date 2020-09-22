package com.devblopgs.app.ws.service.impl;

import com.devblopgs.app.ws.exception.UserServiceException;
import com.devblopgs.app.ws.io.entity.UserEntity;
import com.devblopgs.app.ws.io.repository.UserRepository;
import com.devblopgs.app.ws.service.UserService;
import com.devblopgs.app.ws.shared.Utils;
import com.devblopgs.app.ws.shared.dto.UserDto;
import com.devblopgs.app.ws.ui.model.response.ErrorMessages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email record already exists!");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setUserId(utils.generateUserId(20));

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, userDto);

        return userDto;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Username: \"" + email + "\" not found!");
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User ID: \"" + userId + "\" not found!");
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity updatedEntity = userRepository.save(userEntity);

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(updatedEntity, userDto);

        return userDto;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }

        userRepository.delete(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Username: \"" + email + "\" not found!");
        }
        return new User(
                userEntity.getEmail(),
                userEntity.getEncryptedPassword(),
                new ArrayList<>()
        );
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();
        if (page > 0) {
            page--;
        }

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();

        for (UserEntity userEntity : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }
}


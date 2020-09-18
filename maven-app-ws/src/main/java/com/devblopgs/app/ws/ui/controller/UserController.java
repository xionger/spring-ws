package com.devblopgs.app.ws.ui.controller;

import com.devblopgs.app.ws.service.UserService;
import com.devblopgs.app.ws.shared.dto.UserDto;
import com.devblopgs.app.ws.ui.model.request.UserDetailsRequestModel;
import com.devblopgs.app.ws.ui.model.response.UserRest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getUser() {
        return "getUser is called";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createUser, returnValue);

        return returnValue;
    }

    @PutMapping
    public String updateUser() {
        return "updateUser is called";
    }

    @DeleteMapping
    public String deleteUser() {
        return "deleteUser is called";
    }
}
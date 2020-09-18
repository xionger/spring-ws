package com.devblopgs.app.ws.ui.controller;

import com.devblopgs.app.ws.ui.model.request.UserDetailsRequestModel;
import com.devblopgs.app.ws.ui.model.response.UserRest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @GetMapping
    public String getUser() {
        return "getUser is called";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {
        return null;
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
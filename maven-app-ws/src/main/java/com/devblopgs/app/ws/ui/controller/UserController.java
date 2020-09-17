package com.devblopgs.app.ws.ui.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @GetMapping
    public String getUser() {
        return "getUser is called";
    }

    @PostMapping
    public String setUser() {
        return "setUser is called";
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
package com.telecom.devicemanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class UserController {
    @GetMapping("/external-users")
    public Map fetchUsers() {
        RestTemplate rest = new RestTemplate();
        return rest.getForObject("https://reqres.in/api/users?page=2", Map.class);
    }
}

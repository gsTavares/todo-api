package com.api.todo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.todo.model.User;
import com.api.todo.model.util.Response;
import com.api.todo.service.UserService;

@RestController
@RequestMapping(value = "/api-todo", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserResource {

    @Autowired
    private UserService service;

    @PostMapping(value = "/user")
    public ResponseEntity<Response<User>> create(@RequestBody User user) {
        return service.create(user);
    }

    @GetMapping(value = "/login")
    public ResponseEntity<Response<Boolean>> login(
            @RequestParam(name = "username", required = true) String username,
            @RequestParam(name = "password", required = true) String password) {
                User user = new User(username, password);
                return service.login(user);
    }

}

package com.api.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.todo.model.User;
import com.api.todo.model.util.Response;
import com.api.todo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private void addReturnMessage(List<String> messages, String toBeAdd) {
        messages.add(toBeAdd);
    }

    public ResponseEntity<Response<User>> create(User user) {
        ResponseEntity<Response<User>> response;
        List<String> returnMessages = new ArrayList<>();

        try {

            if (user.getUsername() == null) {
                HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
                addReturnMessage(returnMessages, "username is required");
                response = new ResponseEntity<Response<User>>(new Response<User>(status, returnMessages), status);
            } else if (userRepository.existsByUsername(user.getUsername())) {
                HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
                addReturnMessage(returnMessages, "username is already used");
                response = new ResponseEntity<Response<User>>(new Response<User>(status, returnMessages), status);
            } else if (user.getPassword() == null) {
                HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
                addReturnMessage(returnMessages, "password is required");
                response = new ResponseEntity<Response<User>>(new Response<User>(status, returnMessages), status);
            } else {
                User result = userRepository.saveAndFlush(user);
                HttpStatus status = result == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
                addReturnMessage(returnMessages, result == null ? "error while saving user" : "user saved successful!");
                response = result == null
                        ? new ResponseEntity<Response<User>>(new Response<User>(status, returnMessages), status)
                        : new ResponseEntity<Response<User>>(new Response<User>(status, returnMessages), status);
            }

            return response;

        } catch (Exception e) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            addReturnMessage(returnMessages, e.getMessage());
            return new ResponseEntity<Response<User>>(new Response<User>(status, returnMessages), status);
        }

    }

    public ResponseEntity<Response<Boolean>> login(User user) {
        ResponseEntity<Response<Boolean>> response;
        List<String> returnMessages = new ArrayList<>();
        try {
            if (user.getUsername() == null || user.getPassword() == null) {
                HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
                addReturnMessage(returnMessages, "invalid username or password");
                response = new ResponseEntity<Response<Boolean>>(
                        new Response<Boolean>(status, Boolean.FALSE, returnMessages), status);
            } else {
                Optional<User> find = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
                HttpStatus status = find.isPresent() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
                addReturnMessage(returnMessages,
                        find.isPresent() ? "user logged successful!" : "invalid username or password");
                response = new ResponseEntity<Response<Boolean>>(
                        new Response<Boolean>(status, find.isPresent(), returnMessages), status);
            }

            return response;

        } catch (Exception e) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            addReturnMessage(returnMessages, e.getMessage());
            return new ResponseEntity<Response<Boolean>>(new Response<Boolean>(status, Boolean.FALSE, returnMessages),
                    status);
        }
    }

}

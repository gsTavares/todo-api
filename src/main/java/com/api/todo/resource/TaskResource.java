package com.api.todo.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.todo.model.Task;
import com.api.todo.model.util.Response;
import com.api.todo.service.TaskService;

@RestController
@RequestMapping(value = "/api-todo", produces = { MediaType.APPLICATION_JSON_VALUE })
public class TaskResource {

    @Autowired
    private TaskService service;

    @PostMapping(value = "/task")
    public ResponseEntity<Response<Task>> save(@RequestBody Task task) {
        return service.save(task);
    }

    @PutMapping(value = "/task")
    public ResponseEntity<Response<Task>> update(@RequestBody Task task) {
        return service.udpate(task);
    }

    @GetMapping(value = "/tasks")
    public ResponseEntity<Response<List<Task>>> listByUser(
            @RequestParam(name = "idUser", required = true) Long idUser,
            @RequestParam(name = "startDate", required = false) String startDate) {
        return service.listByUser(idUser, startDate);
    }

}

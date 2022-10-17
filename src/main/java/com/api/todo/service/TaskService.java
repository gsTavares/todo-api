package com.api.todo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.todo.model.Task;
import com.api.todo.model.util.Response;
import com.api.todo.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    private void addReturnMessage(List<String> messages, String toBeAdd) {
        messages.add(toBeAdd);
    }

    private <T> ResponseEntity<Response<T>> createResponse(List<String> messages, HttpStatus status) {
        return new ResponseEntity<Response<T>>(new Response<T>(status, messages), status);
    }

    private <T> ResponseEntity<Response<T>> createResponse(List<String> messages, HttpStatus status, T body) {
        return new ResponseEntity<Response<T>>(new Response<T>(status, body, messages), status);
    }

    public ResponseEntity<Response<Task>> save(Task task) {
        ResponseEntity<Response<Task>> response;
        List<String> returnMessages = new ArrayList<>();

        try {

            if (task.getDescription() == null) {
                HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
                addReturnMessage(returnMessages, "task description is required");
                response = createResponse(returnMessages, status);

            } else if (task.getUser() == null || task.getUser().getId() == null) {
                HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
                addReturnMessage(returnMessages, "task user is required");
                response = createResponse(returnMessages, status);
            } else {
                Task result = taskRepository.saveAndFlush(task);
                HttpStatus status = result == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
                addReturnMessage(returnMessages, result == null ? "error while saving task" : "task saved successful!");
                response = createResponse(returnMessages, status, result);
            }

            return response;

        } catch (Exception e) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            addReturnMessage(returnMessages, e.getMessage());
            return createResponse(returnMessages, status);
        }

    }

    public ResponseEntity<Response<Task>> udpate(Task task) {
        ResponseEntity<Response<Task>> response;
        List<String> returnMessages = new ArrayList<>();

        try {

            if (task.getId() == null) {
                HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
                addReturnMessage(returnMessages, "task id is required");
                response = createResponse(returnMessages, status);
            } else if (!taskRepository.existsById(task.getId())) {

                HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
                addReturnMessage(returnMessages, "task id is required");
                response = createResponse(returnMessages, status);

            } else if (task.getDescription() == null) {
                HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
                addReturnMessage(returnMessages, "task description is required");
                response = createResponse(returnMessages, status);

            } else if (task.getUser() == null || task.getUser().getId() == null) {
                HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
                addReturnMessage(returnMessages, "task user is required");
                response = createResponse(returnMessages, status);
            } else {
                Task result = taskRepository.saveAndFlush(task);
                HttpStatus status = result == null ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
                addReturnMessage(returnMessages, result == null ? "error while saving task" : "task saved successful!");
                response = createResponse(returnMessages, status, result);
            }

            return response;

        } catch (Exception e) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            addReturnMessage(returnMessages, e.getMessage());
            return createResponse(returnMessages, status);
        }

    }

    public ResponseEntity<Response<List<Task>>> listByUser(Long idUser, String startDate) {
        ResponseEntity<Response<List<Task>>> response;
        List<String> returnMessages = new ArrayList<>();

        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start = startDate == null ? sdf.parse(sdf.format(new Date()))  : sdf.parse(startDate);
            calendar.setTime(start);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date end = calendar.getTime();

            List<Task> result = taskRepository.findByUserIdAndCreationDateBetweenOrderByCreationDateAsc(idUser, start, end);

            HttpStatus status = result.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            addReturnMessage(returnMessages, result.isEmpty() ? "task list not found" : "task list returned successful!");
            response = createResponse(returnMessages, status, result);

            return response;
        } catch (Exception e) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            addReturnMessage(returnMessages, e.getMessage());
            return createResponse(returnMessages, status);
        }

    }

}

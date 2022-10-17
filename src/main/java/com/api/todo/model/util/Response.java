package com.api.todo.model.util;

import java.util.List;

import org.springframework.http.HttpStatus;

public class Response<T> {

    private HttpStatus status;
    private T body;
    private List<String> messages;

    public Response() {
    }

    public Response(HttpStatus status, List<String> messsages) {
        this.status = status;
        this.messages = messsages;
    }

    public Response(HttpStatus status, T body, List<String> messsages) {
        this.status = status;
        this.body = body;
        this.messages = messsages;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public List<String> getMMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
    
}

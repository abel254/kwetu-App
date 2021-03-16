package com.project.kwetu.model;

import java.util.Date;

public class ChatModel {
    private String sender;
    private String suggestion;
    private String senderId;
    private String recieverId;
    private String time;

    public ChatModel(){

    }

    public ChatModel(String sender, String suggestion, String senderId, String recieverId, String time) {
        this.sender = sender;
        this.suggestion = suggestion;
        this.senderId = senderId;
        this.recieverId = recieverId;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


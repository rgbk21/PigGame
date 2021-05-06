package com.rgbk21.utils;

public class ErrorInfo {

    private String messageText;
    private String code;

    public String getMessageText() {
        return messageText;
    }

    public ErrorInfo setMessageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ErrorInfo setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "messageText='" + messageText + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

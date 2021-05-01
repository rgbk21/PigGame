package com.rgbk21.model;

public class Hello {

    private String name;

    public Hello(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }

    public Hello setName(String name) {
        this.name = name;
        return this;
    }
}

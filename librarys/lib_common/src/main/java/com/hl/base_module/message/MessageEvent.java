package com.hl.base_module.message;

import com.hl.anotation.NotProguard;

@NotProguard
public class MessageEvent {
    private String name;
    private Object object;

    public MessageEvent(String name) {
        this.name = name;
    }

    public MessageEvent(String name,  Object object) {
        this.name = name;
        this.object = object;
    }

    public String getName() {
        return name;
    }

    public Object getObject() {
        return object;
    }
}

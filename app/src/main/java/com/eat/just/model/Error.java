package com.eat.just.model;

/**
 */

public class Error extends java.lang.Error {

    public Type type;

    public Error(Type type) {
        this.type = type;
    }

    public enum Type {
        Network,
        Empty,
        AccessDenied,
        Fetch
    }
}

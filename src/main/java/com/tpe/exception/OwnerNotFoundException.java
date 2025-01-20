package com.tpe.exception;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException(String s) {
        super(s);
    }
}
package com.thiennth.taskmanager.exception;

public class ForbiddenActionException extends RuntimeException {

    public ForbiddenActionException() {
        super("You are forbidden from making request to this resource");
    }

    public ForbiddenActionException(String message) {
        super(message);
    }
}

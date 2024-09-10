package org.example.todayeating_back.exception;

public class DuplicateNickNameException extends RuntimeException {
    public DuplicateNickNameException(String message) {
        super(message);
    }
}
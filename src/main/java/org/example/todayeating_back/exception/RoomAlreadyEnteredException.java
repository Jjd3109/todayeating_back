package org.example.todayeating_back.exception;

public class RoomAlreadyEnteredException extends RuntimeException {
    public RoomAlreadyEnteredException(String message) {
        super(message);
    }
}
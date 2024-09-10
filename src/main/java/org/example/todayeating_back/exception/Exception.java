package org.example.todayeating_back.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Exception extends RuntimeException {

    @ExceptionHandler(RoomAlreadyEnteredException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleRoomAlreadyEnteredException(RoomAlreadyEnteredException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DuplicateNickNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateNickNameException(DuplicateNickNameException ex) {
        return ex.getMessage();
    }
}

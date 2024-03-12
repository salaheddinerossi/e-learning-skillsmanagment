package com.example.skillsmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SkillNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handelSkillNotFoundException(SkillNotFoundException e) {
        return e.getMessage();
    }


}

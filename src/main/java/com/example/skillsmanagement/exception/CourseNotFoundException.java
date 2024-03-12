package com.example.skillsmanagement.exception;

public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(){
        super("course not found");
    }
}

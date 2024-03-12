package com.example.skillsmanagement.exception;


public class SkillNotFoundException extends RuntimeException{

    public SkillNotFoundException(){
        super("skill not found");
    }

}

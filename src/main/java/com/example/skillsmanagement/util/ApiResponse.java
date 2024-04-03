package com.example.skillsmanagement.util;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse<T> {

    private Boolean success;
    private String message;
    private T data;

}

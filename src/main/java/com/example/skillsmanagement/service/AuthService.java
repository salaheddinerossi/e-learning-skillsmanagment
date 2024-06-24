package com.example.skillsmanagement.service;


import com.example.skillsmanagement.dto.UserDetailsDto;

public interface AuthService {

    public UserDetailsDto getUserDetailsFromAuthService(String serviceUrl, String token);

    public Boolean isAdmin(String role);
    public Boolean isStudent(String role);


}

package com.example.skillsmanagement.controller;


import com.example.skillsmanagement.dto.SkillDto;
import com.example.skillsmanagement.dto.UserDetailsDto;
import com.example.skillsmanagement.exception.UnauthorizedException;
import com.example.skillsmanagement.response.SkillResponse;
import com.example.skillsmanagement.response.SkillsNameResponse;
import com.example.skillsmanagement.service.AuthService;
import com.example.skillsmanagement.service.SkillService;
import com.example.skillsmanagement.util.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SkillController {

    @Value("${auth.url}")
    private String authUrl;

    final
    SkillService skillService;

    final
    AuthService authService;
    public SkillController(SkillService skillService, AuthService authService) {
        this.skillService = skillService;
        this.authService = authService;
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<SkillResponse>> createSkill(@RequestBody SkillDto skillDto,@RequestHeader("Authorization") String token){

        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl,token);
        if (!authService.isAdmin(userDetailsDto.getRole())){
            throw new UnauthorizedException("only admin can perform this action");
        }

        SkillResponse skillResponse = skillService.createSkill(skillDto);
        return ResponseEntity.ok(new ApiResponse<>(true,"skill has been added" , skillResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillResponse>> updateSkill(@RequestBody SkillDto skillDto, @PathVariable Long id,@RequestHeader("Authorization") String token){

        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl,token);
        if (!authService.isAdmin(userDetailsDto.getRole())){
            throw new UnauthorizedException("only admin can perform this action");
        }


        SkillResponse skillResponse= skillService.updateSkill(id,skillDto);
        return ResponseEntity.ok(new ApiResponse<>(true,"skill has been updated",skillResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SkillResponse>> getSkill(@PathVariable Long id,@RequestHeader("Authorization") String token){

        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl,token);
        if (!authService.isAdmin(userDetailsDto.getRole())){
            throw new UnauthorizedException("only admin can perform this action");
        }


        return ResponseEntity.ok(new ApiResponse<>(true,"skill has been fetcehd",skillService.getSkillById(id)));
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<SkillResponse>>> getSkills(){


        return ResponseEntity.ok(new ApiResponse<>(true,"skills have been fetched" , skillService.getAllSkills()));
    }

    @GetMapping("/names")
    public ResponseEntity<ApiResponse<List<SkillsNameResponse>>> getSkillsNames(){
        return ResponseEntity.ok(new ApiResponse<>(true,"skills have been fetched",skillService.getAllSkillsNames()));
    }

    @PutMapping("/approve/{courseId}")
    public ResponseEntity<ApiResponse<?>> approveSkill(@PathVariable Long courseId,@RequestHeader("Authorization") String token){

        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl,token);
        if (!authService.isAdmin(userDetailsDto.getRole())){
            throw new UnauthorizedException("only admin can perform this action");
        }

        skillService.approveSkill(courseId);
        return ResponseEntity.ok(new ApiResponse<>(true,"skill has been approved",null));

    }

    @PutMapping("/refuse/{courseId}")
    public ResponseEntity<ApiResponse<?>> refuseSkill(@PathVariable Long courseId,@RequestHeader("Authorization") String token){

        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl,token);
        if (!authService.isAdmin(userDetailsDto.getRole())){
            throw new UnauthorizedException("only admin can perform this action");
        }

        skillService.refuseSkill(courseId);
        return ResponseEntity.ok(new ApiResponse<>(true,"skill has been refused",null));

    }

}

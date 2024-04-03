package com.example.skillsmanagement.controller;


import com.example.skillsmanagement.dto.StudentSkillDto;
import com.example.skillsmanagement.response.SkillResponse;
import com.example.skillsmanagement.service.StudentService;
import com.example.skillsmanagement.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    final
    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/")
    ResponseEntity<ApiResponse<SkillResponse>> addSkillToStudent(@RequestBody StudentSkillDto studentSkillDto){


        SkillResponse skillResponse = studentService.addSkillToStudent(studentSkillDto);
        return ResponseEntity.ok(new ApiResponse<>(true,"skill has been added", skillResponse));

    }
}

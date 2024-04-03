package com.example.skillsmanagement.service;

import com.example.skillsmanagement.dto.StudentSkillDto;
import com.example.skillsmanagement.response.SkillResponse;

public interface StudentService {

    SkillResponse addSkillToStudent(StudentSkillDto studentSkillDto);
}

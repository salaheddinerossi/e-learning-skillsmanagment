package com.example.skillsmanagement.service;

import com.example.skillsmanagement.dto.SkillDto;
import com.example.skillsmanagement.response.SkillResponse;
import com.example.skillsmanagement.response.SkillsNameResponse;

import java.util.List;

public interface SkillService {

    SkillResponse createSkill(SkillDto skillDto);

    SkillResponse updateSkill(Long id,SkillDto skillDto);

    void approveSkill(Long courseID);

    void refuseSkill(Long SkillID);

    SkillResponse getSkillById(Long id);

    List<SkillResponse> getAllSkills();

    List<SkillsNameResponse> getAllSkillsNames();

    //assign skill to a student

}

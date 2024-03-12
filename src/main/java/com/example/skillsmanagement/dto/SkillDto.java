package com.example.skillsmanagement.dto;

import com.example.skillsmanagement.Enum.SkillLevel;
import lombok.Data;

@Data
public class SkillDto {

    private String name;

    private String image;

    private String skillDescription;

    private Enum<SkillLevel> skillLevelEnum;

}

package com.example.skillsmanagement.response;

import com.example.skillsmanagement.Enum.SkillLevel;
import lombok.Data;

@Data
public class SkillResponse {

    private Long id;

    private String name;

    private String image;

    private String skillDescription;

    private Enum<SkillLevel> skillLevelEnum;

}

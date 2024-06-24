package com.example.skillsmanagement.dto;

import com.example.skillsmanagement.Enum.SkillLevel;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SkillDto {

    private String name;

    private MultipartFile image;

    private String skillDescription;

    private SkillLevel skillLevel;

}

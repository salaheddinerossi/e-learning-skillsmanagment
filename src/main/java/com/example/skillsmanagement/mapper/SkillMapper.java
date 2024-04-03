package com.example.skillsmanagement.mapper;


import com.example.skillsmanagement.dto.SkillDto;
import com.example.skillsmanagement.model.Skill;
import com.example.skillsmanagement.response.SkillResponse;
import com.example.skillsmanagement.response.SkillsNameResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface SkillMapper {

    @Mapping(source = "skillLevel",target = "skillLevel")
    SkillResponse skillToSkillResponse(Skill skill);

    Skill skillDtoToSkill(SkillDto skillDto);

    SkillsNameResponse skillToSkillsNameResponse(Skill skill);

    List<SkillsNameResponse> SkillListToSkillNameResponseList(List<Skill> skills);

    List<SkillResponse> skillListToSkillResponseList(List<Skill> skills);
}

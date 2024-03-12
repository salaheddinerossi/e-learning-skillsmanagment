package com.example.skillsmanagement.serviceImpl;

import com.example.skillsmanagement.Enum.CourseStatus;
import com.example.skillsmanagement.dto.SkillDto;
import com.example.skillsmanagement.exception.CourseNotFoundException;
import com.example.skillsmanagement.exception.SkillNotFoundException;
import com.example.skillsmanagement.model.Course;
import com.example.skillsmanagement.model.Skill;
import com.example.skillsmanagement.repository.CourseRepository;
import com.example.skillsmanagement.repository.SkillRepository;
import com.example.skillsmanagement.response.SkillResponse;
import com.example.skillsmanagement.response.SkillsNameResponse;
import com.example.skillsmanagement.service.SkillService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SkillServiceImpl implements SkillService {

    final
    SkillRepository skillRepository;

    final
    CourseRepository courseRepository;

    public SkillServiceImpl(CourseRepository courseRepository, SkillRepository skillRepository) {
        this.courseRepository = courseRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public void createSkill(SkillDto skillDto) {

        Skill skill = setSkill(skillDto);
        skillRepository.save(skill);

    }

    @Override
    public void updateSkill(Long id, SkillDto skillDto) {

        if(!skillRepository.findById(id).isPresent()){
            throw new SkillNotFoundException();
        }

        Skill skill = setSkill(skillDto);
        skill.setId(id);

        skillRepository.save(skill);

    }

    @Override
    public void approveSkill(Long courseID) {

        Course course = courseRepository.findById(courseID).orElseThrow(
                CourseNotFoundException::new
        );

        course.setCourseStatusEnum(CourseStatus.APPROVED);
        courseRepository.save(course);

    }

    @Override
    public void refuseSkill(Long courseID) {
        Course course = courseRepository.findById(courseID).orElseThrow(
                CourseNotFoundException::new
        );

        course.setSkill(null);

        course.setCourseStatusEnum(CourseStatus.APPROVED);
        courseRepository.save(course);

    }

    @Override
    public SkillResponse getSkillById(Long id) {
        Skill skill = skillRepository.findById(id).orElseThrow(
                SkillNotFoundException::new
        );

        return setSkillResponse(skill);
    }

    @Override
    public List<SkillResponse> getAllSkills() {

        List<SkillResponse> skillResponses = new ArrayList<>();

        List<Skill> skills = skillRepository.findAll();

        for (Skill skill:skills){
            skillResponses.add(setSkillResponse(skill));
        }


        return skillResponses;
    }

    @Override
    public List<SkillsNameResponse> getAllSkillsNames() {

        List<SkillsNameResponse> skillsNameResponses = new ArrayList<>();

        List<Skill> skills = skillRepository.findAll();

        SkillsNameResponse skillsNameResponse = new SkillsNameResponse();

        for (Skill skill:skills){

            skillsNameResponse.setId(skill.getId());
            skillsNameResponse.setTitle(skill.getImage());
            skillsNameResponses.add(skillsNameResponse);

        }

        return skillsNameResponses;
    }

    public Skill setSkill(SkillDto skillDto){
        Skill skill = new Skill();
        skill.setSkillLevelEnum(skillDto.getSkillLevelEnum());
        skill.setSkillDescription(skillDto.getSkillDescription());
        skill.setName(skillDto.getName());
        skill.setImage(skillDto.getImage());

        return skill;
    }

    public SkillResponse setSkillResponse(Skill skill){

        SkillResponse skillResponse = new SkillResponse();

        skillResponse.setId(skill.getId());
        skillResponse.setSkillDescription(skill.getSkillDescription());
        skillResponse.setSkillLevelEnum(skill.getSkillLevelEnum());
        skillResponse.setName(skillResponse.getName());
        skillResponse.setImage(skillResponse.getImage());

        return skillResponse;

    }

}

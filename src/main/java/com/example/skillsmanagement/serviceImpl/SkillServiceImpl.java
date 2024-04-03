package com.example.skillsmanagement.serviceImpl;

import com.example.skillsmanagement.Enum.CourseStatus;
import com.example.skillsmanagement.dto.SkillDto;
import com.example.skillsmanagement.exception.ResourceNotFoundException;
import com.example.skillsmanagement.mapper.SkillMapper;
import com.example.skillsmanagement.model.Course;
import com.example.skillsmanagement.model.Skill;
import com.example.skillsmanagement.repository.CourseRepository;
import com.example.skillsmanagement.repository.SkillRepository;
import com.example.skillsmanagement.response.SkillResponse;
import com.example.skillsmanagement.response.SkillsNameResponse;
import com.example.skillsmanagement.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SkillServiceImpl implements SkillService {

    final
    SkillRepository skillRepository;

    final
    CourseRepository courseRepository;

    final
    SkillMapper skillMapper;

    public SkillServiceImpl(CourseRepository courseRepository, SkillRepository skillRepository, SkillMapper skillMapper) {
        this.courseRepository = courseRepository;
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Override
    public SkillResponse createSkill(SkillDto skillDto) {

        Skill skill = skillRepository.save(skillMapper.skillDtoToSkill(skillDto));

        return skillMapper.skillToSkillResponse(skill);

    }

    @Override
    public SkillResponse updateSkill(Long id, SkillDto skillDto) {

        if(skillRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("skill not found with this id :" + id);
        }

        Skill skill = skillMapper.skillDtoToSkill(skillDto);
        skill.setId(id);

        Skill skill1 = skillRepository.save(skill);
        return skillMapper.skillToSkillResponse(skill1);
    }

    @Override
    public void approveSkill(Long courseID) {

        Course course = courseRepository.findById(courseID).orElseThrow(
                () -> new ResourceNotFoundException("course not found with this id :" + courseID)
        );

        course.setCourseStatusEnum(CourseStatus.APPROVED);
        courseRepository.save(course);

    }

    @Override
    public void refuseSkill(Long courseID) {
        Course course = courseRepository.findById(courseID).orElseThrow(
                () -> new ResourceNotFoundException("course not found with this id :" + courseID)
        );

        course.setSkill(null);

        course.setCourseStatusEnum(CourseStatus.APPROVED);
        courseRepository.save(course);

    }

    @Override
    public SkillResponse getSkillById(Long id) {
        Skill skill = skillRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("skill not found with this id :" + id)
        );

        return skillMapper.skillToSkillResponse(skill);
    }

    @Override
    public List<SkillResponse> getAllSkills() {

        List<Skill> skills = skillRepository.findAll();
        return skillMapper.skillListToSkillResponseList(skills);
    }

    @Override
    public List<SkillsNameResponse> getAllSkillsNames() {
        return skillMapper.SkillListToSkillNameResponseList(skillRepository.findAll());
    }



}

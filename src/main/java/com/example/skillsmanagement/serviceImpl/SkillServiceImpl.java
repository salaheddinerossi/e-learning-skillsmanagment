package com.example.skillsmanagement.serviceImpl;

import com.example.skillsmanagement.Enum.CourseStatus;
import com.example.skillsmanagement.dto.SkillDto;
import com.example.skillsmanagement.exception.ResourceNotFoundException;
import com.example.skillsmanagement.mapper.SkillMapper;
import com.example.skillsmanagement.model.Course;
import com.example.skillsmanagement.model.Skill;
import com.example.skillsmanagement.repository.CourseRepository;
import com.example.skillsmanagement.repository.SkillRepository;
import com.example.skillsmanagement.repository.StudentRepository;
import com.example.skillsmanagement.response.SkillResponse;
import com.example.skillsmanagement.response.SkillsNameResponse;
import com.example.skillsmanagement.service.SkillService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
public class SkillServiceImpl implements SkillService {

    final
    StudentRepository studentRepository;

    final
    SkillRepository skillRepository;

    final
    CourseRepository courseRepository;

    final
    SkillMapper skillMapper;

    final
    StorageService storageService;

    public SkillServiceImpl(CourseRepository courseRepository, SkillRepository skillRepository, SkillMapper skillMapper, StudentRepository studentRepository, StorageService storageService) {
        this.courseRepository = courseRepository;
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
        this.studentRepository = studentRepository;
        this.storageService = storageService;
    }

    @Override
    public SkillResponse createSkill(SkillDto skillDto) throws IOException {



        Skill skill = skillRepository.save(skillMapper.skillDtoToSkill(skillDto));

        String fileName = generateUniqueFileName(Objects.requireNonNull(skillDto.getImage().getOriginalFilename()));
        String presignedUrl = storageService.generatePresignedUrl(fileName);
        storageService.uploadFileToS3(skillDto.getImage(), presignedUrl);

        skill.setImage(storageService.getFileUrl(fileName).toString());


        return skillMapper.skillToSkillResponse(skill);

    }


    @Override
    public SkillResponse updateSkill(Long id, SkillDto skillDto) throws IOException {

        if(skillRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("skill not found with this id :" + id);
        }

        Skill skill = findSkillById(id);


        String fileName = generateUniqueFileName(Objects.requireNonNull(skillDto.getImage().getOriginalFilename()));
        String presignedUrl = storageService.generatePresignedUrl(fileName);
        storageService.uploadFileToS3(skillDto.getImage(), presignedUrl);

        skill.setImage(storageService.getFileUrl(fileName).toString());
        skill.setSkillLevel(skillDto.getSkillLevel());
        skill.setName(skillDto.getName());
        skill.setSkillDescription(skillDto.getSkillDescription());

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

    @Override
    public List<SkillResponse> getStudentSkills(String email) {

        List<Skill> skills = skillRepository.findByStudentsEmail(email);

        return skillMapper.skillListToSkillResponseList(skills);
    }

    Skill findSkillById(Long id){
        return this.skillRepository.findById(id).orElseThrow(
                () ->new ResourceNotFoundException("skill not found with the id:" +id)
        );
    }

    private String generateUniqueFileName(String originalFileName) {
        String extension = "";
        int i = originalFileName.lastIndexOf('.');
        if (i > 0) {
            extension = originalFileName.substring(i);
        }
        return UUID.randomUUID().toString() + extension;
    }


}

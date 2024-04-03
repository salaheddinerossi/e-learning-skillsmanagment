package com.example.skillsmanagement.serviceImpl;

import com.example.skillsmanagement.dto.StudentSkillDto;
import com.example.skillsmanagement.exception.ResourceNotFoundException;
import com.example.skillsmanagement.mapper.SkillMapper;
import com.example.skillsmanagement.model.Course;
import com.example.skillsmanagement.model.Skill;
import com.example.skillsmanagement.model.Student;
import com.example.skillsmanagement.repository.CourseRepository;
import com.example.skillsmanagement.repository.SkillRepository;
import com.example.skillsmanagement.repository.StudentRepository;
import com.example.skillsmanagement.response.SkillResponse;
import com.example.skillsmanagement.service.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImp implements StudentService {

    final
    StudentRepository studentRepository;

    final
    CourseRepository courseRepository;

    final
    SkillRepository skillRepository;

    final
    SkillMapper skillMapper;

    public StudentServiceImp(StudentRepository studentRepository, CourseRepository courseRepository, SkillRepository skillRepository, SkillMapper skillMapper) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
    }

    @Override
    @Transactional
    public SkillResponse addSkillToStudent(StudentSkillDto studentSkillDto) {
        Student student = findStudentById(studentSkillDto.getStudentId());
        Skill skill = findCourseById(studentSkillDto.getCourseId()).getSkill();

        student.getSkills().add(skill);
        skill.getStudents().add(student);

        studentRepository.save(student);
        skillRepository.save(skill);

        return skillMapper.skillToSkillResponse(skill);
    }

    private Course findCourseById(Long id){
        return courseRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("no course with this id: "+id)
        );
    }

    private Skill findSkillById(Long id){
        return skillRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("no skill found wit this id: "+ id)
        );
    }

    private Student findStudentById(Long id){
        return studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("no student found with this id")
        );
    }
}

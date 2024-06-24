package com.example.skillsmanagement.repository                       ;

import com.example.skillsmanagement.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill,Long> {
    List<Skill> findByStudentsEmail(String email);

}

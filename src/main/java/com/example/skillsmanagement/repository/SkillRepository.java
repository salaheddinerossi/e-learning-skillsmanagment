package com.example.skillsmanagement.repository;

import com.example.skillsmanagement.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill,Long> {
}

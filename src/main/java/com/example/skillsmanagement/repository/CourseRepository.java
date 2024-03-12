package com.example.skillsmanagement.repository;

import com.example.skillsmanagement.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}

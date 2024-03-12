package com.example.skillsmanagement.model;

import com.example.skillsmanagement.Enum.CourseStatus;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    private String title;

    private String about;

    private String requirements;

    private Enum<CourseStatus> courseStatusEnum;


    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;



}

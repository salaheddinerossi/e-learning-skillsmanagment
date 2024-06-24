package com.example.skillsmanagement.model;

import com.example.skillsmanagement.Enum.SkillLevel;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "skill")

public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private String skillDescription;

    private SkillLevel skillLevel;

    @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> courses = new ArrayList<>();

    @ManyToMany(mappedBy = "skills")
    private List<Student> students = new ArrayList<>();
}

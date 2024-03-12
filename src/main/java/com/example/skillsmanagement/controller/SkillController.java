package com.example.skillsmanagement.controller;


import com.example.skillsmanagement.dto.SkillDto;
import com.example.skillsmanagement.service.SkillService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SkillController {

    final
    SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createSkill(@RequestBody SkillDto skillDto){
        skillService.createSkill(skillDto);
        return ResponseEntity.ok("the skill has been added");
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateSkill(@RequestBody SkillDto skillDto, @PathVariable Long id){
        skillService.updateSkill(id,skillDto);
        return ResponseEntity.ok("the skill has been updated");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSkill(@PathVariable Long id){
        return ResponseEntity.ok(skillService.getSkillById(id));
    }

    @GetMapping("/")
    public ResponseEntity<?> getSkills(){
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @GetMapping("/names")
    public ResponseEntity<?> getSkillsNames(){
        return ResponseEntity.ok(skillService.getAllSkillsNames());
    }

}

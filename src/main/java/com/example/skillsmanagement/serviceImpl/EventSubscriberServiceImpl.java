package com.example.skillsmanagement.serviceImpl;

import com.example.skillsmanagement.dto.StudentSkillDto;
import com.example.skillsmanagement.service.EventSubscriberService;
import com.example.skillsmanagement.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Service;

@Service
public class EventSubscriberServiceImpl implements EventSubscriberService {

    private final ObjectMapper objectMapper;

    private final StudentService studentService;

    public EventSubscriberServiceImpl(ObjectMapper objectMapper, StudentService studentService) {
        this.objectMapper = objectMapper;
        this.studentService = studentService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        StudentSkillDto studentSkillDto = null;

        try {
            String body = new String(message.getBody());
            studentSkillDto = objectMapper.readValue(body, StudentSkillDto.class);
        } catch (Exception e) {
            System.err.println("Error deserializing message: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        try {
            if (studentSkillDto.getStudentId() == null || studentSkillDto.getCourseId() == null) {
                throw new IllegalArgumentException("StudentId or CourseId is missing in the message");
            }

            studentService.addSkillToStudent(studentSkillDto);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

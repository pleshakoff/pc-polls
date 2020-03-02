package com.parcom.polls.model.student;

import com.parcom.polls.model.group.GroupService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class StudentServiceImplTestConfiguration {

    @Bean
    StudentService studentService(StudentRepository studentRepository, StudentToUserRepository studentToUserRepository, GroupService groupService) {
        return  new StudentServiceImpl(studentRepository,studentToUserRepository,groupService);
    }
}

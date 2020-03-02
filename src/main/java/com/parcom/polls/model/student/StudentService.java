package com.parcom.polls.model.student;

import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface StudentService {


    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    List<Student> all(Long idPoll);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    Student create(StudentDto studentDto);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    void delete(Long id);
}

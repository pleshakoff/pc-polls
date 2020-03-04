package com.parcom.polls.model.student;

import java.util.List;

public interface StudentService {
    List<Student> getGroupStudents();

    Student getStudentById(Long id);
}

package com.parcom.polls.model.student;

import com.parcom.polls.SpringSecurityTestConfiguration;
import com.parcom.polls.model.group.Group;
import com.parcom.polls.model.group.GroupServiceImpl;
import com.parcom.exceptions.NotFoundParcomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDate;
import java.util.Arrays;

import static com.parcom.polls.SpringSecurityTestConfiguration.ID_USER_ADMIN;
import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = {StudentServiceImplTestConfiguration.class,
        SpringSecurityTestConfiguration.class})
public class StudentServiceImplTest {


    private static final long ID_STUDENT_ONE = 1L;
    private static final long ID_STUDENT_TWO = 2L;
    private static final long ID_GROUP_ONE = 1L;
    private static final String FIRST_NAME = "ivan";
    private static final String MIDDLE_NAME = "ivanovich";
    private static final String FAMILY_NAME = "ivanov";

    @Autowired
    StudentService studentService;

    @MockBean
    StudentRepository studentRepository;
    @MockBean
    StudentToUserRepository studentToUserRepository;
    @MockBean
    GroupServiceImpl groupServiceImpl;

    @BeforeEach
    void initMocks() {

    }

    @Test
    @WithUserDetails("admin@parcom.com")
    public void getById() {
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(Student.builder().id(ID_STUDENT_ONE).build()));
        Student student = studentService.getById(ID_STUDENT_ONE);
        assertEquals(ID_STUDENT_ONE, student.getId());
    }

    @Test
    @WithUserDetails("admin@parcom.com")
    public void getByIdNotFound() {
        assertThrows(NotFoundParcomException.class, () -> {
            studentService.getById(ID_STUDENT_ONE);
        });
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void getCurrentStudent() {
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(Student.builder().id(ID_STUDENT_ONE).build()));
        Student student = studentService.getCurrentStudent();
        assertEquals(ID_STUDENT_ONE, student.getId());
    }


    @Test
    @WithUserDetails("childFreeMember@parcom.com")
    public void getCurrentStudentForUserWithoutStudent() {

        assertNull(studentService.getCurrentStudent());
    }

    @Test
    @WithUserDetails("admin@parcom.com")
    public void getCurrentStudentNotFound() {
        assertThrows(NotFoundParcomException.class, () -> {
            studentService.getCurrentStudent();
        });

    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void getMyStudents() {

        Mockito.when(studentToUserRepository.getMyStudents(ID_USER_ADMIN)).
                thenReturn(Arrays.asList(Student.builder().id(ID_STUDENT_ONE).build(), Student.builder().id(ID_STUDENT_TWO).build())
                );

        assertEquals(2, studentService.getMyStudents(null).size());
    }

    @Test
    @WithUserDetails("admin@parcom.com")
    public void getMyStudentsByGroup() {

        Mockito.when(studentToUserRepository.getMyStudentsInGroup(ID_USER_ADMIN, ID_GROUP_ONE)).
                thenReturn(Arrays.asList(Student.builder().id(ID_STUDENT_ONE).build(), Student.builder().id(ID_STUDENT_TWO).build())
                );

        assertEquals(2, studentService.getMyStudents(ID_GROUP_ONE).size());


    }

    @Test
    public void getMyStudentsByGroupUnauthorised() {
        Mockito.when(studentToUserRepository.getMyStudents(ID_USER_ADMIN)).
                thenReturn(Arrays.asList(Student.builder().id(ID_STUDENT_ONE).build(), Student.builder().id(ID_STUDENT_TWO).build())
                );
        assertEquals(0, studentService.getMyStudents(1L).size());
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void getMyStudent() {
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(Student.builder().id(ID_STUDENT_ONE).build()));
        Student student = studentService.getMyStudent(ID_STUDENT_ONE);
        assertEquals(ID_STUDENT_ONE, student.getId());
    }

    @Test
    @WithUserDetails("member@parcom.com")
    public void getMyStudentNotFound() {
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(Student.builder().id(ID_STUDENT_ONE).build()));
        assertThrows(NotFoundParcomException.class, () -> {
            studentService.getMyStudent(ID_STUDENT_TWO);
        });
    }


    @Test
    @WithUserDetails("parent@parcom.com")
    public void getMyStudentWrongParent() {
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(Student.builder().id(ID_STUDENT_ONE).build()));
        assertThrows(NotFoundParcomException.class, () -> {
            studentService.getMyStudent(ID_STUDENT_ONE);
        });
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void getStudents() {
        Mockito.when(studentRepository.getStudentsByGroup(ID_GROUP_ONE)).
                thenReturn(Arrays.asList(Student.builder().id(ID_STUDENT_ONE).build(), Student.builder().id(ID_STUDENT_TWO).build())
                );

        assertEquals(2, studentService.getStudents().size());
    }

    @Test
    @WithUserDetails("parent@parcom.com")
    public void getStudentsAccessDenied() {
        assertThrows(AccessDeniedException.class, () ->
            studentService.getStudents()
        );
    }

    @Test
    @WithUserDetails("fromAnotherGroup@parcom.com")
    public void getStudentsNotMyGroup() {
        Mockito.when(studentRepository.getStudentsByGroup(ID_GROUP_ONE)).
                thenReturn(Arrays.asList(Student.builder().id(ID_STUDENT_ONE).build(), Student.builder().id(ID_STUDENT_TWO).build())
                );
        assertEquals(0, studentService.getStudents().size());
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void create() {

        LocalDate now = LocalDate.now();

        Group group = Group.builder().id(ID_GROUP_ONE).build();

        Mockito.when(groupServiceImpl.getCurrentGroup()).
                thenReturn(group);

        StudentDto studentDTO = StudentDto.builder().
                firstName(FIRST_NAME).
                middleName(MIDDLE_NAME).
                familyName(FAMILY_NAME).
                birthDay(now).build();

       Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenAnswer(i -> i.getArguments()[0]);

       Student insertedStudent = studentService.create(studentDTO);


        assertAll("creation",
                () -> assertEquals(FIRST_NAME,insertedStudent.getFirstName()),
                () -> assertEquals(MIDDLE_NAME,insertedStudent.getMiddleName()),
                () -> assertEquals(FAMILY_NAME,insertedStudent.getFamilyName()),
                () -> assertEquals(now,insertedStudent.getBirthDay())
                );
    }


    @Test
    @WithUserDetails("parent@parcom.com")
    public void createByParent() {

        LocalDate now = LocalDate.now();

        StudentDto studentDTO = StudentDto.builder().
                firstName(FIRST_NAME).
                middleName(MIDDLE_NAME).
                familyName(FAMILY_NAME).
                birthDay(now).build();

        assertThrows(AccessDeniedException.class, () -> {
            studentService.create(studentDTO);
        });
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void update() {
        LocalDate now = LocalDate.now().plusDays(1);

        Student student = Student.builder().id(ID_STUDENT_ONE).firstName("1").
                middleName("2").
                familyName("3").
                birthDay(LocalDate.now()).
                build();

        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(student));

        StudentDto studentDTO = StudentDto.builder().
                firstName(FIRST_NAME).
                middleName(MIDDLE_NAME).
                familyName(FAMILY_NAME).
                birthDay(now).build();


        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenAnswer(i -> i.getArguments()[0]);

        Student updatedStudent = studentService.update(ID_STUDENT_ONE,studentDTO);


        assertAll("update",
                () -> assertEquals(FIRST_NAME,updatedStudent.getFirstName()),
                () -> assertEquals(MIDDLE_NAME,updatedStudent.getMiddleName()),
                () -> assertEquals(FAMILY_NAME,updatedStudent.getFamilyName()),
                () -> assertEquals(now,updatedStudent.getBirthDay())
        );
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void updateWrongId() {
        LocalDate now = LocalDate.now().plusDays(1);

        StudentDto studentDTO = StudentDto.builder().
                firstName(FIRST_NAME).
                middleName(MIDDLE_NAME).
                familyName(FAMILY_NAME).
                birthDay(now).build();


        assertThrows(NotFoundParcomException.class, () -> {
            studentService.update(ID_STUDENT_TWO,studentDTO);
        });
    }


    @Test
    @WithUserDetails("parent@parcom.com")
    public void updateByParent() {

        LocalDate now = LocalDate.now();

        StudentDto studentDTO = StudentDto.builder().
                firstName(FIRST_NAME).
                middleName(MIDDLE_NAME).
                familyName(FAMILY_NAME).
                birthDay(now).build();

        assertThrows(AccessDeniedException.class, () -> {
            studentService.update(ID_STUDENT_ONE,studentDTO);
        });
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void delete() {

        Student student = Student.builder().id(ID_STUDENT_ONE).build();
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(student));
            studentService.delete(ID_STUDENT_ONE);
    }

    @Test
    @WithUserDetails("admin@parcom.com")
    public void deleteNotFound() {
        assertThrows(NotFoundParcomException.class, () -> {
            studentService.delete(ID_STUDENT_ONE);
        });
    }

    @Test
    @WithUserDetails("parent@parcom.com")
    public void deleteByParent() {
        assertThrows(AccessDeniedException.class, () -> {
            studentService.delete(ID_STUDENT_ONE);
        });
    }

}
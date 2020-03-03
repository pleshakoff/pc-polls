package com.parcom.polls.model.voter;

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
public class VoterServiceImplTest {


    private static final long ID_STUDENT_ONE = 1L;
    private static final long ID_STUDENT_TWO = 2L;
    private static final long ID_GROUP_ONE = 1L;
    private static final String FIRST_NAME = "ivan";
    private static final String MIDDLE_NAME = "ivanovich";
    private static final String FAMILY_NAME = "ivanov";

    @Autowired
    VoterService voterService;

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
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(Voter.builder().id(ID_STUDENT_ONE).build()));
        Voter voter = voterService.getById(ID_STUDENT_ONE);
        assertEquals(ID_STUDENT_ONE, voter.getId());
    }

    @Test
    @WithUserDetails("admin@parcom.com")
    public void getByIdNotFound() {
        assertThrows(NotFoundParcomException.class, () -> {
            voterService.getById(ID_STUDENT_ONE);
        });
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void getCurrentStudent() {
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(Voter.builder().id(ID_STUDENT_ONE).build()));
        Voter voter = voterService.getCurrentStudent();
        assertEquals(ID_STUDENT_ONE, voter.getId());
    }


    @Test
    @WithUserDetails("childFreeMember@parcom.com")
    public void getCurrentStudentForUserWithoutStudent() {

        assertNull(voterService.getCurrentStudent());
    }

    @Test
    @WithUserDetails("admin@parcom.com")
    public void getCurrentStudentNotFound() {
        assertThrows(NotFoundParcomException.class, () -> {
            voterService.getCurrentStudent();
        });

    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void getMyStudents() {

        Mockito.when(studentToUserRepository.getMyStudents(ID_USER_ADMIN)).
                thenReturn(Arrays.asList(Voter.builder().id(ID_STUDENT_ONE).build(), Voter.builder().id(ID_STUDENT_TWO).build())
                );

        assertEquals(2, voterService.getMyStudents(null).size());
    }

    @Test
    @WithUserDetails("admin@parcom.com")
    public void getMyStudentsByGroup() {

        Mockito.when(studentToUserRepository.getMyStudentsInGroup(ID_USER_ADMIN, ID_GROUP_ONE)).
                thenReturn(Arrays.asList(Voter.builder().id(ID_STUDENT_ONE).build(), Voter.builder().id(ID_STUDENT_TWO).build())
                );

        assertEquals(2, voterService.getMyStudents(ID_GROUP_ONE).size());


    }

    @Test
    public void getMyStudentsByGroupUnauthorised() {
        Mockito.when(studentToUserRepository.getMyStudents(ID_USER_ADMIN)).
                thenReturn(Arrays.asList(Voter.builder().id(ID_STUDENT_ONE).build(), Voter.builder().id(ID_STUDENT_TWO).build())
                );
        assertEquals(0, voterService.getMyStudents(1L).size());
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void getMyStudent() {
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(Voter.builder().id(ID_STUDENT_ONE).build()));
        Voter voter = voterService.getMyStudent(ID_STUDENT_ONE);
        assertEquals(ID_STUDENT_ONE, voter.getId());
    }

    @Test
    @WithUserDetails("member@parcom.com")
    public void getMyStudentNotFound() {
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(Voter.builder().id(ID_STUDENT_ONE).build()));
        assertThrows(NotFoundParcomException.class, () -> {
            voterService.getMyStudent(ID_STUDENT_TWO);
        });
    }


    @Test
    @WithUserDetails("parent@parcom.com")
    public void getMyStudentWrongParent() {
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(Voter.builder().id(ID_STUDENT_ONE).build()));
        assertThrows(NotFoundParcomException.class, () -> {
            voterService.getMyStudent(ID_STUDENT_ONE);
        });
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void getStudents() {
        Mockito.when(studentRepository.getStudentsByGroup(ID_GROUP_ONE)).
                thenReturn(Arrays.asList(Voter.builder().id(ID_STUDENT_ONE).build(), Voter.builder().id(ID_STUDENT_TWO).build())
                );

        assertEquals(2, voterService.getStudents().size());
    }

    @Test
    @WithUserDetails("parent@parcom.com")
    public void getStudentsAccessDenied() {
        assertThrows(AccessDeniedException.class, () ->
            voterService.getStudents()
        );
    }

    @Test
    @WithUserDetails("fromAnotherGroup@parcom.com")
    public void getStudentsNotMyGroup() {
        Mockito.when(studentRepository.getStudentsByGroup(ID_GROUP_ONE)).
                thenReturn(Arrays.asList(Voter.builder().id(ID_STUDENT_ONE).build(), Voter.builder().id(ID_STUDENT_TWO).build())
                );
        assertEquals(0, voterService.getStudents().size());
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void create() {

        LocalDate now = LocalDate.now();

        Group group = Group.builder().id(ID_GROUP_ONE).build();

        Mockito.when(groupServiceImpl.getCurrentGroup()).
                thenReturn(group);

        VoterDto voterDTO = VoterDto.builder().
                firstName(FIRST_NAME).
                middleName(MIDDLE_NAME).
                familyName(FAMILY_NAME).
                birthDay(now).build();

       Mockito.when(studentRepository.save(Mockito.any(Voter.class))).thenAnswer(i -> i.getArguments()[0]);

       Voter insertedVoter = voterService.create(voterDTO);


        assertAll("creation",
                () -> assertEquals(FIRST_NAME, insertedVoter.getFirstName()),
                () -> assertEquals(MIDDLE_NAME, insertedVoter.getMiddleName()),
                () -> assertEquals(FAMILY_NAME, insertedVoter.getFamilyName()),
                () -> assertEquals(now, insertedVoter.getBirthDay())
                );
    }


    @Test
    @WithUserDetails("parent@parcom.com")
    public void createByParent() {

        LocalDate now = LocalDate.now();

        VoterDto voterDTO = VoterDto.builder().
                firstName(FIRST_NAME).
                middleName(MIDDLE_NAME).
                familyName(FAMILY_NAME).
                birthDay(now).build();

        assertThrows(AccessDeniedException.class, () -> {
            voterService.create(voterDTO);
        });
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void update() {
        LocalDate now = LocalDate.now().plusDays(1);

        Voter voter = Voter.builder().id(ID_STUDENT_ONE).firstName("1").
                middleName("2").
                familyName("3").
                birthDay(LocalDate.now()).
                build();

        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(voter));

        VoterDto voterDTO = VoterDto.builder().
                firstName(FIRST_NAME).
                middleName(MIDDLE_NAME).
                familyName(FAMILY_NAME).
                birthDay(now).build();


        Mockito.when(studentRepository.save(Mockito.any(Voter.class))).thenAnswer(i -> i.getArguments()[0]);

        Voter updatedVoter = voterService.update(ID_STUDENT_ONE, voterDTO);


        assertAll("update",
                () -> assertEquals(FIRST_NAME, updatedVoter.getFirstName()),
                () -> assertEquals(MIDDLE_NAME, updatedVoter.getMiddleName()),
                () -> assertEquals(FAMILY_NAME, updatedVoter.getFamilyName()),
                () -> assertEquals(now, updatedVoter.getBirthDay())
        );
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void updateWrongId() {
        LocalDate now = LocalDate.now().plusDays(1);

        VoterDto voterDTO = VoterDto.builder().
                firstName(FIRST_NAME).
                middleName(MIDDLE_NAME).
                familyName(FAMILY_NAME).
                birthDay(now).build();


        assertThrows(NotFoundParcomException.class, () -> {
            voterService.update(ID_STUDENT_TWO, voterDTO);
        });
    }


    @Test
    @WithUserDetails("parent@parcom.com")
    public void updateByParent() {

        LocalDate now = LocalDate.now();

        VoterDto voterDTO = VoterDto.builder().
                firstName(FIRST_NAME).
                middleName(MIDDLE_NAME).
                familyName(FAMILY_NAME).
                birthDay(now).build();

        assertThrows(AccessDeniedException.class, () -> {
            voterService.update(ID_STUDENT_ONE, voterDTO);
        });
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    public void delete() {

        Voter voter = Voter.builder().id(ID_STUDENT_ONE).build();
        Mockito.when(studentRepository.findById(ID_STUDENT_ONE)).thenReturn(of(voter));
            voterService.delete(ID_STUDENT_ONE);
    }

    @Test
    @WithUserDetails("admin@parcom.com")
    public void deleteNotFound() {
        assertThrows(NotFoundParcomException.class, () -> {
            voterService.delete(ID_STUDENT_ONE);
        });
    }

    @Test
    @WithUserDetails("parent@parcom.com")
    public void deleteByParent() {
        assertThrows(AccessDeniedException.class, () -> {
            voterService.delete(ID_STUDENT_ONE);
        });
    }

}
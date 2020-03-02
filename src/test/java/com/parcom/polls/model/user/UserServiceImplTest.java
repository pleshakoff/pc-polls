package com.parcom.polls.model.user;

import com.parcom.polls.SpringSecurityTestConfiguration;
import com.parcom.polls.model.group.Group;
import com.parcom.polls.model.group.GroupToUser;
import com.parcom.polls.model.group.GroupToUserRepository;
import com.parcom.polls.model.student.Student;
import com.parcom.polls.model.student.StudentToUser;
import com.parcom.polls.model.student.StudentToUserRepository;
import com.parcom.exceptions.ForbiddenParcomException;
import com.parcom.exceptions.NotFoundParcomException;
import com.parcom.exceptions.ParcomException;
import com.parcom.network.Network;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.Arrays;
import java.util.Collections;

import static com.parcom.polls.SpringSecurityTestConfiguration.ID_USER_ADMIN;
import static com.parcom.polls.SpringSecurityTestConfiguration.ID_USER_PARENT;
import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = {UserServiceImplTestConfiguration.class,
        SpringSecurityTestConfiguration.class})
class UserServiceImplTest {

    private static final Long ID_GROUP_ONE = 1L;
    private static final Long ID_STUDENT_ONE =  1L;
    private static final String EMAIL = "test@test.com";
    private static final String FIRST_NAME = "firstName";
    private static final String MIDDLE_NAME = "middleName";
    private static final String FAMILY_NAME = "familyName";
    private static final String PHONE = "123456";

    @MockBean
    UserRepository userRepository;

    @MockBean
    Network network;

    @MockBean
    GroupToUserRepository groupToUserRepository;

    @MockBean
    StudentToUserRepository studentToUserRepository;

    @Autowired
    UserService userService;

    @Test
    @WithUserDetails("admin@parcom.com")
    void current() {
        Mockito.when(userRepository.findById(ID_USER_ADMIN)).thenReturn(of(User.builder().id(ID_USER_ADMIN).build()));
        assertEquals(ID_USER_ADMIN, userService.current().getId());
    }


    @Test
    void currentUnauthorized() {
        Mockito.when(userRepository.findById(ID_USER_ADMIN)).thenReturn(of(User.builder().id(ID_USER_ADMIN).build()));
        assertThrows(NotFoundParcomException.class,() ->  userService.current());
    }


    @Test
    void addUserToGroup() {

        Group group = Group.builder().id(ID_GROUP_ONE).build();
        User user = User.builder().id(ID_USER_ADMIN).build();
        GroupToUser groupToUser = GroupToUser.builder().group(group).user(user).build();
        Mockito.when(groupToUserRepository.save(groupToUser)).thenReturn(groupToUser);
        userService.addUserToGroup(group,user);

    }

    @Test
    void addUserToGroupNullGroup() {
        User user = User.builder().id(ID_USER_ADMIN).build();
        assertThrows(ParcomException.class,() ->  userService.addUserToGroup(null,user));
    }

    @Test
    void addUserToGroupNullUser() {
        Group group = Group.builder().id(ID_GROUP_ONE).build();
        assertThrows(ParcomException.class,() ->  userService.addUserToGroup(group,null));
    }


    @Test
    void addUserToStudent() {
        Student student = Student.builder().id(ID_STUDENT_ONE).build();
        User user = User.builder().id(ID_USER_ADMIN).build();
        StudentToUser studentToUser = StudentToUser.builder().student(student).user(user).build();
        Mockito.when(studentToUserRepository.save(studentToUser)).thenReturn(studentToUser);
        userService.addUserToStudent(student,user);
    }


    @Test
    void addUserToStudentNullUser() {
        Student student = Student.builder().id(ID_STUDENT_ONE).build();
        assertThrows(ParcomException.class,() ->  userService.addUserToStudent(student,null));

    }

    @Test
    void addUserToStudentNullStudent() {
        User user = User.builder().id(ID_USER_ADMIN).build();
        assertThrows(ParcomException.class,() ->  userService.addUserToStudent(null,user));

    }


    @Test
    @WithUserDetails("admin@parcom.com")
    void getByIdAdmin() {
        Mockito.when( groupToUserRepository.findMyGroupUsers(ID_GROUP_ONE)).
                thenReturn( Arrays.asList(User.builder().id(ID_USER_ADMIN).build(),
                                          User.builder().id(ID_USER_PARENT).build()));

        assertAll(
        () ->  assertEquals(ID_USER_ADMIN, userService.getById(ID_USER_ADMIN).getId()),
        () ->  assertEquals(ID_USER_PARENT, userService.getById(ID_USER_PARENT).getId())
        );

    }


    @Test
    @WithUserDetails("parent@parcom.com")
    void getByIdParent() {
        Mockito.when( groupToUserRepository.findMyGroupUsers(ID_GROUP_ONE)).
                thenReturn( Arrays.asList(User.builder().id(ID_USER_ADMIN).build(),
                        User.builder().id(ID_USER_PARENT).build()));
        assertEquals(ID_USER_PARENT, userService.getById(ID_USER_PARENT).getId());

    }

    @Test
    @WithUserDetails("parent@parcom.com")
    void getByIdParentNotFound() {
        Mockito.when( groupToUserRepository.findMyGroupUsers(ID_GROUP_ONE)).
                thenReturn( Arrays.asList(User.builder().id(ID_USER_ADMIN).build(),
                        User.builder().id(ID_USER_PARENT).build()));
        assertThrows(NotFoundParcomException.class,() ->  userService.getById(ID_USER_ADMIN));

    }


    @Test
    @WithUserDetails("admin@parcom.com")
    void allInGroup() {
        User admin = User.builder().id(ID_USER_ADMIN).build();
        User parent = User.builder().id(ID_USER_PARENT).build();
        Mockito.when( groupToUserRepository.findMyGroupUsers(ID_GROUP_ONE)).
                thenReturn( Arrays.asList(admin,parent));

        assertEquals(2,  userService.allInGroup().size());
    }

    @Test
    @WithUserDetails("fromAnotherGroup@parcom.com")
    void allInGroupFromAnotherGroup() {
        User admin = User.builder().id(ID_USER_ADMIN).build();
        User parent = User.builder().id(ID_USER_PARENT).build();
        Mockito.when( groupToUserRepository.findMyGroupUsers(ID_GROUP_ONE)).
                thenReturn( Arrays.asList(admin,parent));

        assertEquals(0,  userService.allInGroup().size());
    }

    @Test
    @WithUserDetails("parent@parcom.com")
    void allInGroupAccessDenied() {
        assertThrows(AccessDeniedException.class, () ->  userService.allInGroup());
    }


     @Test
    void create() {
        User user = User.builder().email(EMAIL).build();
        Mockito.when(userRepository.findUserByEmail(EMAIL)).thenReturn(null);
        Mockito.when(userRepository.save(user)).thenReturn(User.builder().id(ID_USER_ADMIN).email(EMAIL).build());
        User userNew = userService.create(EMAIL);

        assertAll(
                () -> Assertions.assertEquals(ID_USER_ADMIN,userNew.getId()),
                () -> Assertions.assertEquals(EMAIL,userNew.getEmail())
        );

    }

    @Test
    void createExistence() {
        Mockito.when(userRepository.findUserByEmail(EMAIL)).thenReturn(User.builder().id(ID_USER_ADMIN).email(EMAIL).build());
        assertThrows(ParcomException.class,() ->  userService.create(EMAIL));
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    void update() {

        UserUpdateDto userUpdateDto = UserUpdateDto.builder().
                firstName(FIRST_NAME).
                middleName(MIDDLE_NAME).
                familyName(FAMILY_NAME).
                phone(PHONE).
                build();

        User user = User.builder().
                firstName(FIRST_NAME).
                middleName(MIDDLE_NAME).
                familyName(FAMILY_NAME).
                phone(PHONE).
                email(EMAIL).
                id(ID_USER_ADMIN).build();

        Mockito.when( groupToUserRepository.findMyGroupUsers(ID_GROUP_ONE)).
                thenReturn(Collections.singletonList(user));

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User updatedUser = userService.update(ID_USER_ADMIN, userUpdateDto);

        assertAll("update",
                () -> assertEquals(FIRST_NAME,updatedUser.getFirstName()),
                () -> assertEquals(MIDDLE_NAME,updatedUser.getMiddleName()),
                () -> assertEquals(FAMILY_NAME,updatedUser.getFamilyName()),
                () -> assertEquals(PHONE,updatedUser.getPhone())
        );


    }


    @Test
    @WithUserDetails("parent@parcom.com")
    void updateForbidden() {

        UserUpdateDto userUpdateDto = UserUpdateDto.builder().
                firstName(FIRST_NAME).
                middleName(MIDDLE_NAME).
                familyName(FAMILY_NAME).
                phone(PHONE).
                build();

        assertThrows(ForbiddenParcomException.class,() ->  userService.update(ID_USER_ADMIN, userUpdateDto));
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    void delete() {
        User user = User.builder().id(ID_USER_ADMIN).build();
        Mockito.when( groupToUserRepository.findMyGroupUsers(ID_GROUP_ONE)).
                thenReturn(Collections.singletonList(user));

        userService.delete(ID_USER_ADMIN);

    }

    @Test
    @WithUserDetails("admin@parcom.com")
    void deleteNotFound() {
        assertThrows(NotFoundParcomException.class,() ->  userService.delete(ID_USER_ADMIN));
    }

    @Test
    @WithUserDetails("parent@parcom.com")
    void deleteNotForbidden() {
        assertThrows(ForbiddenParcomException.class,() ->  userService.delete(ID_USER_ADMIN));
    }


    @Test
    void registerInSecurity() {
        UserCreateDto userCreateDto = UserCreateDto.builder().
                id(ID_USER_ADMIN).build();
        userService.registerInSecurity(userCreateDto);
    }
}
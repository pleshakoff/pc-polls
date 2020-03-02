package com.parcom.polls.model.group;

import com.parcom.polls.SpringSecurityTestConfiguration;
import com.parcom.polls.model.school.School;
import com.parcom.exceptions.NotFoundParcomException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.Arrays;

import static com.parcom.polls.SpringSecurityTestConfiguration.ID_USER_ADMIN;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = {GroupServiceImplTestConfiguration.class,
                           SpringSecurityTestConfiguration.class})
class GroupServiceImplTest {


    private static final long ID_GROUP_ONE = 1L;
    private static final Long ID_GROUP_TWO = 2L;


    @Autowired
    GroupService groupService;

    @MockBean
    GroupRepository groupRepository;

    @MockBean
    GroupToUserRepository groupToUserRepository;

    @Test
    @WithUserDetails("admin@parcom.com")
    void getCurrentGroup() {
        Mockito.when(groupRepository.findById(ID_GROUP_ONE)).thenReturn(of(Group.builder().id(ID_GROUP_ONE).build()));
        Group group = groupService.getCurrentGroup();
        assertEquals(ID_GROUP_ONE, group.getId());
    }

    @Test
    @WithUserDetails("admin@parcom.com")
    void getCurrentGroupNotFound() {
        assertThrows(NotFoundParcomException.class, () -> {
            groupService.getCurrentGroup();
        });
    }

    @Test
    void getCurrentGroupAccessDenied() {
        assertThrows(NotFoundParcomException.class, () -> {
            groupService.getCurrentGroup();
        });
    }


    @Test
    @WithUserDetails("admin@parcom.com")
    void getMyGroups() {
        Mockito.when(groupToUserRepository.findMyGroups(ID_USER_ADMIN)).
                thenReturn(Arrays.asList(Group.builder().id(ID_GROUP_ONE).build(), Group.builder().id(ID_GROUP_TWO).build())
                );
        assertEquals(2, groupService.getMyGroups().size());
   }

    @Test
    void getById() {
        Mockito.when(groupRepository.findById(ID_GROUP_ONE)).thenReturn(of(Group.builder().id(ID_GROUP_ONE).build()));
        Group group = groupService.getById(ID_GROUP_ONE);
        assertEquals(ID_GROUP_ONE, group.getId());
    }

    @Test
    @WithUserDetails("admin@parcom.com")
    void getByIdNotFound() {
        assertThrows(NotFoundParcomException.class, () -> {
            groupService.getById(ID_GROUP_ONE);
        });
    }


    @Test
    void create() {

        String name = "NAME";
        School school = School.builder().name(name).build();
        Group group = Group.builder().name(name).school(school).build();

        Mockito.when(groupRepository.save(group)).thenReturn(group);

        Group groupCreated = groupService.create(name, school);

        assertAll("Creation",
                () -> assertEquals(name,groupCreated.getName()),
                () -> assertEquals(name,groupCreated.getSchool().getName())
        );

    }
}
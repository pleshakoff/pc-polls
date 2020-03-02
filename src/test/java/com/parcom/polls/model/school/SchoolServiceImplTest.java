package com.parcom.polls.model.school;

import com.parcom.exceptions.NotFoundParcomException;
import com.parcom.exceptions.ParcomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static java.util.Optional.*;


@SpringBootTest(classes = {SchoolServiceImplTestConfiguration.class})
class SchoolServiceImplTest {


    private static final long ID_SCHOOL = 1L;
    private static final String NAME = "school";

    @Autowired
    SchoolService schoolService;

    @MockBean
    SchoolRepository schoolRepository;

    @Test
    void getOreCreateExist() {

        School school = School.builder().id(ID_SCHOOL).name(NAME).build();
        Mockito.when(schoolRepository.findById(ID_SCHOOL)).thenReturn(of(school));

        School foundedSchool = schoolService.getOrCreate(ID_SCHOOL, NAME);

        Assertions.assertAll(
              () ->  Assertions.assertEquals(ID_SCHOOL,foundedSchool.getId()),
              () ->  Assertions.assertEquals(NAME,foundedSchool.getName())
        );
    }

    @Test
    void getOreCreateExistNotFound() {
        Assertions.assertThrows(NotFoundParcomException.class,
                () ->  schoolService.getOrCreate(ID_SCHOOL, NAME)
        );
    }


    @Test
    void getOreCreateNew() {

        Mockito.when(schoolRepository.save(School.builder().name(NAME).build())).thenReturn(School.builder().id(ID_SCHOOL).name(NAME).build());

        School foundedSchool = schoolService.getOrCreate(null, NAME);

        Assertions.assertAll(
                () ->  Assertions.assertEquals(ID_SCHOOL,foundedSchool.getId()),
                () ->  Assertions.assertEquals(NAME,foundedSchool.getName())
        );
    }


    @Test
    void getOreCreateError() {

        Assertions.assertThrows(ParcomException.class,
                () ->  schoolService.getOrCreate(null, null)
        );
    }





}
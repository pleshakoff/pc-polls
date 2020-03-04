package com.parcom.polls.model.student;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
public class Student {

    private final Long id;

    private final String firstName;
    private final String middleName;

    private final String familyName;

}

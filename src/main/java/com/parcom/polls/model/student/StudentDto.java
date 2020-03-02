package com.parcom.polls.model.student;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;


@RequiredArgsConstructor
@Getter
public class StudentDto {

    @NotNull
    private  final Long idPoll;
    @NotNull
    private  final Long idStudent;



}

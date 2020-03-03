package com.parcom.polls.model.voter;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;


@RequiredArgsConstructor
@Getter
public class VoterDto {

    @NotNull
    private  final Long idPoll;
    @NotNull
    private  final Long idStudent;



}

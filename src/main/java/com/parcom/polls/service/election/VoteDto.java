package com.parcom.polls.service.election;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class VoteDto {

    private final Long idVariant;

    @JsonCreator
    public VoteDto(Long idVariant) {
        this.idVariant = idVariant;
    }
}

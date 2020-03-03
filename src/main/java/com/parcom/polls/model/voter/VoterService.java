package com.parcom.polls.model.voter;

import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface VoterService {


    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    List<Voter> all(Long idPoll);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    Voter create(VoterDto voterDto);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    void delete(Long id);
}

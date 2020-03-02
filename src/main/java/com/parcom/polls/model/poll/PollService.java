package com.parcom.polls.model.poll;

import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface PollService {

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    Poll create(PollDto pollDto);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    Poll update(Long id, PollDto pollDto);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    void delete(Long id);

    List<Poll> all(PollState pollState);

    Poll get(Long id);

    Poll changeState(Long id, PollState pollState);
}

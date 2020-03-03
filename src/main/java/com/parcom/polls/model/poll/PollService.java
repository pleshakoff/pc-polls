package com.parcom.polls.model.poll;

import org.springframework.security.access.annotation.Secured;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface PollService {

    Poll get(Long id);

    List<Poll> all(PollState pollState);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    Poll create(PollDto pollDto);

    void checkLocked(@NotNull Long idPoll);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    Poll update(Long id, PollDto pollDto);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    void delete(Long id);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    Poll changeState(Long id, PollState pollState);



}

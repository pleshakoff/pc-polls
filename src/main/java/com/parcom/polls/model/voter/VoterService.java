package com.parcom.polls.model.voter;

import com.parcom.polls.model.poll.Poll;
import com.parcom.polls.model.variant.Variant;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface VoterService {


    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    List<Voter> all(Long idPoll);

    List<Voter> allByVariant(Long idVariant,
                             Long idPoll);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    Voter create(VoterDto voterDto);

    void createForAllGroup(Poll poll);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    void delete(Long id);

      Voter vote(Variant variant);
}

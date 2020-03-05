package com.parcom.polls.service.sync;

import com.parcom.polls.model.poll.Poll;
import com.parcom.polls.model.variant.Variant;
import com.parcom.polls.model.voter.Voter;
import com.parcom.polls.model.voter.VoterDto;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface SyncService {



    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    void synchronize();
}

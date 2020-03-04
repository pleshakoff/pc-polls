package com.parcom.polls.service.election;

import com.parcom.polls.model.voter.Voter;

import java.util.List;

public interface ElectionService {

    Voter vote(VoteDto voterDto);

    List<VoteResult> result(Long idPool);
}

package com.parcom.polls.service.election;


import com.parcom.polls.model.variant.Variant;
import com.parcom.polls.model.voter.Voter;
import lombok.Getter;

import java.util.List;


@Getter
public class VoteResult  {
    private final Long id;

    private  final String description;
    private  final Integer num;
    private  final Integer count;
    private final List<Voter> voters;

    public VoteResult(Variant variant,List<Voter> voters) {
        this.id = variant.getId();
        this.description = variant.getDescription();
        this.num = variant.getNum();
        this.count = voters.size();
        this.voters = voters;
    }

}

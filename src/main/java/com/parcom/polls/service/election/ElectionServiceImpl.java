package com.parcom.polls.service.election;

import com.parcom.polls.model.poll.PollCommon;
import com.parcom.polls.model.variant.Variant;
import com.parcom.polls.model.variant.VariantService;
import com.parcom.polls.model.voter.Voter;
import com.parcom.polls.model.voter.VoterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElectionServiceImpl implements ElectionService {

    private final PollCommon pollCommon;
    private final VariantService variantService;
    private final VoterService voterService;


    @Override
    public Voter vote(VoteDto voteDto) {
        Variant variant = variantService.getVariantById(voteDto.getIdVariant());
        pollCommon.checkActive(variant.getPoll());
        return voterService.vote(variant);
    }

    @Override
    public List<VoteResult> result(Long idPool) {
        List<VoteResult> results = variantService.all(idPool).stream().map(variant ->

                                                                           {
                                                                               List<Voter> voters = voterService.allByVariant(
                                                                                       variant.getId(), idPool);

                                                                               return new VoteResult(variant, voters);
                                                                           }


        ).collect(Collectors.toList());

        List<Voter> voters = voterService.allByVariant(
                null, idPool);


        return results;
    }


}

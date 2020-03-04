package com.parcom.polls.model.poll;

import com.parcom.exceptions.NotFoundParcomException;
import com.parcom.exceptions.ParcomException;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
public class PollCommonImpl implements PollCommon {

    private final PollRepository pollRepository;

    @Override
    public Poll getById(@NotNull Long idPoll) {
        return pollRepository.findByIdAndIdGroup(idPoll, UserUtils.getIdGroup()).orElseThrow(
                () -> new NotFoundParcomException("poll.not_found"));
    }

    @Override
    public void checkLocked(@NotNull Poll poll) {
        if (!poll.getPollState().equals(PollState.DRAFT)) {
            throw new ParcomException("poll.locked");
        }
    }

    @Override
    public void checkActive(@NotNull Poll poll) {
        if (!poll.getPollState().equals(PollState.ACTIVE)) {
            throw new ParcomException("poll.not_active");
        }
    }


}
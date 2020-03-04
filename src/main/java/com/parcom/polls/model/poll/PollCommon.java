package com.parcom.polls.model.poll;

import javax.validation.constraints.NotNull;

public interface PollCommon {
    Poll getById(@NotNull Long idPoll);

    void checkLocked(@NotNull Poll poll);

    void checkActive(@NotNull Poll poll);
}

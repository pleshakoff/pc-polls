package com.parcom.polls.model.poll;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@RequiredArgsConstructor
class PollDto {

    @NotNull
    private final String name;
    @NotNull
    private final String description;
    private final LocalDateTime expiration;
}

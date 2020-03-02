package com.parcom.polls.model.variant;


import lombok.*;

import javax.validation.constraints.NotNull;


@RequiredArgsConstructor
@Getter
public class VariantDto {

    @NotNull
    private  final Long idPoll;
    @NotNull
    private  final String description;
    @NotNull
    private  final Integer num;



}

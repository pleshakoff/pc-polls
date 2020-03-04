package com.parcom.polls.model.variant;

import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface VariantService {

    List<Variant> all(Long idPoll);


    Variant create(VariantDto variantDto);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    Variant update(Long id, VariantDto variantDto);

    Variant getVariantById(Long id);

    @Secured({"ROLE_ADMIN","ROLE_MEMBER"})
    void delete(Long id);
}

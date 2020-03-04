package com.parcom.polls.model.variant;

import com.parcom.exceptions.NotFoundParcomException;
import com.parcom.polls.model.poll.Poll;
import com.parcom.polls.model.poll.PollCommon;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class VariantServiceImpl implements VariantService {

    private final VariantRepository variantRepository;
    private final PollCommon pollCommon;

    @Override
    public List<Variant> all(Long idPoll) {
        return variantRepository.findByIdPoll(idPoll,UserUtils.getIdGroup());
    }



    @Override
    public Variant create(VariantDto variantDto) {
        Poll poll = pollCommon.getById(variantDto.getIdPoll());
        pollCommon.checkLocked(poll);
        Variant variant = Variant.builder().poll(poll).
                num(variantDto.getNum()).
                description(variantDto.getDescription()).build();
        return variantRepository.save(variant);
    }

    @Override
    public Variant update(Long id, VariantDto variantDto) {
        Poll poll = pollCommon.getById(variantDto.getIdPoll());
        pollCommon.checkLocked(poll);
        Variant variant = getVariantById(id);
        variant.setDescription(variantDto.getDescription());
        variant.setNum(variantDto.getNum());
        return variant;
    }

    @Override
    public Variant getVariantById(Long id) {
        return variantRepository.findByIdAndGroup(id,UserUtils.getIdGroup()).orElseThrow(() -> new NotFoundParcomException("variant.not_found"));
    }

    @Override
    public void delete(Long id) {
        pollCommon.checkLocked(getVariantById(id).getPoll());
        variantRepository.delete(getVariantById(id));
    }
}

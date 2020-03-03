package com.parcom.polls.model.variant;

import com.parcom.exceptions.NotFoundParcomException;
import com.parcom.polls.model.poll.Poll;
import com.parcom.polls.model.poll.PollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class VariantServiceImpl implements VariantService {

    private final VariantRepository variantRepository;
    private final PollService pollService;

    @Override
    public List<Variant> all(Long idPoll) {
        return null;
    }

    @Override
    public Variant create(VariantDto variantDto) {
        Poll poll = pollService.getById(variantDto.getIdPoll());
        pollService.checkLocked(poll);
        Variant variant = Variant.builder().poll(poll).
                num(variantDto.getNum()).
                description(variantDto.getDescription()).build();
        return variantRepository.save(variant);
    }

    @Override
    public Variant update(Long id, VariantDto variantDto) {
        Poll poll = pollService.getById(variantDto.getIdPoll());
        pollService.checkLocked(poll);
        Variant variant = getVariantById(id);
        variant.setDescription(variantDto.getDescription());
        variant.setNum(variantDto.getNum());
        return variant;
    }

    private Variant getVariantById(Long id) {
        return variantRepository.findById(id).orElseThrow(() -> new NotFoundParcomException("variant.not_found"));
    }

    @Override
    public void delete(Long id) {
        pollService.checkLocked(getVariantById(id).getPoll());
        variantRepository.deleteById(id);
    }
}

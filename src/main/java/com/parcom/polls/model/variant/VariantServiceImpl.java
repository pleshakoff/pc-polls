package com.parcom.polls.model.variant;

import com.parcom.exceptions.NotFoundParcomException;
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
        Variant.builder().num(variantDto.getNum()).
                          description(variantDto/)
        return null;
    }
    @Override
    public Variant update(Long id, VariantDto variantDto) {
        Variant variant = variantRepository.findById(id).orElseThrow(() -> new NotFoundParcomException("variant.not_found"));
        variant.setDescription(variantDto.getDescription());
        variant.setNum(variantDto.getNum());
        return variant;
    }

    @Override
    public void delete(Long id) {

    }
}

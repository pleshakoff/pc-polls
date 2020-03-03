package com.parcom.polls.model.voter;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class VoterServiceImpl implements VoterService {

    @Override
    public List<Voter> all(Long idPoll) {
        return null;
    }

    @Override
    public Voter create(VoterDto voterDto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

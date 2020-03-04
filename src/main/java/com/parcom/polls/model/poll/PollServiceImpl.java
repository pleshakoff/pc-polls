package com.parcom.polls.model.poll;

import com.parcom.exceptions.ForbiddenParcomException;
import com.parcom.polls.model.voter.VoterService;
import com.parcom.security_client.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
class PollServiceImpl implements PollService {

    private final PollRepository pollRepository;
    private final PollCommon pollCommon;
    private final VoterService voterService;

    @Override
    public Poll create(PollDto pollDto) {
        Poll poll = Poll.builder().idGroup(UserUtils.getIdGroup()).
                name(pollDto.getName()).
                description(pollDto.getDescription()).
                expiration(pollDto.getExpiration()).
                idUser(UserUtils.getIdUser()).
                pollState(PollState.DRAFT).
                creation(LocalDateTime.now()).
                pollType(pollDto.getPollType()).
                build();
        Poll newPoll = pollRepository.save(poll);

        if (poll.getPollType().equals(PollType.GROUP)) {
            voterService.createForAllGroup(newPoll);
        }

        return newPoll;
    }

    @Override
    public Poll getById(@NotNull Long idPoll) {
        return pollCommon.getById(idPoll);
    }



    @Override
    public void checkLocked(@NotNull Poll poll) {
        pollCommon.checkLocked(poll);
    }


    @Override
    public Poll update(@NotNull Long id, PollDto pollDto) {
        Poll poll = pollCommon.getById(id);
        pollCommon.checkLocked(poll);
        poll.setName(pollDto.getName());
        poll.setDescription(pollDto.getDescription());
        poll.setExpiration(pollDto.getExpiration());
        pollRepository.save(poll);
        return poll;
    }

    @Override
    public void delete(Long id) {
        pollCommon.checkLocked(pollCommon.getById(id));
        pollRepository.deleteById(id);
    }

    @Override
    public List<Poll> all(PollState pollState) {
        if (UserUtils.getRole().equals(UserUtils.ROLE_PARENT) && pollState.equals(PollState.DRAFT)) {
            throw new  ForbiddenParcomException();
        }
        else
            return  pollRepository.findByIdGroupAndPollStateOrderByCreationDesc(UserUtils.getIdGroup(),pollState);
    }

    @Override
    public Poll get(Long id) {
        Poll poll = pollCommon.getById(id);
        if (UserUtils.getRole().equals(UserUtils.ROLE_PARENT) && poll.getPollState().equals(PollState.DRAFT)) {
            throw new  ForbiddenParcomException();
        }
        else
            return  poll;
    }

    @Override
    public Poll changeState(Long id, PollState pollState) {
        Poll poll = pollCommon.getById(id);
        poll.setPollState(pollState);
        pollRepository.save(poll);
        return poll;
    }

    @Override
    public List<Poll> allMy() {
        return pollRepository.findMy(UserUtils.getIdStudent());
    }


}

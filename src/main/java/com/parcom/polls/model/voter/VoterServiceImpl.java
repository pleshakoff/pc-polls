package com.parcom.polls.model.voter;

import com.parcom.exceptions.NotFoundParcomException;
import com.parcom.exceptions.ParcomException;
import com.parcom.polls.model.poll.Poll;
import com.parcom.polls.model.poll.PollCommon;
import com.parcom.polls.model.student.Student;
import com.parcom.polls.model.student.StudentService;
import com.parcom.polls.model.variant.Variant;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class VoterServiceImpl implements VoterService {

    private static final String VOTER_NOT_FOUND = "voter.not_found";
    private final StudentService studentService;
    private final PollCommon pollCommon;
    private final VoterRepository voterRepository;


    @Override
    public List<Voter> all(Long idPoll) {
        return voterRepository.findByIdPoll(idPoll,UserUtils.getIdGroup());
    }

    @Override
    public List<Voter> allByVariant(Long idVariant,
                                    Long idPoll)
    {
        return voterRepository.findByVariant(idVariant,idPoll);
    }


    private Voter getVoterById(Long id) {
        return voterRepository.findByIdAndGroup(id,UserUtils.getIdGroup()).orElseThrow(() -> new NotFoundParcomException(
                VOTER_NOT_FOUND));
    }


    @Override
    public Voter create(VoterDto voterDto) {
        Poll poll = pollCommon.getById(voterDto.getIdPoll());
        pollCommon.checkLocked(poll);
        Student student = studentService.getStudentById(voterDto.getIdStudent());
        if (voterRepository.findByStudent(student.getId(), poll.getId()).isPresent()) {
            throw new ParcomException("voter.already_in_poll");
        }
        return createFromStudent(poll, student);
    }

    private Voter createFromStudent(Poll poll,
                                    Student student) {
        Voter voter = Voter.builder().familyName(student.getFamilyName()).
                firstName(student.getFirstName()).
                middleName(student.getMiddleName()).
                idStudent(student.getId()).
                poll(poll).build();

        return voterRepository.save(voter);
    }

    @Override
    public void createForAllGroup(Poll poll) {
        studentService.getGroupStudents().forEach(student ->
                                                         createFromStudent(poll, student)

        );
    }


    @Override
    public void delete(Long id) {
        pollCommon.checkLocked(getVoterById(id).getPoll());
        voterRepository.deleteById(id);

    }

    @Override
    public Voter vote(Variant variant)
    {
        Voter voter = voterRepository.findByStudent(UserUtils.getIdStudent(), variant.getPoll().getId()).orElseThrow(
                () -> new NotFoundParcomException(
                        VOTER_NOT_FOUND));
        voter.setVariant(variant);
        return voterRepository.save(voter);
    }




}

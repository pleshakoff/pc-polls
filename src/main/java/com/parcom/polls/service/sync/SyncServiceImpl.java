package com.parcom.polls.service.sync;

import com.parcom.exceptions.NotFoundParcomException;
import com.parcom.exceptions.ParcomException;
import com.parcom.polls.model.poll.Poll;
import com.parcom.polls.model.poll.PollCommon;
import com.parcom.polls.model.poll.PollService;
import com.parcom.polls.model.poll.PollState;
import com.parcom.polls.model.student.Student;
import com.parcom.polls.model.student.StudentService;
import com.parcom.polls.model.variant.Variant;
import com.parcom.polls.model.voter.Voter;
import com.parcom.polls.model.voter.VoterDto;
import com.parcom.polls.model.voter.VoterService;
import com.parcom.security_client.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
class SyncServiceImpl implements SyncService {

    private final StudentService studentService;
    private final PollService pollService;
    private final VoterService voterService;

    @Override
    public void synchronize() {

        List<Student> students = studentService.getGroupStudents();
        List<Poll> polls = pollService.all(PollState.DRAFT);
        polls.addAll(pollService.all(PollState.ACTIVE));

        for (Poll poll : polls) {
            List<Voter> voters = voterService.all(poll.getId());
            voters.stream().filter(voter -> students.stream().noneMatch(student -> student.getId().equals(voter.getIdStudent()))).
                    map(Voter::getId).forEach(voterService::deleteUnchecked);
            for (Student student : students) {
                Voter voter = voters.stream().filter(v -> v.getIdStudent().equals(student.getId())).findFirst().
                        orElseGet(() -> voterService.createFromStudent(poll,student));
                voterService.updateFromStudent(voter.getId(),student);
            }


        }

    }


}

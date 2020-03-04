package com.parcom.polls.model.poll;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


interface PollRepository extends CrudRepository<Poll, Long> {

 List<Poll> findByIdGroupAndPollStateOrderByCreationDesc(@Param("idGroup") Long idGroup,@Param("pollState") PollState pollState);

 Optional<Poll> findByIdAndIdGroup(@Param("id") Long id, @Param("idGroup") Long idGroup);


@Query("select v.poll from Voter v " +
       "where v.idStudent = :idStudent " +
        " and v.poll.pollState = 1")
 List<Poll> findMy(@Param("idStudent") Long idStudent);


}

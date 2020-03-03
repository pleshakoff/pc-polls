package com.parcom.polls.model.poll;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


interface PollRepository extends CrudRepository<Poll, Long> {

 List<Poll> findByIdGroupAndPollStateOrderByCreationDesc(@Param("idGroup") Long idGroup,@Param("pollState") PollState pollState);



}

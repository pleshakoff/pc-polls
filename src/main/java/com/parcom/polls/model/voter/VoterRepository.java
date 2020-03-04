package com.parcom.polls.model.voter;

import com.parcom.polls.model.variant.Variant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


interface VoterRepository extends CrudRepository<Voter, Long> {


    @Query("select v from Voter v " +
            "where v.poll.id = :idPoll " +
            " and v.poll.idGroup = :idGroup " +
            " order by v.familyName  ")
    List<Voter> findByIdPoll(@Param("idPoll") Long idPoll,
                             @Param("idGroup") Long idGroup);


    @Query("select v from Voter v " +
            "where v.id = :id " +
            " and v.poll.idGroup = :idGroup ")
    Optional<Voter> findByIdAndGroup(@Param("id") Long id,
                                     @Param("idGroup") Long idGroup);


    @Query("select v from Voter v " +
           "where v.idStudent = :idStudent " +
            "and v.poll.id = :idPoll ")
    Optional<Voter> findByStudent(@Param("idStudent") Long idStudent,
                                  @Param("idPoll") Long idPoll);


    @Query("select v from Voter v " +
           "where v.variant.id = :idVariant " +
           "and v.poll.id = :idPoll ")
    List<Voter> findByVariant(@Param("idVariant") Long idVariant,
                                  @Param("idPoll") Long idPoll);

}

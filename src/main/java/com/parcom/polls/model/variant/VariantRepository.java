package com.parcom.polls.model.variant;

import com.parcom.polls.model.poll.Poll;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


interface VariantRepository extends CrudRepository<Variant, Long> {




    @Query("select v from Variant v " +
            "where v.id = :id " +
            " and v.poll.idGroup = :idGroup ")
    Optional<Variant> findByIdAndGroup ( @Param("id") Long id,@Param("idGroup") Long idGroup);


    @Query("select v from Variant v " +
            "where v.poll.id = :idPoll " +
            " and v.poll.idGroup = :idGroup " +
            " order by num  ")
    List<Variant> findByIdPoll(@Param("idPoll") Long idPoll,@Param("idGroup") Long idGroup);

}

package com.parcom.polls.model.variant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parcom.polls.model.poll.Poll;
import com.parcom.polls.model.student.Student;
import com.parcom.polls.model.voter.Voter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Variant {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private  String description;
    private  Integer num;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_poll", referencedColumnName = "id", nullable = false)
    private Poll poll;


}

package com.parcom.polls.model.voter;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parcom.polls.model.poll.Poll;
import com.parcom.polls.model.variant.Variant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voter {

    @Id
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String middleName;

    @Column(nullable = false)
    private String familyName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_poll", referencedColumnName = "id", nullable = false)
    private Poll poll;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_variant", referencedColumnName = "id", nullable = true)
    private Variant variant;


}

package com.parcom.polls.model.poll;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private  Long idGroup;
    @Column
    private String name;
    @Column
    private  String description;
    @Column
    private PollState pollState;
    @Column
    private LocalDateTime creation;
    @Column
    private LocalDateTime expiration;
    @Column
    private Long  idUser;
    @Column
    private PollType pollType;


}

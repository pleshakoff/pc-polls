package com.parcom.polls.model.poll;


import com.parcom.polls.model.student.Student;
import com.parcom.polls.model.variant.Variant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

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
    private LocalDateTime expiration;
    @Column
    private Long  idUser;

    @OneToMany(mappedBy = "id_poll", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Variant> variants;

    @OneToMany(mappedBy = "id_poll", cascade = CascadeType.REMOVE, orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Student> students;


}

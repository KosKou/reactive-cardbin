package com.demo.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data  //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "cardbins")
public class Cardbin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private int binNumber;
    @NotEmpty
    @NonNull
    private String binType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cardbin")
    private List<Attribute> attributes;

    @NotEmpty
    private String state;
}

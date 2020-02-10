package com.demo.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data  //@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "cardbin")
public class Cardbin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private int binNumber;
    @NotEmpty
//    @NonNull
    private String binType;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cardbin", fetch = FetchType.LAZY)
    private List<Attribute> attributes;

    @NotEmpty
    private String state;
}

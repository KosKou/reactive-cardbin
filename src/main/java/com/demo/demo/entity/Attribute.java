package com.demo.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity(name = "attributes")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @NonNull
    private String key;
    @NotEmpty
    @NonNull
    private String value;
    @NotEmpty
    @Setter
    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cardbin cardbin;

}

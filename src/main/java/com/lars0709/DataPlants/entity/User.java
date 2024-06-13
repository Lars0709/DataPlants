package com.lars0709.DataPlants.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // other fields like password, email, etc.

    @OneToMany(mappedBy = "user")
    private List<Plant> plants;
}
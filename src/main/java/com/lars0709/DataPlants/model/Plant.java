package com.lars0709.DataPlants.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "plant")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plant {

    public Plant(LocalDate startDate){
        this.startDate = startDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "strain_id", nullable = false)
    private Strain strain;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    // Other fields and relationships...
}
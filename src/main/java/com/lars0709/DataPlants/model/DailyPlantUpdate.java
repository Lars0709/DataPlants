package com.lars0709.DataPlants.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "daily_plant_update")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyPlantUpdate {

    public DailyPlantUpdate (Plant plant, LocalDate entryDate) {
        this.plant = plant;
        this.entryDate = entryDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;


    // Other fields and relationships...
}

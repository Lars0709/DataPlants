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

    public Plant(LocalDate startOfGerminationStage, LocalDate startOfSeedlingStage,
                 LocalDate startOfVegetativeStage, LocalDate startOfFloweringStage, LocalDate harvestDate,
                 LocalDate startDryingDate, LocalDate startCuringDate, Integer harvestWeight){
        this.startOfGerminationStage = startOfGerminationStage;
        this.startOfSeedlingStage = startOfSeedlingStage;
        this.startOfVegetativeStage = startOfVegetativeStage;
        this.startOfFloweringStage = startOfFloweringStage;
        this.harvestDate = harvestDate;
        this.startDryingDate = startDryingDate;
        this.startCuringDate = startCuringDate;
        this.harvestWeight = harvestWeight;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "strain_id", nullable = false)
    private Strain strain;

    @Column(name = "start_of_germination_stage", nullable = false)
    private LocalDate startOfGerminationStage;

    @Column(name = "start_of_seedling_stage")
    private LocalDate startOfSeedlingStage;

    @Column(name = "start_of_vegetative_stage")
    private LocalDate startOfVegetativeStage;

    @Column(name = "start_of_flowering_stage")
    private LocalDate startOfFloweringStage;

    @Column(name = "harvest_date")
    private LocalDate harvestDate;

    @Column(name = "start_drying_date")
    private LocalDate startDryingDate;

    @Column(name = "start_curing_date")
    private LocalDate startCuringDate;

    @Column(name = "harvest_weight")
    private Integer harvestWeight;

    // Other fields and relationships...
}
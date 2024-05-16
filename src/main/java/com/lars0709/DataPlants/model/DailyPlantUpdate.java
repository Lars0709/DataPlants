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

    public DailyPlantUpdate(Plant plant, LocalDate entryDate, int week, int day, String water, String nutrients,
                            String stage, String comment, String problem, byte[] imageData) {
        this.plant = plant;
        this.entryDate = entryDate;
        this.week = week;
        this.day = day;
        this.water = water;
        this.nutrients = nutrients;
        this.stage = stage;
        this.comment = comment;
        this.problem = problem;
        this.imageData = imageData;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "week", nullable = false)
    private int week;

    @Column(name = "day", nullable = false)
    private int day;

    @Column(name = "water", nullable = false)
    private String water;

    @Column(name = "nutrients", nullable = false)
    private String nutrients;

    @Column(name = "stage", nullable = false)
    private String stage;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "problem", nullable = false)
    private String problem;

    // Define field to store image data
    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    // Other fields and relationships...
}

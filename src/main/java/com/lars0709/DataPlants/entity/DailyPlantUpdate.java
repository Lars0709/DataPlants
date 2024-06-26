package com.lars0709.DataPlants.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "daily_plant_update")
@Getter
@Setter
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

    @Column(name = "week")
    private int week;

    @Column(name = "day")
    private int day;

    @Column(name = "water")
    private String water;

    @Column(name = "nutrients")
    private String nutrients;

    @Column(name = "stage")
    private String stage;

    @Column(name = "comment")
    private String comment;

    @Column(name = "problem")
    private String problem;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;
}

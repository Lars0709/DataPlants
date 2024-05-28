package com.lars0709.DataPlants.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "strain")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Strain {

    public Strain(String name, String description, List<String> aliases, String parent_plant_one,String parent_plant_two,
                  String parent_plant_three, String growType, String strainType, Double thcLevel, String cbdLevel,
                  Integer sativaLevel, Integer indicaLevel, Integer ruderalisLevel, List<String> feelings,
                  List<String> tastes, String seedOrigin, Integer pricePerSeed, Integer floweringPhaseMin,
                  Integer floweringPhaseMax, Integer seedToHarvestMin, Integer seedToHarvestMax, Integer indoorYieldMin,
                  Integer indoorYieldMax, byte[] imageData) {

        this.name = name;
        this.description = description;
        this.aliases = aliases;
        this.parent_plant_one = parent_plant_one;
        this.parent_plant_two = parent_plant_two;
        this.parent_plant_three = parent_plant_three;
        this.growType = growType;
        this.strainType = strainType;
        this.thcLevel = thcLevel;
        this.cbdLevel = cbdLevel;
        this.sativaLevel = sativaLevel;
        this.indicaLevel = indicaLevel;
        this.ruderalisLevel = ruderalisLevel;
        this.feelings = feelings;
        this.tastes = tastes;
        this.seedOrigin = seedOrigin;
        this.pricePerSeed = pricePerSeed;
        this.floweringPhaseMin = floweringPhaseMin;
        this.floweringPhaseMax = floweringPhaseMax;
        this.seedToHarvestMin = seedToHarvestMin;
        this.seedToHarvestMax = seedToHarvestMax;
        this.indoorYieldMin = indoorYieldMin;
        this.indoorYieldMax = indoorYieldMax;
        this.imageData = imageData;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name; // Name of Strain

    @Column(name = "description", length = 2000)
    private String description; // description of Strain

    @Column(name = "aliases")
    private List<String> aliases; // Other names

    @Column(name = "parent_plant_one")
    private String parent_plant_one;

    @Column(name = "parent_plant_two")
    private String parent_plant_two;

    @Column(name = "parent_plant_three")
    private String parent_plant_three;

    @Column(name = "grow_type", nullable = false)
    private String growType; // Auto or Photoperiod

    @Column(name = "strain_type", nullable = false)
    private String strainType; // Sativa, Indica or Hybrid

    @Column(name = "thc_level", nullable = false)
    private Double thcLevel; // level of thc as percentage

    @Column(name = "cbd_level")
    private String cbdLevel; // level of cbd as percentage

    @Column(name = "sativa_level")
    private Integer sativaLevel; // level of sativa as percentage

    @Column(name = "indica_level")
    private Integer indicaLevel; // level of indica as percentage

    @Column(name = "ruderalis_level")
    private Integer ruderalisLevel; // level of ruderalis as percentage

    @Column(name = "feelings")
    private List<String> feelings; // list of feeling associated with the strain

    @Column(name = "tastes")
    private List<String> tastes; // list of feeling associated with the strain

    @Column(name = "seed_origin", nullable = false)
    private String seedOrigin;// website name or link

    @Column(name = "price_per_seed", nullable = false)
    private Integer pricePerSeed; //

    @Column(name = "flowering_phase_min")
    private Integer floweringPhaseMin; //

    @Column(name = "flowering_phase_max")
    private Integer floweringPhaseMax; //

    @Column(name = "seed_to_harvest_min")
    private Integer seedToHarvestMin;

    @Column(name = "seed_to_harvest_max")
    private Integer seedToHarvestMax;

    @Column(name = "indoor_yield_min")
    private Integer indoorYieldMin; //

    @Column(name = "indoor_yield_max")
    private Integer indoorYieldMax; //

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

}

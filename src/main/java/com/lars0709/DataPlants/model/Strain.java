package com.lars0709.DataPlants.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "strain")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Strain {

    public Strain(String name, List<String> aliases, String growType, String strainType, int thcLevel, int cbdLevel,
                  int cbgLevel, int sativaLevel, int indicaLevel, int ruderalisLevel, List<String> feelings,
                  String seedOrigin, int pricePerSeed, int[] floweringPhase, int[] seedToHarvest, int[] indoorYield) {
        this.name = name;
        this.aliases = aliases;
        this.growType = growType;
        this.strainType = strainType;
        this.thcLevel = thcLevel;
        this.cbdLevel = cbdLevel;
        this.cbgLevel = cbgLevel;
        this.sativaLevel = sativaLevel;
        this.indicaLevel = indicaLevel;
        this.ruderalisLevel = ruderalisLevel;
        this.feelings = feelings;
        this.seedOrigin = seedOrigin;
        this.pricePerSeed = pricePerSeed;
        this.floweringPhase = floweringPhase;
        this.seedToHarvest = seedToHarvest;
        this.indoorYield = indoorYield;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name; // Name of Strain

    @Column(name = "aliases", nullable = false)
    private List<String> aliases; // Other names

    @Column(name = "grow_type", nullable = false)
    private String growType; // Auto or Photoperiod

    @Column(name = "strain_type", nullable = false)
    private String strainType; // Sativa, Indica or Hybrid

    @Column(name = "thc_level", nullable = false)
    private int thcLevel; // level of thc as percentage

    @Column(name = "cbd_level")
    private int cbdLevel; // level of cbd as percentage

    @Column(name = "cbg_level")
    private int cbgLevel; // level of cbg as percentage

    @Column(name = "sativa_level")
    private int sativaLevel; // level of sativa as percentage

    @Column(name = "indica_level")
    private int indicaLevel; // level of indica as percentage

    @Column(name = "ruderalis_level")
    private int ruderalisLevel; // level of ruderalis as percentage

    @Column(name = "feelings")
    private List<String> feelings; // list of feeling associated with the strain

    @Column(name = "seed_origin", nullable = false)
    private String seedOrigin; ; // website name or link

    @Column(name = "price_per_seed", nullable = false)
    private int pricePerSeed; //

    @Column(name = "flowering_phase", nullable = false)
    private int[] floweringPhase; //

    @Column(name = "seed_to_harvest", nullable = false)
    private int[] seedToHarvest; ; //

    @Column(name = "indoor_yield", nullable = false)
    private int[] indoorYield; //





}

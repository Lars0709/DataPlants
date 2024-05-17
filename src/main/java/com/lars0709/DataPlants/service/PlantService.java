package com.lars0709.DataPlants.service;

import com.lars0709.DataPlants.model.Plant;
import com.lars0709.DataPlants.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantService {

    private final PlantRepository plantRepository;

    @Autowired
    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    public void savePlant(Plant plant) {
        plantRepository.save(plant);
    }
}

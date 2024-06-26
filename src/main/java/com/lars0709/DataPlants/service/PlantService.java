package com.lars0709.DataPlants.service;

import com.lars0709.DataPlants.entity.Plant;
import com.lars0709.DataPlants.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Plant> getPlantById(Long id) {
        return plantRepository.findById(id);
    }

    public void deletePlantById(Long id) {
        plantRepository.deleteById(id);
    }



}

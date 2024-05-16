package com.lars0709.DataPlants.repository;

import com.lars0709.DataPlants.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}

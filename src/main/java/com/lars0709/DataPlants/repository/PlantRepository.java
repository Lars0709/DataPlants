package com.lars0709.DataPlants.repository;

import com.lars0709.DataPlants.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findAllByStatus(boolean status);
}

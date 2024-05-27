package com.lars0709.DataPlants.repository;

import com.lars0709.DataPlants.model.DailyPlantUpdate;
import com.lars0709.DataPlants.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DailyPlantUpdateRepository extends JpaRepository<DailyPlantUpdate, Long>, JpaSpecificationExecutor<DailyPlantUpdate> {
    List<DailyPlantUpdate> findByPlant(Plant plant);
}
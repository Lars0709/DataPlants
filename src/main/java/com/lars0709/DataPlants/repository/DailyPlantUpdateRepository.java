package com.lars0709.DataPlants.repository;

import com.lars0709.DataPlants.model.DailyPlantUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyPlantUpdateRepository extends JpaRepository<DailyPlantUpdate, Long> {
}

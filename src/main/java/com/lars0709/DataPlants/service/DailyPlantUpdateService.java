package com.lars0709.DataPlants.service;

import com.lars0709.DataPlants.model.DailyPlantUpdate;
import com.lars0709.DataPlants.repository.DailyPlantUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DailyPlantUpdateService {

    private final DailyPlantUpdateRepository dailyPlantUpdateRepository;

    public void save(DailyPlantUpdate dailyPlantUpdate) {
        dailyPlantUpdateRepository.save(dailyPlantUpdate);
    }

    @Autowired
    public DailyPlantUpdateService(DailyPlantUpdateRepository dailyPlantUpdateRepository) {
        this.dailyPlantUpdateRepository = dailyPlantUpdateRepository;
    }

    public Optional<DailyPlantUpdate> getDailyPlantUpdateById(Long id) {
        return dailyPlantUpdateRepository.findById(id);
    }

    public void deleteDailyPlantUpdateById(Long id) {
        dailyPlantUpdateRepository.deleteById(id);
    }
}

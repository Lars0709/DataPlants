package com.lars0709.DataPlants.service;

import com.lars0709.DataPlants.model.DailyPlantUpdate;
import com.lars0709.DataPlants.repository.DailyPlantUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DailyPlantUpdateService {

    private final DailyPlantUpdateRepository dailyPlantUpdateRepository;


    @Autowired
    public DailyPlantUpdateService(DailyPlantUpdateRepository dailyPlantUpdateRepository) {
        this.dailyPlantUpdateRepository = dailyPlantUpdateRepository;
    }

    public void save(DailyPlantUpdate dailyPlantUpdate) {
        dailyPlantUpdateRepository.save(dailyPlantUpdate);
    }
}
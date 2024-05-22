package com.lars0709.DataPlants.controller;

import com.lars0709.DataPlants.model.DailyPlantUpdate;
import com.lars0709.DataPlants.model.Plant;
import com.lars0709.DataPlants.repository.DailyPlantUpdateRepository;
import com.lars0709.DataPlants.repository.PlantRepository;
import com.lars0709.DataPlants.service.DailyPlantUpdateService;
import com.lars0709.DataPlants.service.PlantService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class DailyPlantUpdateController {

    private final DailyPlantUpdateService dailyPlantUpdateService;
    private final DailyPlantUpdateRepository dailyPlantUpdateRepository;
    private final PlantService plantService;
    private final PlantRepository plantRepository;


    public DailyPlantUpdateController(DailyPlantUpdateService dailyPlantUpdateService,
                                      DailyPlantUpdateRepository dailyPlantUpdateRepository,
                                      PlantService plantService,
                                      PlantRepository plantRepository) {
        this.dailyPlantUpdateService = dailyPlantUpdateService;
        this.dailyPlantUpdateRepository = dailyPlantUpdateRepository;
        this.plantService = plantService;
        this.plantRepository = plantRepository;
    }

    @GetMapping("/add-daily-plant-update")
    public String getDailyUpdatesForm(Model model) {
        model.addAttribute("dailyPlantUpdate", new DailyPlantUpdate());
        List<Plant> activePlants = plantService.getAllActivePlants();
        model.addAttribute("plants", activePlants);
        if (!activePlants.isEmpty()) {
            model.addAttribute("plant", activePlants.get(0));
        }
        return "dailyPlantUpdate/add-daily-plant-update";
    }

    @PostMapping("/add-daily-plant-update")
    public String saveDailyUpdate(@ModelAttribute DailyPlantUpdate dailyPlantUpdate) {

        try {
            dailyPlantUpdate.setEntryDate(dailyPlantUpdate.getEntryDate());
            dailyPlantUpdate.setWeek(dailyPlantUpdate.getWeek());
            dailyPlantUpdate.setDay(dailyPlantUpdate.getDay());
            dailyPlantUpdate.setWater(dailyPlantUpdate.getWater());
            dailyPlantUpdate.setNutrients(dailyPlantUpdate.getNutrients());
            dailyPlantUpdate.setStage(dailyPlantUpdate.getStage());
            dailyPlantUpdate.setComment(dailyPlantUpdate.getComment());
            dailyPlantUpdate.setProblem(dailyPlantUpdate.getProblem());

            // Save the DailyPlantUpdate entity
            dailyPlantUpdateService.save(dailyPlantUpdate);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "redirect:dailyupdates";
    }

    @GetMapping("/dailyupdates")
    public String getDailyUpdates(Model model) {
        List<DailyPlantUpdate> dailyPlantUpdates = dailyPlantUpdateRepository.findAll();
        model.addAttribute("dailyPlantUpdates", dailyPlantUpdates);
        return "dailyPlantUpdate/daily-updates";
    }

    @GetMapping("/dailyupdates/{id}")
    public String editDailyUpdate(@PathVariable Long id, Model model) {
        Optional<DailyPlantUpdate> optionalDailyPlantUpdate = dailyPlantUpdateService.getDailyPlantUpdateById(id);
        if (optionalDailyPlantUpdate.isPresent()) {
            DailyPlantUpdate dailyPlantUpdate = optionalDailyPlantUpdate.get();
            model.addAttribute("dailyPlantUpdate", dailyPlantUpdate);
            return "dailyPlantUpdate/edit-daily-plant-update";
        } else {
            return "redirect:/dailyupdates";
        }
    }

    @PostMapping("/dailyupdates/{id}")
public String updateDailyUpdate(@PathVariable Long id, @ModelAttribute DailyPlantUpdate dailyPlantUpdate) {
    Optional<DailyPlantUpdate> optionalDailyPlantUpdate = dailyPlantUpdateService.getDailyPlantUpdateById(id);
    if (optionalDailyPlantUpdate.isPresent()) {
        DailyPlantUpdate existingDailyPlantUpdate = optionalDailyPlantUpdate.get();

        // Update the properties of the existingDailyPlantUpdate object
        existingDailyPlantUpdate.setEntryDate(dailyPlantUpdate.getEntryDate());
        existingDailyPlantUpdate.setWeek(dailyPlantUpdate.getWeek());
        existingDailyPlantUpdate.setDay(dailyPlantUpdate.getDay());
        existingDailyPlantUpdate.setWater(dailyPlantUpdate.getWater());
        existingDailyPlantUpdate.setNutrients(dailyPlantUpdate.getNutrients());
        existingDailyPlantUpdate.setStage(dailyPlantUpdate.getStage());
        existingDailyPlantUpdate.setComment(dailyPlantUpdate.getComment());
        existingDailyPlantUpdate.setProblem(dailyPlantUpdate.getProblem());

        dailyPlantUpdateService.save(existingDailyPlantUpdate);
    }
    return "redirect:/dailyupdates";
}

}

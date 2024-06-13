package com.lars0709.DataPlants.controller;

import com.lars0709.DataPlants.entity.DailyPlantUpdate;
import com.lars0709.DataPlants.entity.Plant;
import com.lars0709.DataPlants.repository.DailyPlantUpdateRepository;
import com.lars0709.DataPlants.service.DailyPlantUpdateService;
import com.lars0709.DataPlants.service.PlantService;
import com.lars0709.DataPlants.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
public class DailyPlantUpdateController {

    private final DailyPlantUpdateService dailyPlantUpdateService;
    private final DailyPlantUpdateRepository dailyPlantUpdateRepository;
    private final PlantService plantService;


    public DailyPlantUpdateController(DailyPlantUpdateService dailyPlantUpdateService,
                                      DailyPlantUpdateRepository dailyPlantUpdateRepository,
                                      PlantService plantService) {
        this.dailyPlantUpdateService = dailyPlantUpdateService;
        this.dailyPlantUpdateRepository = dailyPlantUpdateRepository;
        this.plantService = plantService;
    }

    @GetMapping("/add-daily-plant-update")
    public String getDailyUpdatesForm(Model model) {
        model.addAttribute("dailyPlantUpdate", new DailyPlantUpdate());

        List<Plant> plants = plantService.getAllPlants();
        Map<Plant, String> plantImageMap = new HashMap<>();
        for (Plant plant : plants) {
            String imageDataBase64 = Base64.getEncoder().encodeToString(plant.getStrain().getImageData());
            plantImageMap.put(plant, imageDataBase64);
        }
        model.addAttribute("plants", plantImageMap);

        return "dailyPlantUpdate/add-daily-plant-update";
    }

    @PostMapping("/add-daily-plant-update")
    public String saveDailyUpdate(@ModelAttribute DailyPlantUpdate dailyPlantUpdate,
                                  @RequestParam("image") MultipartFile multipartFile) {

        try {
            dailyPlantUpdate.setEntryDate(dailyPlantUpdate.getEntryDate());
            dailyPlantUpdate.setWeek(dailyPlantUpdate.getWeek());
            dailyPlantUpdate.setDay(dailyPlantUpdate.getDay());
            dailyPlantUpdate.setWater(dailyPlantUpdate.getWater());
            dailyPlantUpdate.setNutrients(dailyPlantUpdate.getNutrients());
            dailyPlantUpdate.setStage(dailyPlantUpdate.getStage());
            dailyPlantUpdate.setComment(dailyPlantUpdate.getComment());
            dailyPlantUpdate.setProblem(dailyPlantUpdate.getProblem());
            dailyPlantUpdate.setImageData(multipartFile.getBytes());

            // Save the DailyPlantUpdate entity
            dailyPlantUpdateService.save(dailyPlantUpdate);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:dailyupdates";
    }

    @GetMapping("/dailyupdates")
    public String getDailyUpdates(Model model) {
        List<DailyPlantUpdate> dailyPlantUpdates = dailyPlantUpdateRepository.findAll();
        model.addAttribute("dailyPlantUpdates", dailyPlantUpdates);

        if (!dailyPlantUpdates.isEmpty()) {
            model.addAttribute("update", dailyPlantUpdates.get(0));
        }

        return "dailyPlantUpdate/daily-updates";
    }

    @GetMapping("/dailyupdates/details/edit/{id}")
    public String editDailyUpdate(@PathVariable Long id, Model model) {
        Optional<DailyPlantUpdate> optionalDailyPlantUpdate = dailyPlantUpdateService.getDailyPlantUpdateById(id);
        if (optionalDailyPlantUpdate.isPresent()) {
            DailyPlantUpdate dailyPlantUpdate = optionalDailyPlantUpdate.get();
            String imageDataBase64 = Base64.getEncoder().encodeToString(dailyPlantUpdate.getImageData());
            model.addAttribute("dailyPlantUpdate", dailyPlantUpdate);
            model.addAttribute("imageData", imageDataBase64); // Add this line
            return "dailyPlantUpdate/edit-daily-plant-update";
        } else {
            return "redirect:/dailyupdates";
        }
    }

    @PostMapping("/dailyupdates/details/edit/{id}")
    public String updateDailyUpdate(@PathVariable Long id, @ModelAttribute DailyPlantUpdate dailyPlantUpdate, @RequestParam("image") MultipartFile multipartFile) {
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

            // Handle the image upload
            if (!multipartFile.isEmpty()) {
                try {
                    existingDailyPlantUpdate.setImageData(multipartFile.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to store uploaded image.", e);
                }
            }

            dailyPlantUpdateService.save(existingDailyPlantUpdate);
        }
        return "redirect:/dailyupdates";
    }

    @GetMapping("/dailyupdates/details/{id}")
    public String getDailyUpdateDetails(@PathVariable Long id, Model model) {
        Optional<DailyPlantUpdate> optionalDailyPlantUpdate = dailyPlantUpdateService.getDailyPlantUpdateById(id);
        if (optionalDailyPlantUpdate.isPresent()) {
            DailyPlantUpdate dailyPlantUpdate = optionalDailyPlantUpdate.get();
            model.addAttribute("dailyPlantUpdate", dailyPlantUpdate);

            // Add the image data to the model
            String imageDataBase64 = Base64.getEncoder().encodeToString(dailyPlantUpdate.getImageData());
            model.addAttribute("imageData", imageDataBase64);

            return "dailyPlantUpdate/daily-plant-update-details";
        } else {
            return "redirect:/dailyupdates";
        }
    }

    @PostMapping("/dailyupdates/details/delete/{id}")
    public String deleteDailyUpdate(@PathVariable Long id) {
        dailyPlantUpdateService.deleteDailyPlantUpdateById(id);
        return "redirect:/dailyupdates";
    }

    @Transactional
    @GetMapping("/dailyupdates/images/{plantId}")
    public String getDailyUpdateImages(@PathVariable Long plantId, Model model) {
        List<DailyPlantUpdate> dailyPlantUpdates = dailyPlantUpdateRepository.findByPlantId(plantId);

        // Sort the dailyPlantUpdates list by entryDate
        Collections.sort(dailyPlantUpdates, Comparator.comparing(DailyPlantUpdate::getEntryDate));

        Map<DailyPlantUpdate, String> dailyPlantUpdateImageMap = new LinkedHashMap<>();
        for (DailyPlantUpdate dailyPlantUpdate : dailyPlantUpdates) {
            String imageDataBase64 = Base64.getEncoder().encodeToString(dailyPlantUpdate.getImageData());
            dailyPlantUpdateImageMap.put(dailyPlantUpdate, imageDataBase64);
        }
        model.addAttribute("dailyPlantUpdates", dailyPlantUpdateImageMap);
        return "plant/plant-images";
    }

}

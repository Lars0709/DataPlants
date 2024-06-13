package com.lars0709.DataPlants.controller;

import com.lars0709.DataPlants.entity.Plant;
import com.lars0709.DataPlants.entity.DailyPlantUpdate;
import com.lars0709.DataPlants.repository.DailyPlantUpdateRepository;
import com.lars0709.DataPlants.service.PlantService;
import com.lars0709.DataPlants.service.StrainService;
import com.lars0709.DataPlants.service.UserService;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
public class PlantController {

    private final PlantService plantService;
    private final StrainService strainService;
    private final DailyPlantUpdateRepository dailyPlantUpdateRepository;
    private final UserService userService;

    public PlantController(PlantService plantService, StrainService strainService, DailyPlantUpdateRepository dailyPlantUpdateRepository, UserService userService) {
        this.plantService = plantService;
        this.strainService = strainService;
        this.dailyPlantUpdateRepository = dailyPlantUpdateRepository;
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        dataBinder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        dataBinder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    @GetMapping(value = "/plants")
    public String getPlantsSite(Model model) {
        List<Plant> plants = plantService.getAllPlants();
        Map<Plant, String> plantImageMap = new HashMap<>();
        for (Plant plant : plants) {
            String imageDataBase64 = Base64.getEncoder().encodeToString(plant.getStrain().getImageData());
            plantImageMap.put(plant, imageDataBase64);
        }
        model.addAttribute("plants", plantImageMap);
        return "plant/plants";
    }

    @Transactional
    @GetMapping("/plants/{id}")
    public String getPlant(@PathVariable Long id, Model model) {
        Optional<Plant> optionalPlant = plantService.getPlantById(id);
        if (optionalPlant.isPresent()) {
            Plant plant = optionalPlant.get();
            String imageDataBase64 = Base64.getEncoder().encodeToString(plant.getStrain().getImageData());
            model.addAttribute("plant", plant);
            model.addAttribute("imageData", imageDataBase64);

            // Add dailyPlantUpdates to the model
            List<DailyPlantUpdate> dailyPlantUpdates = dailyPlantUpdateRepository.findByPlantId(plant.getId());
            model.addAttribute("dailyPlantUpdates", dailyPlantUpdates);

            return "plant/plant-details";
        } else {
            // handle case when plant is not found
            return "error/error404";
        }
    }


    @GetMapping("/plants/add")
    public String getAddPlantForm(Model model) {
        model.addAttribute("plant", new Plant());
        model.addAttribute("strains", strainService.getAllStrains());
        model.addAttribute("users", userService.getAllUsers());
        return "plant/add-plant";
    }

    @PostMapping("/plants/add")
    public String savePlant(@ModelAttribute Plant plant,
                            @RequestParam(value = "startOfGerminationStage") LocalDate startOfGerminationStage,
                            @RequestParam(value = "startOfSeedlingStage", required = false) LocalDate startOfSeedlingStage,
                            @RequestParam(value = "startOfVegetativeStage", required = false) LocalDate startOfVegetativeStage,
                            @RequestParam(value = "startOfFloweringStage", required = false) LocalDate startOfFloweringStage,
                            @RequestParam(value = "harvestDate", required = false) LocalDate harvestDate,
                            @RequestParam(value = "startDryingDate", required = false) LocalDate startDryingDate,
                            @RequestParam(value = "startCuringDate", required = false) LocalDate startCuringDate,
                            @RequestParam(value = "harvestWeight", required = false) Integer harvestWeight) {

        try {
            // Set the numerical values in the Strain entity
            plant.setStrain(plant.getStrain());
            plant.setStartOfGerminationStage(startOfGerminationStage);
            plant.setStartOfSeedlingStage(startOfSeedlingStage);
            plant.setStartOfVegetativeStage(startOfVegetativeStage);
            plant.setStartOfFloweringStage(startOfFloweringStage);
            plant.setHarvestDate(harvestDate);
            plant.setStartDryingDate(startDryingDate);
            plant.setStartCuringDate(startCuringDate);
            plant.setHarvestWeight(harvestWeight);

            // Save the Plant entity
            plantService.savePlant(plant);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "redirect:/plants";
    }

    @GetMapping("/plants/edit/{id}")
    public String editPlant(@PathVariable Long id, Model model) {
        Optional<Plant> optionalPlant = plantService.getPlantById(id);
        if (optionalPlant.isPresent()) {
            Plant plant = optionalPlant.get();
            model.addAttribute("plant", plant);
            model.addAttribute("strains", strainService.getAllStrains());
            return "plant/edit-plant";
        } else {
            return "redirect:/plants";
        }
    }

    @PostMapping("/plants/edit/{id}")
    public String updatePlant(@PathVariable Long id, @ModelAttribute Plant plant) {
        Optional<Plant> optionalPlant = plantService.getPlantById(id);
        if (optionalPlant.isPresent()) {
            Plant existingPlant = optionalPlant.get();
            existingPlant.setStartOfGerminationStage(plant.getStartOfGerminationStage());
            existingPlant.setStartOfSeedlingStage(plant.getStartOfSeedlingStage());
            existingPlant.setStartOfVegetativeStage(plant.getStartOfVegetativeStage());
            existingPlant.setStartOfFloweringStage(plant.getStartOfFloweringStage());
            existingPlant.setHarvestDate(plant.getHarvestDate());
            existingPlant.setStartDryingDate(plant.getStartDryingDate());
            existingPlant.setStartCuringDate(plant.getStartCuringDate());
            existingPlant.setHarvestWeight(plant.getHarvestWeight());
            plantService.savePlant(existingPlant);
        }
        return "redirect:/plants";
    }

    @PostMapping("/plants/details/delete/{id}")
    public String deletePlant(@PathVariable Long id) {
        plantService.deletePlantById(id);
        return "redirect:/plants";
    }

}

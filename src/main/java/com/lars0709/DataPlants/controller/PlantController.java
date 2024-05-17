package com.lars0709.DataPlants.controller;

import com.lars0709.DataPlants.model.Plant;
import com.lars0709.DataPlants.service.PlantService;
import com.lars0709.DataPlants.service.StrainService;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Controller
public class PlantController {

    private final PlantService plantService;
    private final StrainService strainService;

    public PlantController(PlantService plantService, StrainService strainService) {
        this.plantService = plantService;
        this.strainService = strainService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        dataBinder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        dataBinder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    @GetMapping(value = "/plants")
    public ModelAndView getPlantsSite() {
        ModelAndView modelAndView = new ModelAndView("plant/plants");
        modelAndView.addObject("plants", plantService.getAllPlants());
        return modelAndView;
    }

    @GetMapping("/plants/{id}")
    public String getPlant(@PathVariable Long id, Model model) {
        Optional<Plant> optionalPlant = plantService.getPlantById(id);
        if(optionalPlant.isPresent()) {
            model.addAttribute("plant", optionalPlant.get());
            return "plant/plant-details";
        } else {
            // handle case when plant is not found
            return "error/error404";
        }
    }

    @GetMapping("/plants/add")
    public String getAddPlantForm(Model model) {
        model.addAttribute("plant", new Plant());
        model.addAttribute("strains", strainService.getAllStrains()); // add this line
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
        if(optionalPlant.isPresent()) {
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
        if(optionalPlant.isPresent()) {
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

}

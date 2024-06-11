package com.lars0709.DataPlants.controller;

import com.lars0709.DataPlants.entity.Strain;
import com.lars0709.DataPlants.repository.StrainRepository;
import com.lars0709.DataPlants.service.StrainService;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
public class StrainController {

    private final StrainService strainService;
    private final StrainRepository strainRepository;

    public StrainController(StrainService strainService, StrainRepository strainRepository) {
        this.strainService = strainService;
        this.strainRepository = strainRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        dataBinder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        dataBinder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    @GetMapping("/strains/add")
    public String getAddStrainForm(Model model) {
        model.addAttribute("strain", new Strain());
        return "strain/add-strain";
    }

    @PostMapping("/strains/add")
    public String saveStrain(@ModelAttribute Strain strain,
                             @RequestParam("image") MultipartFile multipartFile,
                             @RequestParam(value = "description", required = false) String description,
                             @RequestParam(value = "aliases", required = false) String aliases,
                             @RequestParam(value = "parent_plant_one", required = false) String parent_plant_one,
                             @RequestParam(value = "parent_plant_two", required = false) String parent_plant_two,
                             @RequestParam(value = "parent_plant_three", required = false) String parent_plant_three,
                             @RequestParam(value = "feelings", required = false) String feelings,
                             @RequestParam(value = "tastes", required = false) String tastes,
                             @RequestParam("thcLevel") Double thcLevel,
                             @RequestParam(value = "cbdLevel", required = false) String cbdLevel,
                             @RequestParam(value = "sativaLevel", required = false) Integer sativaLevel,
                             @RequestParam(value = "indicaLevel", required = false) Integer indicaLevel,
                             @RequestParam(value = "ruderalisLevel", required = false) Integer ruderalisLevel,
                             @RequestParam(value = "floweringPhaseMin", required = false) Integer floweringPhaseMin,
                             @RequestParam(value = "floweringPhaseMax", required = false) Integer floweringPhaseMax,
                             @RequestParam(value = "seedToHarvestMin", required = false) Integer seedToHarvestMin,
                             @RequestParam(value = "seedToHarvestMax", required = false) Integer seedToHarvestMax,
                             @RequestParam(value = "indoorYieldMin", required = false) Integer indoorYieldMin,
                             @RequestParam(value = "indoorYieldMax", required = false) Integer indoorYieldMax,
                             @RequestParam(value = "availableSeeds", required = false) Integer availableSeeds) {

        try {
            List<String> aliasesList = aliases != null ? Arrays.asList(aliases.split(",")) : new ArrayList<>();
            List<String> feelingsList = feelings != null ? Arrays.asList(feelings.split(",")) : new ArrayList<>();
            List<String> tastesList = tastes != null ? Arrays.asList(tastes.split(",")) : new ArrayList<>();

            // Set the lists in the Strain entity
            strain.setAliases(aliasesList);
            strain.setFeelings(feelingsList);
            strain.setTastes(tastesList);

            // Set the numerical values in the Strain entity
            strain.setDescription(description);
            strain.setParent_plant_one(parent_plant_one);
            strain.setParent_plant_two(parent_plant_two);
            strain.setParent_plant_three(parent_plant_three);
            strain.setThcLevel(thcLevel);
            strain.setCbdLevel(cbdLevel);
            strain.setSativaLevel(sativaLevel);
            strain.setIndicaLevel(indicaLevel);
            strain.setRuderalisLevel(ruderalisLevel);
            strain.setFloweringPhaseMin(floweringPhaseMin);
            strain.setFloweringPhaseMax(floweringPhaseMax);
            strain.setSeedToHarvestMin(seedToHarvestMin);
            strain.setSeedToHarvestMax(seedToHarvestMax);
            strain.setIndoorYieldMin(indoorYieldMin);
            strain.setIndoorYieldMax(indoorYieldMax);
            strain.setAvailableSeeds(availableSeeds);
            strain.setImageData(multipartFile.getBytes());

            // Save the Strain entity
            strainService.saveStrain(strain);

        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        return "redirect:/strains";
    }

    @GetMapping(value = "/strains")
    public String getStrainsSite(Model model) {
        List<Strain> strains = strainService.getAllStrains();
        Map<Strain, String> strainImageMap = new HashMap<>();
        for (Strain strain : strains) {
            String imageDataBase64 = Base64.getEncoder().encodeToString(strain.getImageData());
            strainImageMap.put(strain, imageDataBase64);
        }
        model.addAttribute("strains", strainImageMap);
        return "strain/strains";
    }

    @GetMapping("/strains/{id}")
    public String getStrain(@PathVariable Long id, Model model) {
        Optional<Strain> optionalStrain = strainService.getStrainById(id);
        if (optionalStrain.isPresent()) {
            Strain strain = optionalStrain.get();
            String imageDataBase64 = Base64.getEncoder().encodeToString(strain.getImageData());
            model.addAttribute("strain", strain);
            model.addAttribute("imageData", imageDataBase64);
            return "strain/strain-details";
        } else {
            // handle case when strain is not found
            return "error/error404";
        }
    }

    @GetMapping("/strains/edit/{id}")
    public String editStrain(@PathVariable Long id, Model model) {
        Optional<Strain> optionalStrain = strainService.getStrainById(id);
        if (optionalStrain.isPresent()) {
            Strain strain = optionalStrain.get();
            String imageDataBase64 = Base64.getEncoder().encodeToString(strain.getImageData());
            model.addAttribute("strain", strain);
            model.addAttribute("imageData", imageDataBase64); // Add this line
            return "strain/edit-strain";
        } else {
            return "redirect:/strains";
        }
    }

    @PostMapping("/strains/edit/{id}")
    public String updateStrain(@PathVariable("id") long id, @Valid Strain strain, BindingResult result, Model model,
                               @RequestParam("image") MultipartFile multipartFile) {
        if (result.hasErrors()) {
            return "strain/edit-strain";
        }

        Optional<Strain> existingStrainOpt = strainRepository.findById(id);
        if (!existingStrainOpt.isPresent()) {
            // handle error when strain with the given id doesn't exist
        }

        Strain existingStrain = existingStrainOpt.get();
        existingStrain.setName(strain.getName());
        existingStrain.setDescription(strain.getDescription());
        existingStrain.setParent_plant_one(strain.getParent_plant_one());
        existingStrain.setParent_plant_two(strain.getParent_plant_two());
        existingStrain.setParent_plant_three(strain.getParent_plant_three());
        existingStrain.setAliases(strain.getAliases());
        existingStrain.setFeelings(strain.getFeelings());
        existingStrain.setTastes(strain.getTastes());
        existingStrain.setThcLevel(strain.getThcLevel());
        existingStrain.setCbdLevel(strain.getCbdLevel());
        existingStrain.setSativaLevel(strain.getSativaLevel());
        existingStrain.setIndicaLevel(strain.getIndicaLevel());
        existingStrain.setRuderalisLevel(strain.getRuderalisLevel());
        existingStrain.setFloweringPhaseMin(strain.getFloweringPhaseMin());
        existingStrain.setFloweringPhaseMax(strain.getFloweringPhaseMax());
        existingStrain.setSeedToHarvestMin(strain.getSeedToHarvestMin());
        existingStrain.setSeedToHarvestMax(strain.getSeedToHarvestMax());
        existingStrain.setIndoorYieldMin(strain.getIndoorYieldMin());
        existingStrain.setIndoorYieldMax(strain.getIndoorYieldMax());
        existingStrain.setAvailableSeeds(strain.getAvailableSeeds());

        // update other fields...

        // Add this block to handle the image file
        if (!multipartFile.isEmpty()) {
            try {
                byte[] imageData = multipartFile.getBytes();
                existingStrain.setImageData(imageData);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately in your application
            }
        }

        strainRepository.save(existingStrain);
        return "redirect:/strains";
    }

    @PostMapping("/strains/details/delete/{id}")
    public String deleteStrain(@PathVariable Long id) {
        strainService.deleteStrainById(id);
        return "redirect:/strains";
    }
}


package com.lars0709.DataPlants.controller;

import com.lars0709.DataPlants.model.Strain;
import com.lars0709.DataPlants.service.StrainService;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class StrainController {

    private final StrainService strainService;

    public StrainController(StrainService strainService) {
        this.strainService = strainService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        dataBinder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        dataBinder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
    }

    @GetMapping(value = "/strains")
    public ModelAndView getStrainsSite() {
        ModelAndView modelAndView = new ModelAndView("strain/strains");
        modelAndView.addObject("strains", strainService.getAllStrains());
        return modelAndView;
    }

    @GetMapping("/strains/add")
    public String getAddStrainForm(Model model) {
        model.addAttribute("strain", new Strain());
        return "strain/add-strain";
    }

    @PostMapping("/strains/add")
    public String saveStrain(@ModelAttribute Strain strain,
                             @RequestParam(value = "imageUrl", required = false) String imageUrl,
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
                             @RequestParam(value = "indoorYieldMax", required = false) Integer indoorYieldMax) {

        try {
            List<String> aliasesList = aliases != null ? Arrays.asList(aliases.split(",")) : new ArrayList<>();
            List<String> feelingsList = feelings != null ? Arrays.asList(feelings.split(",")) : new ArrayList<>();
            List<String> tastesList = tastes != null ? Arrays.asList(tastes.split(",")) : new ArrayList<>();

            // Set the lists in the Strain entity
            strain.setAliases(aliasesList);
            strain.setFeelings(feelingsList);
            strain.setTastes(tastesList);

            // Set the numerical values in the Strain entity
            strain.setImageUrl(imageUrl);
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


            // Save the Strain entity
            strainService.saveStrain(strain);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "redirect:/strains";
    }
}

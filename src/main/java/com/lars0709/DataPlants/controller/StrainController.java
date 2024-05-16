package com.lars0709.DataPlants.controller;

import com.lars0709.DataPlants.model.Strain;
import com.lars0709.DataPlants.service.StrainService;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
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
        // This will convert empty strings into null
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        dataBinder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        dataBinder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
        // dataBinder.registerCustomEditor(Byte.class, new CustomNumberEditor(Byte.class, true));
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
                             @RequestParam(value = "aliases", required = false) String aliases,
                             @RequestParam(value = "feelings", required = false) String feelings,
                             @RequestParam(value = "tastes", required = false) String tastes,
                             @RequestParam("thcLevel") Double thcLevel,
                             @RequestParam(value = "cbdLevel", required = false) Double cbdLevel,
                             @RequestParam(value = "cbgLevel", required = false) Double cbgLevel,
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
            strain.setThcLevel(thcLevel);
            strain.setCbdLevel(cbdLevel);
            strain.setCbgLevel(cbgLevel);
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

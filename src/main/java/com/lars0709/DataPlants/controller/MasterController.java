package com.lars0709.DataPlants.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MasterController {

    @GetMapping(value = "/")
    private String websiteIndexPage(Model model) {
        return "index";
    }

    @GetMapping(value = "/information")
    private String websiteInformationPage() {
        return "information";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
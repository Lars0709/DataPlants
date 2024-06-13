package com.lars0709.DataPlants.controller;

import com.lars0709.DataPlants.entity.User;
import com.lars0709.DataPlants.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users/add")
    public String getAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user/add-user";
    }

    @PostMapping("/users/add")
    public String saveUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/";
    }

}

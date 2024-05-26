package com.lars0709.DataPlants.controller;

import com.lars0709.DataPlants.model.Image;
import com.lars0709.DataPlants.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class MasterController {

    @Autowired
    private ImageService imageService;

    @GetMapping(value = "/")
    private String websiteIndexPage(Model model) {
        return "index";
    }

    @GetMapping(value = "/information")
    private String websiteInformationPage() {
        return "information";
    }

    @GetMapping("/images")
    public String viewAllImages(Model model) {
        List<Image> images = imageService.getAllImages();
        model.addAttribute("images", images);
        return "images"; // This should be the name of your Thymeleaf template
    }

    @GetMapping(value = "/image-test")
    private String websiteImageTestPage() {
        return "image-test";
    }

    @PostMapping("/image-test")
    public String uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("type") String type) {
        Image image = new Image();
        image.setName(name);
        image.setType(type);
        try {
            imageService.saveImage(file, image);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately in your application
        }
        return "redirect:/"; // Redirect to a page after successful upload
    }

    @GetMapping("/images/{id}")
    public String viewImage(@PathVariable Long id, Model model) {
        Optional<Image> imageOptional = imageService.getImageById(id);
        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            String imageDataBase64 = Base64.getEncoder().encodeToString(image.getImageData());
            model.addAttribute("image", image);
            model.addAttribute("imageData", imageDataBase64);
            return "view-image"; // This should be the name of your Thymeleaf template
        } else {
            // Redirect to an error page or a page that says the image was not found
            return "error/error404";
        }
    }

}
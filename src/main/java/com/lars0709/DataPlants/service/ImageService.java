package com.lars0709.DataPlants.service;

import com.lars0709.DataPlants.model.Image;
import com.lars0709.DataPlants.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image saveImage(MultipartFile file, Image image) throws IOException {
        byte[] bytes = file.getBytes();
        image.setImageData(bytes);
        return imageRepository.save(image);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

}
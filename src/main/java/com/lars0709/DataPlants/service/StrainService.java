package com.lars0709.DataPlants.service;

import com.lars0709.DataPlants.model.Image;
import com.lars0709.DataPlants.model.Strain;
import com.lars0709.DataPlants.repository.StrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StrainService {

    private final StrainRepository strainRepository;
    private final ImageService imageService;

    @Autowired
    public StrainService(StrainRepository strainRepository, ImageService imageService) {
        this.strainRepository = strainRepository;
        this.imageService = imageService;
    }

    public List<Strain> getAllStrains() {
        return strainRepository.findAll();
    }

    public void saveStrain(Strain strain) throws IOException {
        strainRepository.save(strain);
    }

    public Optional<Strain> getStrainById(Long id) {
        return strainRepository.findById(id);
    }

    public void deleteStrainById(Long id) {
        strainRepository.deleteById(id);
    }
}
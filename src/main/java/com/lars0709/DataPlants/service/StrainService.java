package com.lars0709.DataPlants.service;

import com.lars0709.DataPlants.model.Strain;
import com.lars0709.DataPlants.repository.StrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrainService {

    private final StrainRepository strainRepository;

    @Autowired
    public StrainService(StrainRepository strainRepository) {
        this.strainRepository = strainRepository;
    }

    public List<Strain> getAllStrains() {
        return strainRepository.findAll();
    }

    public void saveStrain(Strain strain) {
        strainRepository.save(strain);
    }
}


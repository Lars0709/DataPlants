package com.lars0709.DataPlants.repository;

import com.lars0709.DataPlants.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findById(Long id);

}

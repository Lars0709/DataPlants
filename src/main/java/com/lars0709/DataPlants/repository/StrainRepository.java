package com.lars0709.DataPlants.repository;

import com.lars0709.DataPlants.entity.Strain;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StrainRepository extends JpaRepository<Strain, Long> {}
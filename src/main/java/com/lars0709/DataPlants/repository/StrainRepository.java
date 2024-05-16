package com.lars0709.DataPlants.repository;

import com.lars0709.DataPlants.model.Strain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StrainRepository extends JpaRepository<Strain, Long> {}
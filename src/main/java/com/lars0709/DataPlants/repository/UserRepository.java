package com.lars0709.DataPlants.repository;

import com.lars0709.DataPlants.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

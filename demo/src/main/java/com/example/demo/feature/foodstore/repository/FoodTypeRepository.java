package com.example.demo.feature.foodstore.repository;

import com.example.demo.feature.foodstore.entity.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTypeRepository extends JpaRepository<FoodType, Integer> {
}

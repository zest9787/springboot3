package com.example.demo.feature.foodstore.repository;

import com.example.demo.feature.foodstore.entity.FoodStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodStoreRepository extends JpaRepository<FoodStore, Integer> {
}

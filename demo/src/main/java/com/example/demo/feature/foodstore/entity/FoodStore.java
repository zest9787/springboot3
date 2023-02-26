package com.example.demo.feature.foodstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString(of = {"id", "storeName", "rate", "ownerName"})
public class FoodStore {

    @Id @GeneratedValue
    private Integer id;

    private String storeName;
    private int rate;
    private String ownerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private FoodType foodType;

    public FoodStore(String storeName, int rate, String ownerName, FoodType foodType) {
        this.storeName = storeName;
        this.rate = rate;
        this.ownerName = ownerName;
        changeFoodType(foodType);
    }

    private void changeFoodType(FoodType foodType) {
        this.foodType = foodType;
        foodType.getFoodStoreList().add(this);
    }
}
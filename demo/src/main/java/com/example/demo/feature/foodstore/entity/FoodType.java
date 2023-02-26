package com.example.demo.feature.foodstore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "foodTypeName", "foodOrder"})
public class FoodType {

    @Id @GeneratedValue
    @Column(name = "food_type_id")
    private Integer id;

    @Column(unique = true)
    private String foodTypeName;

    private int foodOrder;

    @OneToMany(mappedBy = "foodType")
    private List<FoodStore> foodStoreList = new ArrayList<>();

    public FoodType(String foodTypeName, int foodOrder) {
        this.foodTypeName = foodTypeName;
        this.foodOrder = foodOrder;
    }
}

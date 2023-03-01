package com.example.demo.feature.product.entity;

import com.example.demo.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name="product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Builder
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id
    private Long id;

    String name;
    Integer price;
    Integer stock;

}

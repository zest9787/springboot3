package com.example.demo.feature.user.domain;

import com.example.demo.domain.BaseEntity;
import com.example.demo.domain.listener.CustomEntityListener;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tuser")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(CustomEntityListener.class)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String email;
    String name;

}

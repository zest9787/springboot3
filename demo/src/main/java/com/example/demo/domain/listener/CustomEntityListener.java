package com.example.demo.domain.listener;

import com.example.demo.feature.user.domain.User;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomEntityListener {

    @PostLoad
    public void preLoad(User user) {
        log.info(":: [PostLoad] called");
    }
    @PrePersist
    public void PrePersist(User user) {
        log.info(":: [PrePersist] called");
    }
    @PostPersist
    public void PostPersist(User user) {
        log.info(":: [PostPersist] called");
    }
    @PreUpdate
    public void PreUpdate(User user) {
        log.info(":: [PreUpdate] called");
    }
    @PostUpdate
    public void postUpdate(User entity) {
        log.info(":: [postUpdate] called!!");
    }

    @PreRemove
    public void preRemove(User entity) {
        log.info(":: [preRemove] called!!");
    }

    @PostRemove
    public void postRemove(User entity) {
        log.info(":: [postRemove] called!!");
    }
}

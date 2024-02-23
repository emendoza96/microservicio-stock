package com.microservice.stock.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.stock.domain.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public Optional<UserEntity> findByUsername(String username);

}

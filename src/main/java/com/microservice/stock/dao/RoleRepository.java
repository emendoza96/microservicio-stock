package com.microservice.stock.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.stock.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

}

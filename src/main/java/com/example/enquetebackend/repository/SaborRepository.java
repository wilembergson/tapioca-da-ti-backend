package com.example.enquetebackend.repository;

import com.example.enquetebackend.entity.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaborRepository extends JpaRepository<Sabor, Integer> {
}

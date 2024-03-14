package com.example.enquetebackend.repository;

import com.example.enquetebackend.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRespository extends JpaRepository<Voto, Integer> {
}

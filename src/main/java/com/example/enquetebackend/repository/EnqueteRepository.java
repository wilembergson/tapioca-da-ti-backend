package com.example.enquetebackend.repository;

import com.example.enquetebackend.entity.Enquete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnqueteRepository extends JpaRepository<Enquete, Integer> {

    Optional<Enquete> findByAtivo(Integer ativo);

    Optional<Enquete> findByExibirResultado(Integer exibirResultado);
}

package com.example.enquetebackend.repository;

import com.example.enquetebackend.entity.Item;
import com.example.enquetebackend.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    Optional<Pedido> findByStatus(String status);
}

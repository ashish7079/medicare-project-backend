package com.example.demo.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.OrderMedicine;

public interface OrderRepo
extends JpaRepository<OrderMedicine, Long>{

    // IMPORTANT
    List<OrderMedicine> findByAuthId(Long authId);
}
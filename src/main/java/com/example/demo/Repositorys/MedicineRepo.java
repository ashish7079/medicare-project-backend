package com.example.demo.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Medicine;

public interface MedicineRepo
extends JpaRepository<Medicine, Long>{

    // IMPORTANT
    List<Medicine> findByAuthId(Long authId);
}
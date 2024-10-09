package com.example.demo.repository;

import com.example.demo.entity.SaleLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleLineRepository extends JpaRepository<SaleLine, Long> {
}

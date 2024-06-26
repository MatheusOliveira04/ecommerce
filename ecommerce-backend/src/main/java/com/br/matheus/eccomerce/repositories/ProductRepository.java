package com.br.matheus.eccomerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.matheus.eccomerce.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

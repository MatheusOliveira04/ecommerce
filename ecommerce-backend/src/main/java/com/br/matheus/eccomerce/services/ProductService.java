package com.br.matheus.eccomerce.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.br.matheus.eccomerce.models.Product;

public interface ProductService {

	Product insert(Product product);
	
	Product update(Product product);
	
	Product findById(Long id);
	
	void delete(Long id);
	
	List<Product> findAll();
	
	Page<Product> findAll(Pageable pageable);
}

package com.br.matheus.eccomerce.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.br.matheus.eccomerce.models.Product;
import com.br.matheus.eccomerce.repositories.ProductRepository;
import com.br.matheus.eccomerce.services.ProductService;
import com.br.matheus.eccomerce.services.exceptions.IntegrityViolation;
import com.br.matheus.eccomerce.services.exceptions.ObjectNotFound;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	
	private void validatedName(Product product) {
		NameIsEmptyOrNull(product);
	}

	private void NameIsEmptyOrNull(Product product) {
		if(product.getFirstName().isBlank() || product.getFirstName() == null) {
			throw new IntegrityViolation("The name is null or empty");
		}
	}
	
	private void validatedCodebar(Product product) {
		codebarIsEmptyOrNull(product);
		codebarLengthIsOk(product);
		codebarNotContainNumbers(product);
		//TODO -> Implements if already exists a codebar
	}

	private void codebarIsEmptyOrNull(Product product) {
		if (product.getCodebar().isBlank() || product.getCodebar() == null) {
			throw new IntegrityViolation("The codebar is null or empty");
		}
	}

	private void codebarLengthIsOk(Product product) {
		if (product.getCodebar().trim().length() != 11) {
			throw new IntegrityViolation("The codebar must have 11 caracters");
		}
	}

	private void codebarNotContainNumbers(Product product) {
		if (!Pattern.compile(".*\\d.*").matcher(product.getCodebar()).matches()) {
			throw new IntegrityViolation("The codebar must have only numbers");
		}
	}
	
	@Override
	public Product insert(Product product) {
		validatedName(product);
		validatedCodebar(product);
		return productRepository.save(product);
	}

	@Override
	public Product update(Product product) {
		findById(product.getId());
		validatedName(product);
		validatedCodebar(product);
		return productRepository.save(product);
	}

	@Override
	public Product findById(Long id) {
		Optional<Product> byId = productRepository.findById(id);
		return byId.orElseThrow(() -> new ObjectNotFound("Product not found"));
	}

	@Override
	public void delete(Long id) {
		Product product = findById(id);
		productRepository.delete(product);
	}

	@Override
	public List<Product> findAll() {
		List<Product> listProduct = productRepository.findAll();
		if (listProduct.isEmpty()) {
			throw new ObjectNotFound("Didn't find any products");
		} 
		return listProduct;
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		Page<Product> pages = productRepository.findAll(pageable);
		if (pages.isEmpty()) {
			throw new ObjectNotFound("Didn't find any products");
		}
		return pages;
	}

}

package com.br.matheus.eccomerce.resources;

import java.net.URI;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.br.matheus.eccomerce.models.Product;
import com.br.matheus.eccomerce.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@GetMapping
	@Secured({"ROLE_USER"})
	@Cacheable(value = "/findAll")
	public ResponseEntity<List<Product>> findAll() {
		return ResponseEntity.ok(productService.findAll());
	}

	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@Secured({"ROLE_USER"})
	@GetMapping("/pageable")
	public ResponseEntity<Page<Product>> findAll(@PageableDefault(sort = "id" ,direction = Direction.ASC, page = 0, size = 10) Pageable pageable) {
		return ResponseEntity.ok(productService.findAll(pageable));
	}

	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(productService.findById(id));
	}

	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	@CacheEvict(value = "findAll", allEntries = true)
	public ResponseEntity<Product> insert(@RequestBody Product product, UriComponentsBuilder uriBuilder){
		URI uri = uriBuilder.path("/Product/{id}").buildAndExpand(product.getId()).toUri();
		return ResponseEntity.created(uri).body(productService.insert(product));
	}

	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	@CacheEvict(value = "findAll", allEntries = true)
	public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable Long id) {
		product.setId(id);
		return ResponseEntity.ok(productService.update(product));
	}

	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	@CacheEvict(value = "findAll", allEntries = true)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		productService.delete(id);
		return ResponseEntity.ok().build();
	}
}

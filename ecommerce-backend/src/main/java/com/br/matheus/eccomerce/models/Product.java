package com.br.matheus.eccomerce.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = "codebar")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

	public Product(String firstName, String codebar, Double price) {
		this.firstName = firstName;
		this.codebar = codebar;
		this.price = price;
		dateCreation = LocalDate.now();
	}
	
	@Id @NotNull @Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull @NotEmpty
	private String firstName;
	@NotNull @Size(min = 11, max = 11)
	private String codebar;
	@NotNull
	private LocalDate dateCreation;
	@NotNull
	private Double price;
}

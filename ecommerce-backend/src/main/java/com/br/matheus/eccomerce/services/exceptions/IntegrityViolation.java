package com.br.matheus.eccomerce.services.exceptions;

public class IntegrityViolation extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IntegrityViolation(String msg) {
		super(msg);
	}

	
}

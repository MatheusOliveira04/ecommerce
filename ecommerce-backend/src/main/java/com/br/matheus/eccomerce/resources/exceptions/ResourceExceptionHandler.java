package com.br.matheus.eccomerce.resources.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.br.matheus.eccomerce.services.exceptions.IntegrityViolation;
import com.br.matheus.eccomerce.services.exceptions.ObjectNotFound;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFound.class)
	public ResponseEntity<StandardError> getObjectNotFoundExceptionHandler(ObjectNotFound ex, HttpServletRequest req) {
		StandardError standardError = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
	}
	
	@ExceptionHandler(IntegrityViolation.class)
	public ResponseEntity<StandardError> getIntegrityViolationExceptionHandler(IntegrityViolation ex, HttpServletRequest req) {
		StandardError standardError = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), req.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}
}

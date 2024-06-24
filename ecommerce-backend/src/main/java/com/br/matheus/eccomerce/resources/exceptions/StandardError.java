package com.br.matheus.eccomerce.resources.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StandardError {

	private LocalDateTime time;
	private Integer error;
	private String message;
	private String url;
}

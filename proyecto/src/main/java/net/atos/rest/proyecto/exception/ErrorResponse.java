package net.atos.rest.proyecto.exception;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponse {
	
	private int statusCode;
	private LocalDateTime timeStamp;
	private String message;
	private String description;
	
	public ErrorResponse(int statusCode, String message, String description) {
		super();
		this.statusCode = statusCode;
		this.timeStamp = LocalDateTime.now();
		this.message = message;
		this.description = description;
	}

}

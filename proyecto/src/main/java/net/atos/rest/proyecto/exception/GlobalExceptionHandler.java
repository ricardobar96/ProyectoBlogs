package net.atos.rest.proyecto.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Object> genericException(CustomException customException,
												   HttpServletRequest request) {
		
		ErrorResponse errorResponse = new ErrorResponse(customException.getHttpStatus().value(),
														customException.getMessage(),
														request.getRequestURI());
		
		return new ResponseEntity<>(errorResponse, customException.getHttpStatus());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> defaultException(Exception exception,
													HttpServletRequest request){

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
														"Ha ocurrido algún error",
														request.getRequestURI());
		

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}

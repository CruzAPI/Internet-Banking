package com.cruzapi.internetbanking.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handle(MethodArgumentNotValidException e)
	{
		FieldError fieldError = e.getFieldError();
		
		if(fieldError != null)
		{
			return ResponseEntity.badRequest().body(fieldError.getDefaultMessage());
		}
		
		return ResponseEntity.badRequest().build();
	}
}

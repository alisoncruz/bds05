package com.devsuperior.movieflix.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.movieflix.services.exceptions.DataBaseException;
import com.devsuperior.movieflix.services.exceptions.ForbiddenException;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.services.exceptions.UnauthorizedException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {

		StandardError standardError = new StandardError();
		standardError.setTimeStamp(Instant.now());
		standardError.setStatus(HttpStatus.NOT_FOUND.value());
		standardError.setError("Resource not found");
		standardError.setMessage(e.getMessage());
		standardError.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
	}

	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandardError> database(DataBaseException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError standardError = new StandardError();
		standardError.setTimeStamp(Instant.now());
		standardError.setStatus(status.value());
		standardError.setError("Database exception");
		standardError.setMessage(e.getMessage());
		standardError.setPath(request.getRequestURI());
		return ResponseEntity.status(status.value()).body(standardError);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> database(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationError validationError = new ValidationError();
		validationError.setTimeStamp(Instant.now());
		validationError.setStatus(status.value());
		validationError.setError("Validation exception");
		validationError.setMessage(e.getMessage());
		validationError.setPath(request.getRequestURI());
		
		for(FieldError f: e.getBindingResult().getFieldErrors()) {
			validationError.addError(f.getField(),f.getDefaultMessage());
		}
		return ResponseEntity.status(status.value()).body(validationError);
	}
	
	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<OAuthCustomError> database(ForbiddenException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		OAuthCustomError authCustomError = new OAuthCustomError();
		authCustomError.setError("Forbidden Exception");
		authCustomError.setErrorDescription(e.getMessage());
		return ResponseEntity.status(status.value()).body(authCustomError);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<OAuthCustomError> database(UnauthorizedException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		OAuthCustomError authCustomError = new OAuthCustomError();
		authCustomError.setError("Unauthorized Exception");
		authCustomError.setErrorDescription(e.getMessage());
		return ResponseEntity.status(status.value()).body(authCustomError);
	}
}

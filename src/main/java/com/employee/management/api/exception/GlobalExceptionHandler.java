package com.employee.management.api.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.employee.management.api.controller.EmployeeController;
import com.employee.management.api.dto.EmployeeResponseDTO;
import com.employee.management.api.dto.ErrorResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private final EmployeeController employeeController;


    GlobalExceptionHandler(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex)
	{
		
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult()
			.getFieldErrors()
			.forEach(error -> 
				errors.put(error.getField(), error.getDefaultMessage()));
		
		ErrorResponse response = ErrorResponse.builder()
								.timestamp(LocalDateTime.now())
								.status(HttpStatus.BAD_REQUEST.value())
								.error("Validation Failed")
								.validationErrors(errors)
								.build();
		
		return ResponseEntity.badRequest().body(response);
		
	}
	
	@ExceptionHandler(TransactionSystemException.class)
	public ResponseEntity<ErrorResponse> handleTransactionSystemException(TransactionSystemException ex)
	{
		
		Throwable rootCause = ex.getRootCause();
		
		if(rootCause instanceof ConstraintViolationException violationException)
		{
			Map<String, String> errors = new HashMap<>();
			
			for(ConstraintViolation<?> violation :  violationException.getConstraintViolations())
			{
				String fieldNamString = violation.getPropertyPath().toString();
				
				errors.put(fieldNamString, violation.getMessage());
			}
			
			ErrorResponse response = ErrorResponse.builder()
									.timestamp(LocalDateTime.now())
									.status(HttpStatus.BAD_REQUEST.value())
									.error("Validation Error")
									.validationErrors(errors)
									.build();
			
			return ResponseEntity.badRequest().body(response);
			
		}
		
		
		ErrorResponse response = ErrorResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.error("Internal Server Error")
				.build();
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		
	}
	
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex)
	{
		ErrorResponse errorResponse = ErrorResponse.builder()
									 .timestamp(LocalDateTime.now())
									 .status(HttpStatus.CONFLICT.value())
									 .error(ex.getMessage())
									 .build();
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex)
	{
		ErrorResponse errorResponse = ErrorResponse.builder()
									 .timestamp(LocalDateTime.now())
									 .status(HttpStatus.NOT_FOUND.value())
									 .error(ex.getMessage())
									 .build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		
		ErrorResponse response = ErrorResponse.builder()
								.timestamp(LocalDateTime.now())
								.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
								.error(ex.getMessage())
								.build();
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		
	}

}

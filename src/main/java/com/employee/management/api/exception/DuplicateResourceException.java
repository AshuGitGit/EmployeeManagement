package com.employee.management.api.exception;


public class DuplicateResourceException extends RuntimeException{
	
	public DuplicateResourceException(String message)
	{
		super(message);
	}

}

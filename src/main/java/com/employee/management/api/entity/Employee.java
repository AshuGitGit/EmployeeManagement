package com.employee.management.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	//@NotBlank(message = "Username cannot be blank")
	@Column(unique = true,nullable = false)
	private String userName;
	
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	@NotBlank(message = "Password cannot be blank")
	private String password;
	
	@NotBlank(message = "Department cannot be null")
	private String department;
	
	private Double salary;
	
	private Integer age;
	
	@Column(name = "ISDELETED")
	private boolean isDeleted = false;

}

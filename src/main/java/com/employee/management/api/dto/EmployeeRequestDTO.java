package com.employee.management.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDTO {
	
	private String id;
	
	@NotBlank(message = "Username cannot be blank")
	private String userName;
	
	@NotBlank(message = "Name Cannot be Blank!")
	private String name;
	
	@NotBlank(message = "Password Cannot be Blank!")
	private String password;
	
	@NotBlank(message = "Department Cannot be Blank!")
	private String department;
	
	private Double salary;
	
	private Integer age;

}

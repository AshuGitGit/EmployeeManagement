package com.employee.management.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateDTO {
	
	
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	@NotBlank(message = "Department cannot be blank")
	private String department;
	
	@NotNull(message = "Salary cannot be blank")
	private Double salary;
	
	@NotNull(message = "Age cannot be blank")
	private Integer age;
	
	private String message;

}

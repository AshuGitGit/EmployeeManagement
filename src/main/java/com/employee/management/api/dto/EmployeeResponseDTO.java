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
public class EmployeeResponseDTO {
	
	//public String id;
	
	@NotBlank(message = "Username cannot be blank")
	private String userName;
	
	@NotBlank(message = "Name cannot be blank")
	public String name;
	
	@NotBlank(message = "Department cannot be blank")
	public String department;
	
	public Double salary;
	
	public Integer age;

}

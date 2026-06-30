package com.employee.management.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.employee.management.api.dto.EmployeeResponseDTO;
import com.employee.management.api.dto.EmployeeUpdateDTO;
import com.employee.management.api.dto.PageResponseDTO;
import com.employee.management.api.entity.Employee;

import jakarta.validation.Valid;

@Service
public interface EmployeeService {

	EmployeeResponseDTO createEmployee(Employee e1);

	EmployeeResponseDTO getEmployeeById(String id);

	List<EmployeeResponseDTO> getAllEmployees();

	//List<EmployeeResponseDTO> getAllEmployees(Integer page, Integer size, String sortBy, String sortDir);
	
	PageResponseDTO<EmployeeResponseDTO> getAllEmployees(Integer page, Integer size, String sortBy, String sortDir);

	EmployeeResponseDTO updateEmployeeById(String id, @Valid EmployeeUpdateDTO employeeUpdateDTO);

	void deleteEmployeeByUserName(String userName);

}

package com.employee.management.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.api.dto.EmployeeRequestDTO;
import com.employee.management.api.dto.EmployeeResponseDTO;
import com.employee.management.api.dto.EmployeeUpdateDTO;
import com.employee.management.api.dto.PageResponseDTO;
import com.employee.management.api.entity.Employee;
import com.employee.management.api.service.EmployeeService;

import java.util.*;

import jakarta.validation.Valid;
import lombok.Getter;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
	
	private EmployeeService employeeService;
	
	private Logger log;
	
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
		this.log = LoggerFactory.getLogger(EmployeeController.class);
	}
	
	@PostMapping("/create")
	public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO emp)
	{
		log.info("Inside Create Employee Controller with Employee data {}",emp.toString());
		
		Employee e1 = new Employee();
		
		e1.setName(emp.getName());
		e1.setUserName(emp.getUserName());
		e1.setDepartment(emp.getDepartment());
		e1.setAge(emp.getAge());
		e1.setSalary(emp.getSalary());
		e1.setPassword(emp.getPassword());
		
		EmployeeResponseDTO employeeResponseDTO =  employeeService.createEmployee(e1);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponseDTO);
		
	}
	
	@GetMapping("/getEmp/{id}")
	public ResponseEntity<EmployeeResponseDTO> getEmployeeByID(@PathVariable String id)
	{
		log.info("Inside getEmployeeByID Controller with Employee data {}",id.toString());
		

		EmployeeResponseDTO respDto = employeeService.getEmployeeById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(respDto);
		
	}
	
	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees()
	{
		//log.info("Inside getEmployeeByID Controller with Employee data {}",id.toString());
		log.info("Inside getEmployeeByID Controller with Employee data {}");
		

		List<EmployeeResponseDTO> respDto = employeeService.getAllEmployees();
		
		return ResponseEntity.status(HttpStatus.OK).body(respDto);
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<PageResponseDTO<EmployeeResponseDTO>> getAllEmployeesPage(
			@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "5") Integer size,
			@RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir
			)
	{
		
		log.info("Inside getAllEmployeesPage Controller with Employee data {}");
		

		//List<EmployeeResponseDTO> respDto = employeeService.getAllEmployees(page,size,sortBy,sortDir);
		
		PageResponseDTO<EmployeeResponseDTO> respDto = employeeService.getAllEmployees(page,size,sortBy,sortDir);
		
		return ResponseEntity.status(HttpStatus.OK).body(respDto);
		
	}
	
	@PutMapping("/update/{userName}")
	public ResponseEntity<EmployeeResponseDTO> updateEmployeeById(
						@PathVariable String userName,
						@Valid @RequestBody EmployeeUpdateDTO employeeUpdateDTO)
	{
		
		log.info("Inside updateEmployeeById userName is {}",userName);
		
		EmployeeResponseDTO emp = employeeService.updateEmployeeById(userName,employeeUpdateDTO);
		
		return ResponseEntity.status(HttpStatus.OK).body(emp);
		
	}
	
	@DeleteMapping("/delete/{userName}")
	public ResponseEntity<Map<String, String>> deleteEmployeebyUsername(@PathVariable String userName)
	{
		employeeService.deleteEmployeeByUserName(userName);
		
		Map<String, String> resp = new HashMap<String, String>();
		resp.put("message", "User Deleted Successfully!!!");
		
		
		return ResponseEntity.status(HttpStatus.OK).body(resp);	
	}

}

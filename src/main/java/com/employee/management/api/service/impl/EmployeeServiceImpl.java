package com.employee.management.api.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.employee.management.api.dto.EmployeeResponseDTO;
import com.employee.management.api.dto.EmployeeUpdateDTO;
import com.employee.management.api.dto.PageResponseDTO;
import com.employee.management.api.entity.Employee;
import com.employee.management.api.exception.DuplicateResourceException;
import com.employee.management.api.exception.ResourceNotFoundException;
import com.employee.management.api.repo.EmployeeRepo;
import com.employee.management.api.service.EmployeeService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	private final EmployeeRepo employeeRepo;
	
	EmployeeServiceImpl(EmployeeRepo employeeRepo) {
		this.employeeRepo = employeeRepo;
	}
	
	@Override
	public EmployeeResponseDTO createEmployee(Employee e1) {
		
		
		boolean userExists = employeeRepo.existsByUserName(e1.getUserName());
		
		if(!userExists)
			employeeRepo.save(e1);
		else 
			throw new DuplicateResourceException("Username Already Exists In The System!");
		
		EmployeeResponseDTO employeeResponseDTO = EmployeeResponseDTO.builder()
												 .userName(e1.getUserName())
												 .name(e1.getName())
												 .department(e1.getDepartment())
												 .age(e1.getAge())
												 .salary(e1.getSalary())
												 .build();
		
		return employeeResponseDTO;
		
	}

	@Override
	public EmployeeResponseDTO getEmployeeById(String id) {
		
		Employee emp = employeeRepo.findById(id)
								.orElseThrow(() -> new RuntimeException("Employee Not Found"));
		
		/*
		 * EmployeeResponseDTO respDto = EmployeeResponseDTO.builder()
		 * .userName(emp.getUserName()) .name(emp.getName())
		 * .department(emp.getDepartment()) .age(emp.getAge())
		 * //.salary(emp.getSalary()) // .id(emp.getId()) .build();
		 * 
		 * return respDto;
		 */
		
		return this.mapResponseDTO(emp);	
		
	}

	@Override
	public List<EmployeeResponseDTO> getAllEmployees() {
		
		//List<Employee> list = employeeRepo.findAll();
		
		
		List<Employee> list = employeeRepo.findByIsDeletedFalse();
		
		//ArrayList<EmployeeResponseDTO> empDTOList = new ArrayList<EmployeeResponseDTO>();
		
		//Approach 1: Basic Level
		/*
		 * for(Employee emp : list) { EmployeeResponseDTO respDto =
		 * EmployeeResponseDTO.builder() .userName(emp.getUserName())
		 * .name(emp.getName()) .department(emp.getDepartment()) .age(emp.getAge())
		 * //.salary(emp.getSalary()) // .id(emp.getId()) .build();
		 * 
		 * empDTOList.add(respDto); }
		 * 
		 * return empDTOList;
		 */
		
		//Approach 2: Industry Level
		
		return list.stream()
					.map(this::mapResponseDTO)
					.toList();
	}
	
	private EmployeeResponseDTO mapResponseDTO(Employee emp)
	{
		return EmployeeResponseDTO.builder()
				.userName(emp.getUserName())
				.name(emp.getName())
				.department(emp.getDepartment())
				.salary(emp.getSalary())
				.age(emp.getAge())
				.build();
	}

	/*
	 * @Override public List<EmployeeResponseDTO> getAllEmployees(Integer page,
	 * Integer size, String sortBy, String sortDir) {
	 * 
	 * Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() :
	 * Sort.by(sortBy).descending();
	 * 
	 * Pageable pageable = PageRequest.of(page, size, sort);
	 * 
	 * Page<Employee> emploPage = employeeRepo.findAll(pageable);
	 * 
	 * return emploPage.stream() .map(this::mapResponseDTO) .toList(); }
	 */
	
	@Override
	public PageResponseDTO<EmployeeResponseDTO> getAllEmployees(Integer page, Integer size, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase("asc")
					? Sort.by(sortBy).ascending()
					: Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(page, size, sort);
		
		Page<Employee> emploPage = employeeRepo.findAll(pageable);
		
		if(page> emploPage.getTotalPages())
		{
			throw new ResourceNotFoundException("Page Does Not Exist!");
		}
		
		List<EmployeeResponseDTO> employeeResponseDTO = emploPage.stream()
													.map(this::mapResponseDTO)
													.toList();
		
		PageResponseDTO<EmployeeResponseDTO> pageResponseDTO = PageResponseDTO
																.<EmployeeResponseDTO>builder()
																.content(employeeResponseDTO)
																.pageNo(emploPage.getNumber())
																.pageSize(emploPage.getSize())
																.totalElements(emploPage.getTotalElements())
																.totalPages(emploPage.getTotalPages())
																.last(emploPage.isLast())
																.build();
		
			
		return pageResponseDTO;
	}

	@Override
	public EmployeeResponseDTO updateEmployeeById(String userName,
			@Valid EmployeeUpdateDTO employeeUpdateDTO) {

		Employee emp = employeeRepo.findByUserName(userName).orElseThrow(() -> new ResourceNotFoundException("No Data Found for mentioned username!"));
		
		emp.setName(employeeUpdateDTO.getName());
		emp.setAge(employeeUpdateDTO.getAge());
		emp.setDepartment(employeeUpdateDTO.getDepartment());
		emp.setSalary(employeeUpdateDTO.getSalary());
		
		employeeRepo.save(emp);

		return mapResponseDTO(emp);
	}

	@Override
	@Transactional
	public void deleteEmployeeByUserName(String userName) {
		
		Employee emp = employeeRepo.findByUserName(userName).orElseThrow(() -> new ResourceNotFoundException("User not available for deletion!"));
		
		//employeeRepo.deleteById(emp.getId());//Hard Delete
		
		emp.setAge(99);
		emp.setDeleted(true);
		employeeRepo.save(emp);//Soft Delete
		
	}

}

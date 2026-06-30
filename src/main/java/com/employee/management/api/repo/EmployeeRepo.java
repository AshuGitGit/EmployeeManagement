package com.employee.management.api.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.management.api.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String>{
	
	boolean existsByUserName(String userName);

	Optional<Employee> findByUserName(String userName);
	
	List<Employee> findByIsDeletedFalse();

}

package com.example.employee.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import com.example.employee.repos.EmployeeRepository;
import com.example.employee.repos.StudentRepository;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class EmployeeHandlers {
		
	 private final StudentRepository studentRepository;
	 
	private EmployeeRepository employeeRepository;
	
	public ServerResponse getAllEmployees(ServerRequest request) {
		
		return ServerResponse.ok().body(employeeRepository.findAll());
	}
	
	public ServerResponse getAllStudent(ServerRequest request) {
		
		return ServerResponse.ok().body(studentRepository.findAll());
	}
}

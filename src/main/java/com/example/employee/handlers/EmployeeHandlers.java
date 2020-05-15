package com.example.employee.handlers;

import com.example.employee.repos.EmployeeRepository;
import com.example.employee.repos.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.ServerResponse.ok;

@Controller
@AllArgsConstructor
public class EmployeeHandlers {

    private final StudentRepository studentRepository;

    private EmployeeRepository employeeRepository;

    public ServerResponse getAllEmployees(ServerRequest request) {
        return ok().body(employeeRepository.findAll());
    }

    public ServerResponse getAllStudent(ServerRequest request) {
        return ok().body(studentRepository.findAll());
    }
}

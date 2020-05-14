package com.example.employee.configs;

import com.example.employee.model.Department;
import com.example.employee.model.Employee;
import com.example.employee.model.Student;
import com.example.employee.model.Subject;
import com.example.employee.repos.EmployeeRepository;
import com.example.employee.repos.StudentRepository;
import com.example.employee.repos.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class DataInitalizer {

    private final EmployeeRepository employeeRepository;

    private final SubjectRepository subjectRepository;

    private final StudentRepository studentRepository;

    @Bean
    public CommandLineRunner init() {
        return args -> {
            employeeRepository.deleteAll();
            subjectRepository.deleteAll();
            studentRepository.deleteAll();
			

            Stream
                    .of(
                            Employee.builder()
                                    .name("satendra")
                                    .dept(
                                            List.of(
                                                    Department.builder()
                                                            .departmentName("oracle")
                                                            .build(),
                                                    Department.builder()
                                                            .departmentName("java")
                                                            .build()
                                            )
                                    )
                                    .build(),

                            Employee.builder()
                                    .name("manish")
                                    .dept(List.of(
                                                    Department.builder()
                                                            .departmentName("oracle").build(),
                                                    Department.builder()
                                                            .departmentName("AWS Cloud").build()
                                            )
                                    ).build(),

                            Employee.builder()
                                    .name("Lion")
                                    .build(),

                            Employee.builder()
                                    .name("Tiger")
                                    .build()
            )
                    .forEach(employeeRepository::save);

            Stream
                    .of(
                            Student.builder()
                                    .studentName("Rajan")
                                    .subject(subjectRepository.save(
                                            Subject.builder()
                                                    .subjectName("Mathematics")
                                                    .build()))
                                    .build())
                    .forEach(studentRepository::save);

        };
    }
}

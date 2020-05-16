package com.example.employee;

import com.example.employee.model.Department;
import com.example.employee.model.Employee;
import com.example.employee.model.Student;
import com.example.employee.model.Subject;
import com.example.employee.repos.EmployeeRepository;
import com.example.employee.repos.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//unit test use mockito
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeHandlersTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void getAllEmployee_should_OK() throws Exception {
        var emp = List.of(
                Employee.builder()
                        .name("test1")
                        .createdDate(LocalDateTime.of(2019, 12, 12, 12, 1, 1))
                        .dept(List
                                .of(
                                        Department.builder()
                                                .departmentName("test2s")
                                                .build()
                                )
                        )
                        .build());

        when(employeeRepository.findAll()).thenReturn(emp);

        mvc.perform(
                get("/api/employee/all").accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].name", is(emp.get(0).getName())))
                .andExpect(jsonPath("$.[0].createdDate", is(emp.get(0).getCreatedDate().toString())))
                .andExpect(jsonPath("$.[0].dept[0].departmentName", is(emp.get(0).getDept().get(0).getDepartmentName())));
    }

    @Test
    public void getAllStudent_should_OK() throws Exception {

        var stu = List.of(
                Student.builder()
                        .studentName("test student")
                        .subject(Subject.builder().subjectName("subject").build())
                        .build());

        when(studentRepository.findAll()).thenReturn(stu);

        mvc.perform(
                get("/api/student/all").accept(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].studentName",is(stu.get(0).getStudentName())))
                .andExpect(jsonPath("$.[0].subject.subjectName",is(stu.get(0).getSubject().getSubjectName())));
    }
}

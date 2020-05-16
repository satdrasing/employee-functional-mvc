package com.example.employee;

import com.example.employee.model.Employee;
import com.example.employee.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void getAllEmployee_should_OK() {
        var emp = restTemplate.getForObject("http://localhost:" + port + "/api/employee/all", Employee[].class);
        assertEquals(emp[0].getName(), "satendra");
        assertEquals(emp[0].getDept().get(0).getDepartmentName(), "oracle");
    }

    @Test
    public  void getAllStudent_should_OK(){
        Student[] student = restTemplate.getForObject("http://localhost:" + port + "/api/student/all", Student[].class);
        assertEquals(student[0].getStudentName(),"Rajan");
        assertEquals(student[0].getSubject().getSubjectName(),"Mathematics");
    }
}

package com.example.employee.repos;

import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.employee.model.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, BigInteger>{

}

package com.example.employee.repos;

import com.example.employee.model.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface SubjectRepository extends MongoRepository<Subject, BigInteger> {
}

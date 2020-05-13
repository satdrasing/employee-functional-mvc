package com.example.employee.model;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Employee {

	@Id
    private ObjectId id;
	
	private String name;

	@Builder.Default
	private LocalDateTime createdDate = LocalDateTime.now();
	
	private List<Department> dept;
}

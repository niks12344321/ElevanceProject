package com.demo.mongodb.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



@Document(collection = "traineeDB")
@JsonInclude(Include.NON_NULL)
public class TraineeDAO {

	@Id
	private long id;
	
	private String name;
	
	private String location;
	
	private LocalDate joinDate;
	private LocalDate testDate;

	public TraineeDAO() {
		// TODO Auto-generated constructor stub
	}

	

	public TraineeDAO(long id, String name, String location, LocalDate joinDate, LocalDate testDate) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.joinDate = joinDate;
		this.testDate = testDate;
	}



	@Override
	public String toString() {
		return "Trainee [id=" + id + ", name=" + name + ", location=" + location + ", joinDate=" + joinDate
				+ ", testDate=" + testDate + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name	) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getTestDate() {
		return testDate;
	}

	public void setTestDate(LocalDate testDate) {
		this.testDate = testDate;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}
	
	
}

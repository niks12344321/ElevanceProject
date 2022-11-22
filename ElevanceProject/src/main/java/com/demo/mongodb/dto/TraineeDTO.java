package com.demo.mongodb.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TraineeDTO {
	
	private String id;
	
	private String name;
	
	private String location;
	
	private String joinDate;
	private String testDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	public String getTestDate() {
		return testDate;
	}
	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}
	@Override
	public String toString() {
		return "TraineeDTO [id=" + id + ", name=" + name + ", location=" + location + ", joinDate=" + joinDate
				+ ", testDate=" + testDate + "]";
	}
	public TraineeDTO(String id, String name, String location) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
	}
	
	
	public TraineeDTO(String id, String name, String location, String joinDate, String testDate) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.joinDate = joinDate;
		this.testDate = testDate;
	}
	public TraineeDTO() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, joinDate, location, name, testDate);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TraineeDTO other = (TraineeDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(joinDate, other.joinDate)
				&& Objects.equals(location, other.location) && Objects.equals(name, other.name)
				&& Objects.equals(testDate, other.testDate);
	}
	
	
	
}

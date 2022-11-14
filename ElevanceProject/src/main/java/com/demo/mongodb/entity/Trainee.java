package com.demo.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "traineeDB")
public class Trainee {

	@Id
	private long id;
	private String name;
	private String location;

	public Trainee() {
		// TODO Auto-generated constructor stub
	}

	public Trainee(long id, String name, String location) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
	}

	@Override
	public String toString() {
		return "Trainee [id=" + id + ", name=" + name + ", location=" + location + "]";
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

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
}

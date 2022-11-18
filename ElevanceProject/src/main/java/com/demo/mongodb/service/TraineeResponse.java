package com.demo.mongodb.service;

import java.util.List;

import com.demo.mongodb.entity.Trainee;

public class TraineeResponse {
	
	private List<Trainee> traineesList=null;

	public List<Trainee> getTraineesList() {
		return traineesList;
	}

	public void setTraineesList(List<Trainee> traineesList) {
		this.traineesList = traineesList;
	}
	
	
	
	

}

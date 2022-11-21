package com.demo.mongodb.dto;

import java.util.List;

import com.demo.mongodb.entity.TraineeDAO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TraineeResponse {
	
	private List<TraineeDAO> traineesList=null;

	public List<TraineeDAO> getTraineesList() {
		return traineesList;
	}

	public void setTraineesList(List<TraineeDAO> traineesList) {
		this.traineesList = traineesList;
	}
}

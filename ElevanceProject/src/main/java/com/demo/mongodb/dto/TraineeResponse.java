package com.demo.mongodb.dto;

import java.util.List;

import com.demo.mongodb.entity.TraineeDAO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TraineeResponse {
	
	private List<TraineeDTO> traineesList=null;

	public List<TraineeDTO> getTraineesList() {
		return traineesList;
	}

	public void setTraineesList(List<TraineeDTO> traineesList) {
		this.traineesList = traineesList;
	}
}

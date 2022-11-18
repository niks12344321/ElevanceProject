package com.demo.mongodb.repository;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.demo.mongodb.entity.Trainee;
import com.demo.mongodb.service.TraineeResponse;

public interface ServiceRepository {
	
	ResponseEntity<Trainee> createTrainee(Trainee t);
	
	ResponseEntity<Trainee> updateTrainee(Trainee t);
	
	ResponseEntity<TraineeResponse> getAllTrainees();
	
	ResponseEntity<Optional<Trainee>> getTraineebyID(long id);
	
	ResponseEntity<TraineeResponse> getAllTraineesPages(int pnum, int psize,int dir, String v);
	
	ResponseEntity<TraineeResponse>  getSortedTrainees(int dir,String v);
	
	ResponseEntity<TraineeResponse> getTraineesGreaterThan(int gt);
	
	ResponseEntity<TraineeResponse> getTraineeByName(String nam);
	
	ResponseEntity<TraineeResponse> getTraineeByNameAndLocation(String nam, String loc);
	
	ResponseEntity<TraineeResponse> getTraineeByNameContaining(String expr);
	
	HttpStatus deleteTraineebyID(long id);
	
	int getTraineeCountByName(String nam);
	
	ResponseEntity<TraineeResponse> getTraineeByNameDispLoc(String nam);
	
}

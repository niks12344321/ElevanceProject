package com.demo.mongodb.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.demo.mongodb.dto.TraineeDTO;
import com.demo.mongodb.dto.TraineeResponse;
import com.demo.mongodb.entity.TraineeDAO;

public interface TraineeServiceInterface {
	
	ResponseEntity<TraineeDTO> createTrainee(TraineeDAO t);
	
	ResponseEntity<TraineeDTO> updateTrainee(TraineeDAO t);
	
	ResponseEntity<TraineeResponse> getAllTrainees();
	
	ResponseEntity<TraineeResponse> getTraineebyID(long id);
	
	ResponseEntity<TraineeResponse> getAllTraineesPages(int pnum, int psize,String dir, String v,Boolean idreq);
	
	ResponseEntity<TraineeResponse>  getSortedTrainees(String dir,String v);
	
	ResponseEntity<TraineeResponse> getTraineesGreaterThan(int gt);
	
	ResponseEntity<TraineeResponse> getTraineesGreaterThanDate(LocalDate joinDate);

	ResponseEntity<TraineeResponse> getTraineeByName(String nam);
	
	ResponseEntity<TraineeResponse> getTraineeByNameAndLocation(String nam, String loc);
	
	ResponseEntity<TraineeResponse> getTraineeByNameContaining(String expr);
	
	HttpStatus deleteTraineebyID(long id);
	
	int getTraineeCountByName(String nam);
	
	ResponseEntity<TraineeResponse> getTraineeByNameDispLoc(String nam);

	
}

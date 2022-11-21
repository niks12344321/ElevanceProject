/**
 * 
 */
package com.demo.mongodb.controller;

import static com.demo.mongodb.util.Constants.DIRERROR;
import static com.demo.mongodb.util.Constants.NUMBERFORM;
import static com.demo.mongodb.util.Constants.REQUIRED;
import static com.demo.mongodb.util.InputDataValidation.dirnValidation;
import static com.demo.mongodb.util.InputDataValidation.isNotValidNumber;
import static com.demo.mongodb.util.InputDataValidation.isNotValidString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.mongodb.dto.TraineeResponse;
import com.demo.mongodb.service.TraineeService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/**
 * @author nikhilgupta2
 *
 */
@RestController
@JsonInclude(Include.NON_NULL)
@RequestMapping("/GETvariations")
public class TraineeControllerGETVariation {
	
	Logger logger = LoggerFactory.getLogger(TraineeService.class);

	
	@Autowired
	private TraineeService service;
	
	
	
	//Get trainees with sorting. ASC for asc, DESC for desc.
	@GetMapping("/traineeWithSort/{sortDirection}/{by}")
	private ResponseEntity<TraineeResponse> getTraineeWithSorting(@PathVariable String sortDirection,@PathVariable String by) {

		TraineeResponse response=new TraineeResponse();

		try {
			if(dirnValidation(sortDirection))
				throw new IllegalArgumentException(DIRERROR);
			return service.getSortedTrainees(sortDirection,by);
		}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
	}
	
	// Get trainees with ids greater than x
	@GetMapping("/traineeGreaterThan/{gt}")
	private ResponseEntity<TraineeResponse> getTraineeGreater(@PathVariable String gt) {

		TraineeResponse response=new TraineeResponse();

		try {
			if(isNotValidNumber(gt))
				throw new IllegalArgumentException(NUMBERFORM);
			
			int greaterthan = Integer.parseInt(gt);

			return service.getTraineesGreaterThan(greaterthan);
		}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
			}
	}

	// Get trainees by Name
	@GetMapping("/traineeByName/{name}")
	private ResponseEntity<TraineeResponse> getTraineeThroughName(@PathVariable String name) {

		TraineeResponse response=new TraineeResponse();
		
		try {
			if(isNotValidString(name))
				throw new IllegalArgumentException("name " + REQUIRED);
			return service.getTraineeByName(name);
		}
		catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	// Get trainees by Name display only Location
	@GetMapping("/traineeByNameDispLocation/{name}")
	private ResponseEntity<TraineeResponse> getTraineeThroughNameDispLocation(@PathVariable String name) {

		TraineeResponse response=new TraineeResponse();

		try {
			if(isNotValidString(name))
				throw new IllegalArgumentException("name " + REQUIRED);
			return service.getTraineeByNameDispLoc(name);
		}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
			}
	}

	// Get trainees by Name and Location
	@GetMapping("/traineeByNameAndLoc/{name}/{loc}")
	private ResponseEntity<TraineeResponse> getTraineeThroughNameLocation(@PathVariable String name,@PathVariable String loc) {
		TraineeResponse response=new TraineeResponse();

		try {
			if((isNotValidString(name)||(isNotValidString(loc))))
				throw new IllegalArgumentException("name and location" + REQUIRED);
			return service.getTraineeByNameAndLocation(name,loc);
		}
		catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	// Get trainees by Name Like
	@GetMapping("/traineeByNameRegex/	{expr}")
	private ResponseEntity<TraineeResponse> getTraineewithNameregex(@PathVariable String expr) {

		return service.getTraineeByNameContaining(expr);
	}
	
	// Get trainee count by Name
	@GetMapping("/traineeCountByName/{name}")
	private int getTraineeCountThroughName(@PathVariable String name) {

		try {
			if(isNotValidString(name))
				throw new IllegalArgumentException("name " + REQUIRED);
			return service.getTraineeCountByName(name);
		} 
		catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return 0;
		}
		
	}

}

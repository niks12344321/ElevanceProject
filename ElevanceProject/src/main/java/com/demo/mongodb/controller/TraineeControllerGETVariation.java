/**
 * 
 */
package com.demo.mongodb.controller;

import static com.demo.mongodb.util.Constants.*;
import static com.demo.mongodb.util.InputDataValidation.*;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
			if(isValidNumber(gt))
				return service.getTraineesGreaterThan(Integer.parseInt(gt));
			else
				throw new IllegalArgumentException(NUMBERFORM);
		
		}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
			}
	}
	
	@GetMapping("/traineeWithPagesFilter")
	private ResponseEntity<TraineeResponse> getTraineeWithPages(
			@RequestParam(value="pageno", required=true, defaultValue ="1") String pn
	, @RequestParam(value="pagesiz", required=true,defaultValue = "1") String ps
	, @RequestParam(value="sortDirection", required=true,defaultValue = "ASC") String sortDirection
	, @RequestParam(value="sortby", required=true,defaultValue = "id") String sortParam
	, @RequestParam(value="gt", required=true,defaultValue = "1") String gt) {
		
		TraineeResponse response=new TraineeResponse();

		try {
			if(isValidNumber(pn)&&isValidNumber(ps)&&isValidNumber(gt))
			{
				if(dirnValidation(sortDirection))
					throw new IllegalArgumentException(DIRERROR);
				else
					return service.getAllTraineesPagesFilter(Integer.parseInt(pn),Integer.parseInt(ps),sortDirection,sortParam,Integer.parseInt(gt));
			}else 
				throw new IllegalArgumentException(NUMBERFORM);

		}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
	}
	
	// Get trainees with Dates greater than x
		@GetMapping("/traineeGreaterThanDate/{date}")
		private ResponseEntity<TraineeResponse> getTraineeGreaterDate(@PathVariable String date) {

			TraineeResponse response=new TraineeResponse();

			try {
				if(isValidDate(date))
				{
					LocalDate dat = LocalDate.parse(date);
					
					return service.getTraineesGreaterThanDate(dat);
				}
				else
					throw new IllegalArgumentException(INVALIDATE);
			
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
			if(isValidString(name))
				return service.getTraineeByName(name);
			else
				throw new IllegalArgumentException("name " + REQUIRED);
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
			if(isValidString(name))
				return service.getTraineeByNameDispLoc(name);
			else
				throw new IllegalArgumentException("name " + REQUIRED);
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
			if((isValidString(name)&&(isValidString(loc))))
				return service.getTraineeByNameAndLocation(name,loc);
			else
				throw new IllegalArgumentException("name and location" + REQUIRED);
		}
		catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	// Get trainees by Name Like
	@GetMapping("/traineeByNameRegex/{expr}")
	private ResponseEntity<TraineeResponse> getTraineewithNameregex(@PathVariable String expr) {

		try{
			if(isValidString(expr))
				return service.getTraineeByNameContaining(expr);
			else
				throw new IllegalArgumentException("Expression " + REQUIRED);
		}
		catch(IllegalArgumentException e)
		{
			logger.error(e.getMessage());
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}	
		
	}
	
	// Get trainee count by Name
	@GetMapping("/traineeCountByName/{name}")
	private int getTraineeCountThroughName(@PathVariable String name) {

		try {
			if(isValidString(name))
				return service.getTraineeCountByName(name);
			else
				throw new IllegalArgumentException("name " + REQUIRED);
		} 
		catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return 0;
		}
		
	}

}

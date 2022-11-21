/**
 * 
 */
package com.demo.mongodb.controller;

import static com.demo.mongodb.util.Constants.*;
import static com.demo.mongodb.util.InputDataValidation.dirnValidation;
import static com.demo.mongodb.util.InputDataValidation.isNegativeVal;
import static com.demo.mongodb.util.InputDataValidation.isNotValidDate;
import static com.demo.mongodb.util.InputDataValidation.isNotValidNumber;
import static com.demo.mongodb.util.InputDataValidation.isNotValidString;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.mongodb.dto.TraineeDTO;
import com.demo.mongodb.dto.TraineeResponse;
import com.demo.mongodb.entity.TraineeDAO;
import com.demo.mongodb.service.TraineeService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/**
 * @author nikhilgupta2
 *
 */
@RestController
@RequestMapping("/CRUDoperations")
@JsonInclude(Include.NON_NULL)
public class TraineeControllerMainCRUD {
	
	Logger logger = LoggerFactory.getLogger(TraineeService.class);

	
	@Autowired
	private TraineeService service;
	
	@GetMapping("/trainee")
	private ResponseEntity<TraineeResponse> getTrainees() {
		return service.getAllTrainees();
	}
	
	@GetMapping("/trainee/{tid}")
	private ResponseEntity<Optional<TraineeDAO>> getTraineeById(@PathVariable String tid) {

		try {
			if(isNotValidNumber(tid))
				throw new IllegalArgumentException(NUMBERFORM);
			
			Long traineeid = Long.parseLong(tid);

			return service.getTraineebyID(traineeid);
			}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
			} 
	}
	
	@GetMapping("/traineeWithPages")
	private ResponseEntity<TraineeResponse> getTraineeWithPages(
			@RequestParam(value="pageno", required=true, defaultValue ="1") int pn
	, @RequestParam(value="pagesiz", required=true,defaultValue = "1") int ps
	, @RequestParam(value="sortDirection", required=true,defaultValue = "ASC") String sortDirection
	, @RequestParam(value="sortby", required=true,defaultValue = "id") String sortParam) {
		
		TraineeResponse response=new TraineeResponse();

		try {
			if(isNegativeVal(pn, ps))
				throw new IllegalArgumentException(NUMBERFORM);
			if(dirnValidation(sortDirection))
				throw new IllegalArgumentException(DIRERROR);
			
			return service.getAllTraineesPages(pn,ps,sortDirection,sortParam);
		}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping("/trainee")
	private ResponseEntity<TraineeDAO> saveTrainee(@RequestBody TraineeDTO t ) throws ParseException {
		
	
		try {
			if(isNotValidDate(t.getJoinDate(), t.getTestDate()))
				throw new IllegalArgumentException(INVALIDATE);
	
			if((isNotValidString(t.getName())||(isNotValidString(t.getLocation()))))
				throw new IllegalArgumentException("name and location" + REQUIRED);
			
			if(isNotValidNumber(t.getId()))
				throw new IllegalArgumentException(NUMBERFORM);
			
			//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			LocalDate jd = LocalDate.parse(t.getJoinDate());
			LocalDate td = LocalDate.parse(t.getTestDate());
			
			Long tid = Long.parseLong(t.getId());

			return service.createTrainee(new TraineeDAO(tid,t.getName(),t.getLocation(),
					jd,td));
		}
		catch(IllegalArgumentException|DateTimeParseException e){
			logger.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PutMapping("/trainee")
	private ResponseEntity<TraineeDAO> updateTrainee(@RequestBody TraineeDTO t ) {
		
		try {
			if(isNotValidDate(t.getJoinDate(), t.getTestDate()))
				throw new IllegalArgumentException(INVALIDATE);
			if((isNotValidString(t.getName())||(isNotValidString(t.getLocation()))))
				throw new IllegalArgumentException("name and location" + REQUIRED);
			if(isNotValidNumber(t.getId()))
				throw new IllegalArgumentException(NUMBERFORM);
	
			//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			LocalDate jd = LocalDate.parse(t.getJoinDate());
			LocalDate td = LocalDate.parse(t.getTestDate());

			Long tid = Long.parseLong(t.getId());

			return service.updateTrainee(new TraineeDAO(tid,t.getName(),t.getLocation(),
					jd,td));
		}
		catch(IllegalArgumentException|DateTimeParseException e){
			logger.error(e.getMessage());
			return new ResponseEntity<>(null,HttpStatus.EXPECTATION_FAILED);		

			}
	}	
	
	/*
	 * @PutMapping("/trainee/{tid}") private Trainee
	 * updateTraineeNoCreate(@PathVariable int tid,@RequestBody Trainee t ) {
	 * t.setId(tid); return service.updateTrainee(t); }
	 */
	
	
	@DeleteMapping("/trainee/{tid}")
	private void  deleteTrainee(@PathVariable String tid) {
		
		try {
			if(isNotValidNumber(tid))
				throw new IllegalArgumentException(NUMBERFORM);
			
			Long traineeid = Long.parseLong(tid);

			service.deleteTraineebyID(traineeid);
		}
		catch(IllegalArgumentException e)
		{
			logger.error(e.getMessage());
		}
	}

}

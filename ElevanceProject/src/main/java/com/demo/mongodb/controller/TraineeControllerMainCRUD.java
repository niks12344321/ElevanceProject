/**
 * 
 */
package com.demo.mongodb.controller;

import static com.demo.mongodb.util.Constants.DIRERROR;
import static com.demo.mongodb.util.Constants.INVALIDATE;
import static com.demo.mongodb.util.Constants.NUMBERFORM;
import static com.demo.mongodb.util.Constants.STRINGINCORRECT;
import static com.demo.mongodb.util.InputDataValidation.dirnValidation;
import static com.demo.mongodb.util.InputDataValidation.isValidDate;
import static com.demo.mongodb.util.InputDataValidation.isValidNumber;
import static com.demo.mongodb.util.InputDataValidation.isValidString;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
	public ResponseEntity<TraineeResponse> getTrainees() {
		return service.getAllTrainees();
	}
	
	@GetMapping("/trainee/{tid}")
	private ResponseEntity<TraineeResponse> getTraineeById(@PathVariable String tid) {

		try {
			if(isValidNumber(tid))
				return service.getTraineebyID(Long.parseLong(tid));
			else
				throw new IllegalArgumentException(NUMBERFORM);
			}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
			} 
	}
	
	@GetMapping("/traineeWithPages")
	private ResponseEntity<TraineeResponse> getTraineeWithPages(
			@RequestParam(value="pageno", required=true, defaultValue ="1") String pn
	, @RequestParam(value="pagesiz", required=true,defaultValue = "1") String ps
	, @RequestParam(value="sortDirection", required=true,defaultValue = "ASC") String sortDirection
	, @RequestParam(value="sortby", required=true,defaultValue = "id") String sortParam
	, @RequestParam(value="idreq", required=false,defaultValue = "true") boolean idreq) {
		
		TraineeResponse response=new TraineeResponse();

		try {
			if(isValidNumber(pn)&&isValidNumber(ps))
			{
				if(dirnValidation(sortDirection))
					throw new IllegalArgumentException(DIRERROR);
				else
					return service.getAllTraineesPages(Integer.parseInt(pn),Integer.parseInt(ps),sortDirection,sortParam,idreq);
			}else 
				throw new IllegalArgumentException(NUMBERFORM);

		}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping("/trainee")
	public ResponseEntity<TraineeDTO> saveTrainee(@RequestBody TraineeDTO t ) throws ParseException {
		TraineeDAO newTrainee=new TraineeDAO();
	
		try {
			if(t.getJoinDate()!=null) {
				if(isValidDate(t.getJoinDate()))
					newTrainee.setJoinDate(LocalDate.parse(t.getJoinDate()));
				else
					throw new IllegalArgumentException(INVALIDATE);
			}
			if(t.getTestDate()!=null) {
				if(isValidDate(t.getTestDate()))
					newTrainee.setTestDate(LocalDate.parse(t.getTestDate()));
				else
					throw new IllegalArgumentException(INVALIDATE);
			}

			if(t.getName()!=null) {
				if(isValidString(t.getName()))
					newTrainee.setName(t.getName());
				else
					throw new IllegalArgumentException("name" + STRINGINCORRECT);
			}
			if(t.getLocation()!=null) {
				if(isValidString(t.getLocation()))
					newTrainee.setLocation(t.getLocation());
				else
					throw new IllegalArgumentException("location" + STRINGINCORRECT);
			}
			
			if(isValidNumber(t.getId()))
				newTrainee.setId(Long.parseLong(t.getId()));
			else
				throw new IllegalArgumentException(NUMBERFORM);

			return service.createTrainee(newTrainee);
		}
		catch(IllegalArgumentException|DateTimeParseException e){
			logger.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PutMapping("/trainee")
	private ResponseEntity<TraineeDTO> updateTrainee(@RequestBody TraineeDTO t ) {
		
		TraineeDAO newTrainee=new TraineeDAO();

		try {
			if(t.getJoinDate()!=null) {
				if(isValidDate(t.getJoinDate()))
					newTrainee.setJoinDate(LocalDate.parse(t.getJoinDate()));
				else
					throw new IllegalArgumentException(INVALIDATE);
			}
			if(t.getTestDate()!=null) {
				if(isValidDate(t.getTestDate()))
					newTrainee.setTestDate(LocalDate.parse(t.getTestDate()));
				else
					throw new IllegalArgumentException(INVALIDATE);
			}
			
			if(t.getName()!=null) {
				if(isValidString(t.getName()))
					newTrainee.setName(t.getName());
				else
					throw new IllegalArgumentException("name" + STRINGINCORRECT);
			}
			if(t.getLocation()!=null) {
				if(isValidString(t.getLocation()))
					newTrainee.setLocation(t.getLocation());
				else
					throw new IllegalArgumentException("location" + STRINGINCORRECT);
			}
			
			if(isValidNumber(t.getId()))
				newTrainee.setId(Long.parseLong(t.getId()));
			else
				throw new IllegalArgumentException(NUMBERFORM);

			return service.updateTrainee(newTrainee);
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
	private ResponseEntity<String>  deleteTrainee(@PathVariable String tid) {
		
		try {
			if(isValidNumber(tid))
				return service.deleteTraineebyID(Long.parseLong(tid));
			else
				throw new IllegalArgumentException(NUMBERFORM);
		}
		catch(IllegalArgumentException e)
		{
			logger.error(e.getMessage());
			return new ResponseEntity<String>("Invalid input format", HttpStatus.BAD_REQUEST);
		}
		
	}

}

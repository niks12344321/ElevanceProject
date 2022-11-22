/**
 * 
 */
package com.demo.mongodb.controller;

import static com.demo.mongodb.util.Constants.DIRERROR;
import static com.demo.mongodb.util.Constants.INVALIDATE;
import static com.demo.mongodb.util.Constants.NUMBERFORM;
import static com.demo.mongodb.util.Constants.REQUIRED;
import static com.demo.mongodb.util.InputDataValidation.*;

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
	public ResponseEntity<TraineeResponse> getTrainees() {
		return service.getAllTrainees();
	}
	
	@GetMapping("/trainee/{tid}")
	private ResponseEntity<Optional<TraineeDAO>> getTraineeById(@PathVariable String tid) {

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
		
	
		try {
			if(isValidDate(t.getJoinDate(), t.getTestDate()))
			{
				if((isValidString(t.getName())&&(isValidString(t.getLocation()))))
				{
					if(isValidNumber(t.getId()))
					{
						LocalDate jd = LocalDate.parse(t.getJoinDate());
						LocalDate td = LocalDate.parse(t.getTestDate());
						

						return service.createTrainee(new TraineeDAO(Long.parseLong(t.getId()),t.getName(),t.getLocation(),
								jd,td));
					}
					else
						throw new IllegalArgumentException(NUMBERFORM);
				}
				else
					throw new IllegalArgumentException("name and location" + REQUIRED);
			}
			else
				throw new IllegalArgumentException(INVALIDATE);

			//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
		}
		catch(IllegalArgumentException|DateTimeParseException e){
			logger.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PutMapping("/trainee")
	private ResponseEntity<TraineeDTO> updateTrainee(@RequestBody TraineeDTO t ) {
		
		try {
			if(isValidDate(t.getJoinDate(), t.getTestDate()))
			{
				if((isValidString(t.getName())&&(isValidString(t.getLocation()))))
				{
					if(isValidNumber(t.getId()))
					{
						LocalDate jd = LocalDate.parse(t.getJoinDate());
						LocalDate td = LocalDate.parse(t.getTestDate());
						
						Long tid = Long.parseLong(t.getId());

						return service.updateTrainee(new TraineeDAO(tid,t.getName(),t.getLocation(),
								jd,td));
					}
					else
						throw new IllegalArgumentException(NUMBERFORM);
				}
				else
					throw new IllegalArgumentException("name and location" + REQUIRED);
			}
			else
				throw new IllegalArgumentException(INVALIDATE);
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
			if(isValidNumber(tid))
				service.deleteTraineebyID(Long.parseLong(tid));
			else
				throw new IllegalArgumentException(NUMBERFORM);			
		}
		catch(IllegalArgumentException e)
		{
			logger.error(e.getMessage());
		}
	}

}

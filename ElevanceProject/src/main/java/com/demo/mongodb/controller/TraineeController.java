/**
 * 
 */
package com.demo.mongodb.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.mongodb.entity.Trainee;
import com.demo.mongodb.entity.TraineeDAO;
import com.demo.mongodb.service.TraineeResponse;
import com.demo.mongodb.service.TraineeService;
import static com.demo.mongodb.util.InputDataValidation.*;
import static com.demo.mongodb.util.Constants.*;
/**
 * @author nikhilgupta2
 *
 */
@RestController
public class TraineeController {
	
	Logger logger = LoggerFactory.getLogger(TraineeService.class);

	
	@Autowired
	private TraineeService service;
	
	@GetMapping("/trainee")
	private ResponseEntity<TraineeResponse> getTrainees() {
		return service.getAllTrainees();
	}
	
	@GetMapping("/trainee/{tid}")
	private ResponseEntity<Optional<Trainee>> getTraineeById(@PathVariable long tid) {

		try {
			if(isNegativeId(tid))
				throw new IllegalArgumentException(NEGVAL);
			
			return service.getTraineebyID(tid);
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
	, @RequestParam(value="dir", required=true,defaultValue = "1") int dir
	, @RequestParam(value="sortby", required=true,defaultValue = "id") String s) {
		
		TraineeResponse response=new TraineeResponse();

		try {
			if(isNegativeVal(pn, ps))
				throw new IllegalArgumentException(NEGVAL);
			if(dirnValidation(dir))
				throw new IllegalArgumentException(DIRERROR);
			
			return service.getAllTraineesPages(pn,ps,dir,s);
		}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
	}
	
	//Get trainees with sorting. 1 for asc, -1 for desc.
	@GetMapping("/traineeWithSort/{dir}/{by}")
	private ResponseEntity<TraineeResponse> getTraineeWithSorting(@PathVariable int dir,@PathVariable String by) {

		TraineeResponse response=new TraineeResponse();

		try {
			if(dirnValidation(dir))
				throw new IllegalArgumentException(DIRERROR);
			return service.getSortedTrainees(dir,by);
		}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
	}
	
	// Get trainees with ids greater than x
	@GetMapping("/traineeGreaterThan/{gt}")
	private ResponseEntity<TraineeResponse> getTraineeGreater(@PathVariable int gt) {

		TraineeResponse response=new TraineeResponse();

		try {
			if(isNegativeVal(gt))
				throw new IllegalArgumentException(NEGVAL);
			return service.getTraineesGreaterThan(gt);
		}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
			}
	}

	// Get trainees by Name
	@GetMapping("/traineeByName/{nam}")
	private ResponseEntity<TraineeResponse> getTraineeThroughName(@PathVariable String nam) {

		TraineeResponse response=new TraineeResponse();
		
		try {
			if(isNotValidString(nam))
				throw new IllegalArgumentException("name " + REQUIRED);
			return service.getTraineeByName(nam);
		}
		catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	// Get trainees by Name display only Location
	@GetMapping("/traineeByNameDispLocation/{nam}")
	private ResponseEntity<TraineeResponse> getTraineeThroughNameDispLocation(@PathVariable String nam) {

		TraineeResponse response=new TraineeResponse();

		try {
			if(isNotValidString(nam))
				throw new IllegalArgumentException("name " + REQUIRED);
			return service.getTraineeByNameDispLoc(nam);
		}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
			}
	}

	// Get trainees by Name and Location
	@GetMapping("/traineeByNameAndLoc/{nam}/{loc}")
	private ResponseEntity<TraineeResponse> getTraineeThroughNameLocation(@PathVariable String nam,@PathVariable String loc) {
		TraineeResponse response=new TraineeResponse();

		try {
			if((isNotValidString(nam)||(isNotValidString(loc))))
				throw new IllegalArgumentException("name and location" + REQUIRED);
			return service.getTraineeByNameAndLocation(nam,loc);
		}
		catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	// Get trainees by Name Like
	@GetMapping("/traineeByNameRegex/{expr}")
	private ResponseEntity<TraineeResponse> getTraineewithNameregex(@PathVariable String expr) {

		return service.getTraineeByNameContaining(expr);
	}
	
	// Get trainee count by Name
	@GetMapping("/traineeCountByName/{nam}")
	private int getTraineeCountThroughName(@PathVariable String nam) {

		try {
			if(isNotValidString(nam))
				throw new IllegalArgumentException("name " + REQUIRED);
			return service.getTraineeCountByName(nam);
		} 
		catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return 0;
		}
		
	}
	
	@PostMapping("/trainee")
	private ResponseEntity<Trainee> saveTrainee(@RequestBody TraineeDAO t ) throws ParseException {
		
	
		try {
			if(isNotValidDate(t.getJoinDate(), t.getTestDate()))
				throw new IllegalArgumentException(INVALIDATE);
	
			if((isNotValidString(t.getName())||(isNotValidString(t.getLocation()))))
				throw new IllegalArgumentException("name and location" + REQUIRED);
			
			if(isNegativeId(t.getId()))
				throw new IllegalArgumentException(NEGVAL);
			
			//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			LocalDate jd = LocalDate.parse(t.getJoinDate());
			LocalDate td = LocalDate.parse(t.getTestDate());

			return service.createTrainee(new Trainee(t.getId(),t.getName(),t.getLocation(),
					jd,td));
		}
		catch(IllegalArgumentException|DateTimeParseException e){
			logger.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PutMapping("/trainee")
	private ResponseEntity<Trainee> updateTrainee(@RequestBody TraineeDAO t ) {
		
		try {
			if(isNotValidDate(t.getJoinDate(), t.getTestDate()))
				throw new IllegalArgumentException(INVALIDATE);
			if((isNotValidString(t.getName())||(isNotValidString(t.getLocation()))))
				throw new IllegalArgumentException("name and location" + REQUIRED);
			if(isNegativeId(t.getId()))
				throw new IllegalArgumentException(NEGVAL);
	
			//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			LocalDate jd = LocalDate.parse(t.getJoinDate());
			LocalDate td = LocalDate.parse(t.getTestDate());

			return service.updateTrainee(new Trainee(t.getId(),t.getName(),t.getLocation(),
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
	private void  deleteTrainee(@PathVariable long tid) {
		
		try {
			if(isNegativeId(tid))
				throw new IllegalArgumentException(NEGVAL);
			service.deleteTraineebyID(tid);
		}
		catch(IllegalArgumentException e)
		{
			logger.error(e.getMessage());
		}
	}

}

/**
 * 
 */
package com.demo.mongodb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.mongodb.entity.Trainee;
import com.demo.mongodb.service.TraineeService;

/**
 * @author nikhilgupta2
 *
 */
@RestController
public class TraineeController {
	
	@Autowired
	private TraineeService service;
	
	@GetMapping("/trainee")
	private List<Trainee> getTrainees() {
		return service.getAllTrainees();
	}
	
	@GetMapping("/trainee/{tid}")
	private Optional<Trainee> getTraineeById(@PathVariable int tid) {

		return service.getTraineebyID(tid);
	}
	
	@PostMapping("/trainee")
	private Trainee saveTrainee(@RequestBody Trainee t ) {
		return service.createTrainee(t);
	}
	
	@PutMapping("/trainee")
	private Trainee updateTrainee(@RequestBody Trainee t ) {
		return service.updateTrainee(t);
	}	
	
	/*
	 * @PutMapping("/trainee/{tid}") private Trainee
	 * updateTraineeNoCreate(@PathVariable int tid,@RequestBody Trainee t ) {
	 * t.setId(tid); return service.updateTrainee(t); }
	 */
	
	
	@DeleteMapping("/trainee/{tid}")
	private HttpStatus  deleteTrainee(@PathVariable int tid) {
		service.deleteTraineebyID(tid);
		return HttpStatus.OK;
	}

}

/**
 * 
 */
package com.demo.mongodb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/traineeWithPages")
	private List<Trainee> getTraineeWithPages(@RequestParam int pn, @RequestParam int ps) {

		return service.getAllTraineesPages(pn,ps);
	}
	
	//Get trainees with sorting. 1 for asc, -1 for desc.
	@GetMapping("/traineeWithSort/{dir}")
	private List<Trainee> getTraineeWithSorting(@PathVariable int dir) {

		return service.getSortedTrainees(dir);
	}
	
	// Get trainees with ids greater than x
	@GetMapping("/traineeGreaterThan/{gt}")
	private List<Trainee> getTraineeGreater(@PathVariable int gt) {

		return service.getTraineesGreaterThan(gt);
	}

	// Get trainees by Name
	@GetMapping("/traineeByName/{nam}")
	private List<Trainee> getTraineeThroughName(@PathVariable String nam) {

		return service.getTraineeByName(nam);
	}
	
	// Get trainees by Name display only Location
		@GetMapping("/traineeByNameDispLocation/{nam}")
		private List<Trainee> getTraineeThroughNameDispLocation(@PathVariable String nam) {

			return service.getTraineeByNameDispLoc(nam);
		}

	// Get trainees by Name and Location
	@GetMapping("/traineeByNameAndLoc/{nam}/{loc}")
	private List<Trainee> getTraineeThroughNameLocation(@PathVariable String nam,@PathVariable String loc) {

		return service.getTraineeByNameAndLocation(nam,loc);
	}
	
	// Get trainees by Name Like
	@GetMapping("/traineeByNameRegex/{expr}")
	private List<Trainee> getTraineewithNameregex(@PathVariable String expr) {

		return service.getTraineeByNameContaining(expr);
	}
	
	// Get trainee count by Name
		@GetMapping("/traineeCountByName/{nam}")
		private int getTraineeCountThroughName(@PathVariable String nam) {

			return service.getTraineeCountByName(nam);
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
	private String deleteTrainee(@PathVariable int tid) {
		return service.deleteTraineebyID(tid);
		
	}

}

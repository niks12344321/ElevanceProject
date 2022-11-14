/**
 * 
 */
package com.demo.mongodb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.mongodb.entity.Trainee;
import com.demo.mongodb.repository.TraineeRepository;

/**
 * @author nikhilgupta2
 *
 */
@Service
@Transactional
public class TraineeService {

	@Autowired
	private TraineeRepository trepo;
	
	public Trainee createTrainee(Trainee t)
	{
		return trepo.save(t);
	}
	
	public Trainee updateTrainee(Trainee t) {

		return trepo.save(t);
	}
	
	/*
	 * public Trainee updateTraineeNoCreate(Trainee t) throws Exception {
	 * 
	 * Optional<Trainee> traineeVal = this.trepo.findById(t.getId());
	 * 
	 * if (traineeVal.isPresent()) { Trainee updatingTrainee = traineeVal.get();
	 * updatingTrainee.setId(t.getId());
	 * updatingTrainee.setLocation(t.getLocation());
	 * updatingTrainee.setName(t.getName()); trepo.save(updatingTrainee); return
	 * updatingTrainee; } else { throw new Exception("Record not found with id : " +
	 * t.getId()); }
	 * 
	 * }
	 */

	
	public List<Trainee> getAllTrainees() {

		List<Trainee> trList = new ArrayList<Trainee>();
		trepo.findAll().forEach(traineeA -> trList.add(traineeA));
		return trList;
	}
	
	public Optional<Trainee> getTraineebyID(long id) {
		
		return trepo.findById(id);
	}
	
	public void deleteTraineebyID(long id) {
		trepo.deleteById(id);
	}
}

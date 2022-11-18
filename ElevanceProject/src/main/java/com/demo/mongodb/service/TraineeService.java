/**
 * 
 */
package com.demo.mongodb.service;

import static com.demo.mongodb.util.Constants.DELETERROR;
import static com.demo.mongodb.util.Constants.GTERROR;
import static com.demo.mongodb.util.Constants.NAMEPATTERNERROR;
import static com.demo.mongodb.util.Constants.NEGVAL;
import static com.demo.mongodb.util.Constants.NOID;
import static com.demo.mongodb.util.Constants.NONAMELOCERROR;
import static com.demo.mongodb.util.Constants.NONAMERROR;
import static com.demo.mongodb.util.InputDataValidation.isNegativeId;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.mongodb.entity.Trainee;
import com.demo.mongodb.repository.ServiceRepository;
import com.demo.mongodb.repository.TraineeRepository;

/**
 * @author nikhilgupta2
 *
 */


@Service
@Transactional
public class TraineeService implements ServiceRepository {


	@Autowired
	private TraineeRepository trepo;

	
	Logger logger = LoggerFactory.getLogger(TraineeService.class);
	
	@Override
	public ResponseEntity<Trainee> createTrainee(Trainee t)
	{			
			return new ResponseEntity<>(trepo.save(t), HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<Trainee> updateTrainee(Trainee t) {

		return new ResponseEntity<>(trepo.save(t), HttpStatus.CREATED);
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

	@Override
	public ResponseEntity<TraineeResponse> getAllTrainees() {

		List<Trainee> trList = new ArrayList<Trainee>();
		trepo.findAll().forEach(traineeA -> trList.add(traineeA));
		
		List<Trainee> traineeList= trList;
		TraineeResponse response=new TraineeResponse();
		response.setTraineesList(traineeList);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Optional<Trainee>> getTraineebyID(long id) {
		
		try {
			if(trepo.findById(id).isEmpty())
				throw new NoSuchElementException(NOID + id);
			
			return new ResponseEntity<>(trepo.findById(id), HttpStatus.CREATED);

			}
		catch(NoSuchElementException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		
	}
	
	@Override
	public ResponseEntity<TraineeResponse> getAllTraineesPages(int pnum, int psize,int dir, String v) {
				
		TraineeResponse response=new TraineeResponse();

		if(dir==-1) {
			PageRequest pageRequest = PageRequest.of(pnum-1, psize,Sort.by(Sort.Direction.DESC,v));
			List<Trainee> traineeList= trepo.findAll(pageRequest).getContent();
			response.setTraineesList(traineeList);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			PageRequest pageRequest = PageRequest.of(pnum-1, psize,Sort.by(Sort.Direction.ASC,v));
			List<Trainee> traineeList= trepo.findAll(pageRequest).getContent();
			response.setTraineesList(traineeList);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
			
	}
	
	
	@Override
	public ResponseEntity<TraineeResponse>  getSortedTrainees(int dir,String v) {
		
		TraineeResponse response=new TraineeResponse();

		if(dir==-1) {
			Sort sort = Sort.by(Sort.Direction.DESC,v);
			List<Trainee> traineeList= trepo.findAll(sort);
			response.setTraineesList(traineeList);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			Sort sort = Sort.by(Sort.Direction.ASC,v);
			List<Trainee> traineeList= trepo.findAll(sort);
			response.setTraineesList(traineeList);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}		
	}
	
	@Override
	public ResponseEntity<TraineeResponse> getTraineesGreaterThan(int gt) {
		
		TraineeResponse response=new TraineeResponse();

		try {
			if(trepo.findByIdGreaterThan(gt).isEmpty())
				throw new NoSuchElementException(GTERROR + gt);
			
			List<Trainee> traineeList= trepo.findByIdGreaterThan(gt);
			response.setTraineesList(traineeList);
			return new ResponseEntity<>(response,HttpStatus.FOUND);
			}
		catch(NoSuchElementException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
			}
	}
	
	@Override
	public ResponseEntity<TraineeResponse> getTraineeByName(String nam){
		
		TraineeResponse response=new TraineeResponse();
		try {
			if (trepo.findByName(nam).isEmpty())
				throw new NoSuchElementException(NONAMERROR);
			
			List<Trainee> traineeList= trepo.findByName(nam);
			response.setTraineesList(traineeList);
			return new ResponseEntity<>(response,HttpStatus.FOUND);
			
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<TraineeResponse> getTraineeByNameAndLocation(String nam, String loc) {
	
		TraineeResponse response=new TraineeResponse();

		try {
			if (trepo.findByNameAndLocation(nam, loc).isEmpty())
				throw new NoSuchElementException(NONAMELOCERROR);
			
			List<Trainee> traineeList= trepo.findByNameAndLocation(nam, loc);
			response.setTraineesList(traineeList);
			return new ResponseEntity<>(response,HttpStatus.FOUND);
		} 
		catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}
	}
	
	@Override
	public ResponseEntity<TraineeResponse> getTraineeByNameContaining(String expr) {
		
		TraineeResponse response=new TraineeResponse();

		try {
			if (trepo.findByNameLike(expr).isEmpty())
				throw new NoSuchElementException(NAMEPATTERNERROR);
			
			List<Trainee> traineeList= trepo.findByNameLike(expr);
			response.setTraineesList(traineeList);
			return new ResponseEntity<>(response,HttpStatus.FOUND);
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}
	}
	
	@Override
	public HttpStatus deleteTraineebyID(long id) {
		
		try {
			if(isNegativeId(id))
				throw new IllegalArgumentException(NEGVAL);
			if(trepo.findById(id).isPresent())
				{
				trepo.deleteById(id);
				logger.info("Deleted Successfully");
				return HttpStatus.OK;
				}
			else 
				throw new NoSuchElementException(DELETERROR);
			}
		catch(NoSuchElementException e)
			{
				logger.error(e.getMessage());
				return HttpStatus.BAD_REQUEST;
			}
	}

	@Override
	public int getTraineeCountByName(String nam) {
				
		return trepo.countByName(nam);	
	}

	@Override
	public  ResponseEntity<TraineeResponse> getTraineeByNameDispLoc(String nam) {

		TraineeResponse response=new TraineeResponse();

		try {			
			if(trepo.findByName(nam).isEmpty())		
				throw new NoSuchElementException(NONAMERROR);
			
			List<Trainee> traineeList= trepo.findByNameIncludeLocation(nam);
			response.setTraineesList(traineeList);
			return new ResponseEntity<>(response,HttpStatus.FOUND);
			}
		catch(NoSuchElementException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
			}
	}


}

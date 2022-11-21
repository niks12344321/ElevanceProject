/**
 * 
 */
package com.demo.mongodb.service;

import static com.demo.mongodb.util.Constants.*;
import static com.demo.mongodb.util.InputDataValidation.isNegativeId;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.mongodb.dto.TraineeResponse;
import com.demo.mongodb.entity.TraineeDAO;
import com.demo.mongodb.repository.TraineeRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author nikhilgupta2
 *
 */


@Service
@Transactional
@JsonInclude(Include.NON_NULL)
public class TraineeService implements TraineeServiceInterface {


	@Autowired
	private TraineeRepository trepo;

	
	Logger logger = LoggerFactory.getLogger(TraineeService.class);
	
	@Override
	public ResponseEntity<TraineeDAO> createTrainee(TraineeDAO traineedao)
	{			
		TraineeDAO tCreated = trepo.save(traineedao);
		try {
		if(Objects.isNull(tCreated)) {
			throw new DataRetrievalFailureException(CREATIONFAILURERROR);
		}
		else
			return new ResponseEntity<>(tCreated, HttpStatus.CREATED);
		}
		catch(DataRetrievalFailureException e){
			
			logger.error(e.getMessage());
			return new ResponseEntity<>(tCreated, HttpStatus.INTERNAL_SERVER_ERROR);

		}	}
	
	@Override
	public ResponseEntity<TraineeDAO> updateTrainee(TraineeDAO traineedao) {

		TraineeDAO tUpdated = trepo.save(traineedao);
		try {
		if(Objects.isNull(tUpdated)) {
			throw new DataRetrievalFailureException(CREATIONFAILURERROR);
		}
		else
			return new ResponseEntity<>(tUpdated, HttpStatus.CREATED);
		}
		catch(DataRetrievalFailureException e){
			
			logger.error(e.getMessage());
			return new ResponseEntity<>(tUpdated, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
	}
	
	/*
	 * public Trainee updateTraineeNoCreate(Trainee traineedao) throws Exception {
	 * 
	 * Optional<Trainee> traineeVal = this.trepo.findById(traineedao.getId());
	 * 
	 * if (traineeVal.isPresent()) { Trainee updatingTrainee = traineeVal.get();
	 * updatingTrainee.setId(traineedao.getId());
	 * updatingTrainee.setLocation(traineedao.getLocation());
	 * updatingTrainee.setName(traineedao.getName()); trepo.save(updatingTrainee); return
	 * updatingTrainee; } else { throw new Exception("Record not found with id : " +
	 * traineedao.getId()); }
	 * 
	 * }
	 */

	@Override
	public ResponseEntity<TraineeResponse> getAllTrainees() {

		List<TraineeDAO> trList = new ArrayList<TraineeDAO>();
		trepo.findAll().forEach(traineeA -> trList.add(traineeA));
		
		List<TraineeDAO> traineeList= trList;
		TraineeResponse response=new TraineeResponse();
		response.setTraineesList(traineeList);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<Optional<TraineeDAO>> getTraineebyID(long id) {
		
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
	public ResponseEntity<TraineeResponse> getAllTraineesPages(int pnum, int psize,String sortDirection, String sortParam) {
				
		TraineeResponse response=new TraineeResponse();

		if(sortDirection.equalsIgnoreCase("DESC")) {
			PageRequest pageRequest = PageRequest.of(pnum-1, psize,Sort.by(Sort.Direction.DESC,sortParam));
			List<TraineeDAO> traineeList= trepo.findAll(pageRequest).getContent();
			response.setTraineesList(traineeList);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else if(sortDirection.equalsIgnoreCase("ASC")) {
			PageRequest pageRequest = PageRequest.of(pnum-1, psize,Sort.by(Sort.Direction.ASC,sortParam));
			List<TraineeDAO> traineeList= trepo.findAll(pageRequest).getContent();
			response.setTraineesList(traineeList);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else return new ResponseEntity<>(response,HttpStatus.PRECONDITION_FAILED);
	}
	
	
	@Override
	public ResponseEntity<TraineeResponse>  getSortedTrainees(String sortDirection,String sortParam) {
		
		TraineeResponse response=new TraineeResponse();

		if(sortDirection.equalsIgnoreCase("DESC")) {
			Sort sort = Sort.by(Sort.Direction.DESC,sortParam);
			List<TraineeDAO> traineeList= trepo.findAll(sort);
			response.setTraineesList(traineeList);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else if(sortDirection.equalsIgnoreCase("ASC")){
			Sort sort = Sort.by(Sort.Direction.ASC,sortParam);
			List<TraineeDAO> traineeList= trepo.findAll(sort);
			response.setTraineesList(traineeList);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}		
		else return new ResponseEntity<>(response,HttpStatus.PRECONDITION_FAILED);

	}
	
	@Override
	public ResponseEntity<TraineeResponse> getTraineesGreaterThan(int gt) {
		
		TraineeResponse response=new TraineeResponse();

		try {
			if(trepo.findByIdGreaterThan(gt).isEmpty())
				throw new NoSuchElementException(GTERROR + gt);
			
			List<TraineeDAO> traineeList= trepo.findByIdGreaterThan(gt);
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
	public ResponseEntity<TraineeResponse> getTraineeByName(String name){
		
		TraineeResponse response=new TraineeResponse();
		try {
			if (trepo.findByName(name).isEmpty())
				throw new NoSuchElementException(NONAMERROR);
			
			List<TraineeDAO> traineeList= trepo.findByName(name);
			response.setTraineesList(traineeList);
			return new ResponseEntity<>(response,HttpStatus.FOUND);
			
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<TraineeResponse> getTraineeByNameAndLocation(String name, String loc) {
	
		TraineeResponse response=new TraineeResponse();

		try {
			if (trepo.findByNameAndLocation(name, loc).isEmpty())
				throw new NoSuchElementException(NONAMELOCERROR);
			
			List<TraineeDAO> traineeList= trepo.findByNameAndLocation(name, loc);
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
			
			List<TraineeDAO> traineeList= trepo.findByNameLike(expr);
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
	public int getTraineeCountByName(String name) {
				
		return trepo.countByName(name);	
	}

	@Override
	public  ResponseEntity<TraineeResponse> getTraineeByNameDispLoc(String name) {

		TraineeResponse response=new TraineeResponse();

		try {			
			if(trepo.findByName(name).isEmpty())		
				throw new NoSuchElementException(NONAMERROR);
			
			List<TraineeDAO> traineeList= trepo.findByNameIncludeLocation(name);
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

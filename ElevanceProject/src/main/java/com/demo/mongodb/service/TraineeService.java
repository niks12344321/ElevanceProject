/**
 * 
 */
package com.demo.mongodb.service;

import static com.demo.mongodb.util.Constants.*;
import static com.demo.mongodb.util.InputDataValidation.isNegativeId;

import java.time.LocalDate;
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

import com.demo.mongodb.dto.TraineeDTO;
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
	public ResponseEntity<TraineeDTO> createTrainee(TraineeDAO traineedao)
	{			
		
		TraineeDAO tCreated = trepo.save(traineedao);
		TraineeDTO tReturn = new TraineeDTO();

		try {
			if(Objects.isNull(tCreated)) {
				throw new DataRetrievalFailureException(CREATIONFAILURERROR);
			}
			else
			{
				tReturn.setId(String.valueOf(tCreated.getId()));
				tReturn.setName(tCreated.getName());
				tReturn.setLocation(tCreated.getLocation());
				tReturn.setJoinDate(tCreated.getJoinDate().toString());
				tReturn.setTestDate(tCreated.getTestDate().toString());
				
				return new ResponseEntity<>(tReturn, HttpStatus.CREATED);				
			}
		}
		catch(DataRetrievalFailureException e){
			
			logger.error(e.getMessage());
			return new ResponseEntity<>(tReturn, HttpStatus.INTERNAL_SERVER_ERROR);

		}	
		}
	
	@Override
	public ResponseEntity<TraineeDTO> updateTrainee(TraineeDAO traineedao) {

		TraineeDAO tCreated = trepo.save(traineedao);
		TraineeDTO tReturn = new TraineeDTO();

		try {
			if(Objects.isNull(tCreated)) {
				throw new DataRetrievalFailureException(CREATIONFAILURERROR);
			}
			else
			{
				tReturn.setId(String.valueOf(traineedao.getId()));
				tReturn.setName(traineedao.getName());
				tReturn.setLocation(traineedao.getLocation());
				tReturn.setJoinDate(traineedao.getJoinDate().toString());
				tReturn.setTestDate(tCreated.getTestDate().toString());
				
				return new ResponseEntity<>(tReturn, HttpStatus.CREATED);				
			}
		}
		catch(DataRetrievalFailureException e){
			
			logger.error(e.getMessage());
			return new ResponseEntity<>(tReturn, HttpStatus.INTERNAL_SERVER_ERROR);

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
		
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();

		trepo.findAll().forEach(traineeA ->  {
			TraineeDTO tReturn = new TraineeDTO();	

		    tReturn.setId(String.valueOf(traineeA.getId()));
			tReturn.setName(traineeA.getName());
			tReturn.setLocation(traineeA.getLocation());
			if(traineeA.getJoinDate() != null)
				tReturn.setJoinDate(traineeA.getJoinDate().toString());
		
			if(traineeA.getJoinDate() != null)
				tReturn.setTestDate(traineeA.getTestDate().toString());
		
			trList.add(tReturn);
		});
		
		List<TraineeDTO> traineeList= trList;
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
	public ResponseEntity<TraineeResponse> getAllTraineesPages(int pnum, int psize,String sortDirection, String sortParam, Boolean idreq) {
		
		int i=0;
		TraineeResponse response=new TraineeResponse();
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();
		
		if(sortDirection.equalsIgnoreCase("DESC")) {
			PageRequest pageRequest = PageRequest.of(pnum-1, psize,Sort.by(Sort.Direction.DESC,sortParam));
			List<TraineeDAO> traineeList= trepo.findAll(pageRequest).getContent();
			
			for(TraineeDAO traineeA : traineeList) {
				
				TraineeDTO tReturn = new TraineeDTO();	

			    tReturn.setId(String.valueOf(traineeA.getId()));
				tReturn.setName(traineeA.getName());
				tReturn.setLocation(traineeA.getLocation());
				if(traineeA.getJoinDate() != null)
					tReturn.setJoinDate(traineeA.getJoinDate().toString());
				
				if(traineeA.getJoinDate() != null)
					tReturn.setTestDate(traineeA.getTestDate().toString());
		
				trList.add(tReturn);
			}
			
			System.out.println(trList);

			response.setTraineesList(trList);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else if(sortDirection.equalsIgnoreCase("ASC")) {
			PageRequest pageRequest = PageRequest.of(pnum-1, psize,Sort.by(Sort.Direction.ASC,sortParam));
			List<TraineeDAO> traineeList= trepo.findAll(pageRequest).getContent();

			for(TraineeDAO traineeA : traineeList) 
			{
				//TraineeDTO tReturn = new TraineeDTO(String.valueOf(traineeA.getId()),traineeA.getName(),traineeA.getLocation());	
				TraineeDTO tReturn = new TraineeDTO();	

			    tReturn.setId(String.valueOf(traineeA.getId()));
				tReturn.setName(traineeA.getName());
				tReturn.setLocation(traineeA.getLocation());
				if(traineeA.getJoinDate() != null)
					tReturn.setJoinDate(traineeA.getJoinDate().toString());
				
				if(traineeA.getJoinDate() != null)
					tReturn.setTestDate(traineeA.getTestDate().toString());
			
				trList.add(tReturn);
			}
			
			response.setTraineesList(trList);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else return new ResponseEntity<>(response,HttpStatus.PRECONDITION_FAILED);
	}
	
	
	@Override
	public ResponseEntity<TraineeResponse>  getSortedTrainees(String sortDirection,String sortParam) {
		
		TraineeResponse response=new TraineeResponse();
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();

		if(sortDirection.equalsIgnoreCase("DESC")) {
			Sort sort = Sort.by(Sort.Direction.DESC,sortParam);
			List<TraineeDAO> traineeList= trepo.findAll(sort);

			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = new TraineeDTO();	

			    tReturn.setId(String.valueOf(traineeA.getId()));
				tReturn.setName(traineeA.getName());
				tReturn.setLocation(traineeA.getLocation());
				if(traineeA.getJoinDate() != null)
					tReturn.setJoinDate(traineeA.getJoinDate().toString());
				
				if(traineeA.getJoinDate() != null)
					tReturn.setTestDate(traineeA.getTestDate().toString());
				trList.add(tReturn);
				});
			response.setTraineesList(trList);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else if(sortDirection.equalsIgnoreCase("ASC")){
			Sort sort = Sort.by(Sort.Direction.ASC,sortParam);
			List<TraineeDAO> traineeList= trepo.findAll(sort);
			
			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = new TraineeDTO();	

			    tReturn.setId(String.valueOf(traineeA.getId()));
				tReturn.setName(traineeA.getName());
				tReturn.setLocation(traineeA.getLocation());
				if(traineeA.getJoinDate() != null)
					tReturn.setJoinDate(traineeA.getJoinDate().toString());
				
				if(traineeA.getJoinDate() != null)
					tReturn.setTestDate(traineeA.getTestDate().toString());
			
				trList.add(tReturn);
				});
			response.setTraineesList(trList);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}		
		else return new ResponseEntity<>(response,HttpStatus.PRECONDITION_FAILED);

	}
	
	@Override
	public ResponseEntity<TraineeResponse> getTraineesGreaterThan(int gt) {
		
		TraineeResponse response=new TraineeResponse();
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();

		try {
			if(trepo.findByIdGreaterThan(gt).isEmpty())
				throw new NoSuchElementException(GTERROR + gt);
			
			List<TraineeDAO> traineeList= trepo.findByIdGreaterThan(gt);
			
			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = new TraineeDTO();	

			    tReturn.setId(String.valueOf(traineeA.getId()));
				tReturn.setName(traineeA.getName());
				tReturn.setLocation(traineeA.getLocation());
				if(traineeA.getJoinDate() != null)
					tReturn.setJoinDate(traineeA.getJoinDate().toString());
		
				if(traineeA.getJoinDate() != null)
					tReturn.setTestDate(traineeA.getTestDate().toString());
			
				trList.add(tReturn);
				});
			response.setTraineesList(trList);
			return new ResponseEntity<>(response,HttpStatus.FOUND);
			}
		catch(NoSuchElementException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
			}
	}
	
	//Get trainees with joining date greater than.
	@Override
	public ResponseEntity<TraineeResponse> getTraineesGreaterThanDate(LocalDate joinDate) {
		
		TraineeResponse response=new TraineeResponse();
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();

		try {
			if(trepo.findByJoinDateGreaterThanEqual(joinDate).isEmpty())
				throw new NoSuchElementException(GTERROR + joinDate);
			
			List<TraineeDAO> traineeList= trepo.findByJoinDateGreaterThanEqual(joinDate);
			
			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = new TraineeDTO();	

			    tReturn.setId(String.valueOf(traineeA.getId()));
				tReturn.setName(traineeA.getName());
				tReturn.setLocation(traineeA.getLocation());
				if(traineeA.getJoinDate() != null)
					tReturn.setJoinDate(traineeA.getJoinDate().toString());
			
				if(traineeA.getJoinDate() != null)
					tReturn.setTestDate(traineeA.getTestDate().toString());
		
				trList.add(tReturn);
				});
			response.setTraineesList(trList);
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
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();
		
		try {
			if (trepo.findByName(name).isEmpty())
				throw new NoSuchElementException(NONAMERROR);
			
			List<TraineeDAO> traineeList= trepo.findByName(name);
			
			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = new TraineeDTO();	

			    tReturn.setId(String.valueOf(traineeA.getId()));
				tReturn.setName(traineeA.getName());
				tReturn.setLocation(traineeA.getLocation());
				if(traineeA.getJoinDate() != null)
					tReturn.setJoinDate(traineeA.getJoinDate().toString());

				if(traineeA.getJoinDate() != null)
					tReturn.setTestDate(traineeA.getTestDate().toString());

				trList.add(tReturn);
				});
			response.setTraineesList(trList);
			return new ResponseEntity<>(response,HttpStatus.FOUND);
			
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<TraineeResponse> getTraineeByNameAndLocation(String name, String loc) {
	
		TraineeResponse response=new TraineeResponse();
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();
		
		try {
			if (trepo.findByNameAndLocation(name, loc).isEmpty())
				throw new NoSuchElementException(NONAMELOCERROR);
			
			List<TraineeDAO> traineeList= trepo.findByNameAndLocation(name, loc);
			
			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = new TraineeDTO();	

			    tReturn.setId(String.valueOf(traineeA.getId()));
				tReturn.setName(traineeA.getName());
				tReturn.setLocation(traineeA.getLocation());
				if(traineeA.getJoinDate() != null)
					tReturn.setJoinDate(traineeA.getJoinDate().toString());
				if(traineeA.getJoinDate() != null)
					tReturn.setTestDate(traineeA.getTestDate().toString());
				
				trList.add(tReturn);
				});
			response.setTraineesList(trList);
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
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();
		
		try {
			if (trepo.findByNameLike(expr).isEmpty())
				throw new NoSuchElementException(NAMEPATTERNERROR);
			
			List<TraineeDAO> traineeList= trepo.findByNameLike(expr);
			
			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = new TraineeDTO();	

			    tReturn.setId(String.valueOf(traineeA.getId()));
				tReturn.setName(traineeA.getName());
				tReturn.setLocation(traineeA.getLocation());
				if(traineeA.getJoinDate() != null)
					tReturn.setJoinDate(traineeA.getJoinDate().toString());
				
				if(traineeA.getJoinDate() != null)
					tReturn.setTestDate(traineeA.getTestDate().toString());
	
				trList.add(tReturn);
				});
			response.setTraineesList(trList);
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
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();
		

		try {			
			if(trepo.findByName(name).isEmpty())		
				throw new NoSuchElementException(NONAMERROR);
			
			List<TraineeDAO> traineeList= trepo.findByNameIncludeLocation(name);
			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = new TraineeDTO();	

			    tReturn.setId(String.valueOf(traineeA.getId()));
				tReturn.setName(traineeA.getName());
				tReturn.setLocation(traineeA.getLocation());
				if(traineeA.getJoinDate() != null)
					tReturn.setJoinDate(traineeA.getJoinDate().toString());
			
				if(traineeA.getJoinDate() != null)
					tReturn.setTestDate(traineeA.getTestDate().toString());
			
				trList.add(tReturn);
				});
			response.setTraineesList(trList);
			return new ResponseEntity<>(response,HttpStatus.FOUND);
			}
		catch(NoSuchElementException e)
			{
				logger.error(e.getMessage());
				return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
			}
	}


}

/**
 * 
 */
package com.demo.mongodb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.mongodb.entity.Trainee;
import com.demo.mongodb.repository.TraineeRepository;

import static com.demo.mongodb.util.Constants.*;
import static com.demo.mongodb.util.InputDataValidation.*;

/**
 * @author nikhilgupta2
 *
 */


@Service
@Transactional
public class TraineeService {


	@Autowired
	private TraineeRepository trepo;
	
	Logger logger = LoggerFactory.getLogger(TraineeService.class);
	
	public Trainee createTrainee(Trainee t)
	{
		try {
			if((t.getName().isEmpty())||(t.getLocation().isEmpty()))
				throw new IllegalArgumentException("name and location" + REQUIRED);
			if(isNotAlpha(t.getName()))
				throw new IllegalArgumentException(NAMEFORM);
			if(isNotAlpha(t.getLocation()))
				throw new IllegalArgumentException(LOCATIONFORM);
			
			if(isNegativeId(t.getId()))
				throw new IllegalArgumentException(NEGVAL);
			return trepo.save(t);
			}
		catch(IllegalArgumentException e){
				logger.error(e.getMessage());
				return null;
			}
	}
	
	public Trainee updateTrainee(Trainee t) {

		try {
			if((t.getName().isEmpty())||(t.getLocation().isEmpty()))
				throw new IllegalArgumentException("name and location" + REQUIRED);
			if(isNotAlpha(t.getName()))
				throw new IllegalArgumentException(NAMEFORM);
			if(isNotAlpha(t.getLocation()))
				throw new IllegalArgumentException(LOCATIONFORM);
			
			if(isNegativeId(t.getId()))
				throw new IllegalArgumentException(NEGVAL);
			return trepo.save(t);
			}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return null;
			}
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
		
		try {
			if(isNegativeId(id))
				throw new IllegalArgumentException(NEGVAL);
			if(trepo.findById(id).isEmpty())
				throw new NoSuchElementException(NOID + id);
			
			return trepo.findById(id);
			}
		catch(IllegalArgumentException|NoSuchElementException e)
			{
				logger.error(e.getMessage());
				return null;
			}
		
	}
	
	public List<Trainee> getAllTraineesPages(int pnum, int psize) {

		try {
			if(isNegativeVal(pnum, psize))
				throw new IllegalArgumentException(NEGVAL);
			PageRequest pageRequest = PageRequest.of(pnum-1, psize);
			return trepo.findAll(pageRequest).getContent();
			}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return null;
			}
	}
	
	public List<Trainee> getSortedTrainees(int dir) {


		try {
			if(dirnValidation(dir))
				throw new IllegalArgumentException(DIRERROR);
			
			if(dir==-1) {
				Sort sort = Sort.by(Sort.Direction.DESC,"id");
				return trepo.findAll(sort);
			}
			else {
				Sort sort = Sort.by(Sort.Direction.ASC,"id");
				return trepo.findAll(sort);
			}
		}
		catch(IllegalArgumentException e)
			{
				logger.error(e.getMessage());
				return null;
		}
		
	}
	
	public List<Trainee> getTraineesGreaterThan(int gt) {

		try {
			if(isNegativeVal(gt))
				throw new IllegalArgumentException(NEGVAL);
			if(trepo.findByIdGreaterThan(gt).isEmpty())
				throw new NoSuchElementException(GTERROR + gt);
			
			return trepo.findByIdGreaterThan(gt);
			}
		catch(IllegalArgumentException|NoSuchElementException e)
			{
				logger.error(e.getMessage());
				return null;
			}
	}
	
	public List<Trainee> getTraineeByName(String nam){
		try {
			if(nam.isEmpty())
				throw new IllegalArgumentException("name " + REQUIRED);
			if(isNotAlpha(nam))
				throw new IllegalArgumentException(NAMEFORM);

			if (trepo.findByName(nam).isEmpty())
				throw new IllegalArgumentException(NONAMERROR);
			else
				return trepo.findByName(nam);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	public List<Trainee> getTraineeByNameAndLocation(String nam, String loc) {
		try {
			if((nam.isEmpty())||(loc.isEmpty()))
				throw new IllegalArgumentException("name and location" + REQUIRED);
			if(isNotAlpha(nam))
				throw new IllegalArgumentException(NAMEFORM);
			if(isNotAlpha(loc))
				throw new IllegalArgumentException(LOCATIONFORM);
			
			if (trepo.findByNameAndLocation(nam, loc).isEmpty())
				throw new NoSuchElementException(NONAMELOCERROR);
			else
				return trepo.findByNameAndLocation(nam, loc);
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public List<Trainee> getTraineeByNameContaining(String expr) {
		try {
			if (trepo.findByNameLike(expr).isEmpty())
				throw new NoSuchElementException(NAMEPATTERNERROR);
			else
				return trepo.findByNameLike(expr);
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public String deleteTraineebyID(long id) {
		
		try {
			if(isNegativeId(id))
				throw new IllegalArgumentException(NEGVAL);
			if(trepo.findById(id).isPresent())
				{
				trepo.deleteById(id);
				return "Deleted successfully";
				}
			else 
				throw new NoSuchElementException(DELETERROR);
			}
		catch(IllegalArgumentException|NoSuchElementException e)
			{
				logger.error(e.getMessage());
				return e.getMessage();
			}
	}

	public int getTraineeCountByName(String nam) {

		try {
			if(nam.isEmpty())
				throw new IllegalArgumentException("name " + REQUIRED);
			if(isNotAlpha(nam))
				throw new IllegalArgumentException(NAMEFORM);
			else
				return trepo.countByName(nam);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return 0;
		}
	}

	public List<Trainee> getTraineeByNameDispLoc(String nam) {
		// TODO Auto-generated method stub
		try {
			if(nam.isEmpty())
				throw new IllegalArgumentException("name " + REQUIRED);
			if(isNotAlpha(nam))
				throw new IllegalArgumentException(NAMEFORM);
			
			if(trepo.findByName(nam).isEmpty())		
				throw new NoSuchElementException(NONAMERROR);
			else 
				return trepo.findByNameIncludeLocation(nam);
			}
		catch(NoSuchElementException e)
			{
				logger.error(e.getMessage());
				return null;
			}
	}


}

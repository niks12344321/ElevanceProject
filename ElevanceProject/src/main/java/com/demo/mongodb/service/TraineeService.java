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
	public ResponseEntity<TraineeDTO> createTrainee(TraineeDAO traineedao) {

		TraineeDTO tReturn = new TraineeDTO();

		try {

			Optional<TraineeDAO> traineeDAOexisting = trepo.findById(traineedao.getId());

			if (traineeDAOexisting.isEmpty()) {

				TraineeDAO tCreated = trepo.save(traineedao);

				if (Objects.isNull(tCreated)) {
					throw new DataRetrievalFailureException(CREATIONFAILURERROR);
				} else {
					tReturn = setDTO(tCreated);
					return new ResponseEntity<>(tReturn, HttpStatus.CREATED);
				}
			}

			else {
				tReturn = setDTO(traineedao);
				return new ResponseEntity<>(tReturn, HttpStatus.OK);
			}
		} catch (DataRetrievalFailureException e) {

			logger.error(e.getMessage());
			return new ResponseEntity<>(tReturn, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@Override
	public ResponseEntity<TraineeDTO> updateTrainee(TraineeDAO traineedao) {

		TraineeDTO tReturn = new TraineeDTO();

		try {

			Optional<TraineeDAO> traineeDAOexisting = trepo.findById(traineedao.getId());

			if (traineeDAOexisting.isEmpty()) {

				TraineeDAO tCreated = trepo.save(traineedao);

				if (Objects.isNull(tCreated)) {
					throw new DataRetrievalFailureException(CREATIONFAILURERROR);
				} else {
					tReturn = setDTO(tCreated);
					return new ResponseEntity<>(tReturn, HttpStatus.CREATED);
				}
			}
			else {
				TraineeDAO traineeDAOexistingprime = traineeDAOexisting.get();
				
				if(traineedao.getName() != null)
					traineeDAOexistingprime.setName(traineedao.getName());
				if(traineedao.getLocation() != null)
					traineeDAOexistingprime.setLocation(traineedao.getLocation());
				if(traineedao.getJoinDate()!=null)
					traineeDAOexistingprime.setJoinDate(traineedao.getJoinDate());
				if(traineedao.getTestDate()!=null)
					traineeDAOexistingprime.setJoinDate(traineedao.getTestDate());

				TraineeDAO traineeDAOupdated =  trepo.save(traineeDAOexistingprime);
				tReturn = setDTO(traineeDAOupdated);

				return new ResponseEntity<>(tReturn, HttpStatus.OK);
			}
		} catch (DataRetrievalFailureException e) {

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
	 * updatingTrainee.setName(traineedao.getName()); trepo.save(updatingTrainee);
	 * return updatingTrainee; } else { throw new
	 * Exception("Record not found with id : " + traineedao.getId()); }
	 * 
	 * }
	 */

	@Override
	public ResponseEntity<TraineeResponse> getAllTrainees() {

		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();

		trepo.findAll().forEach(traineeA -> {
			TraineeDTO tReturn = setDTO(traineeA);
			trList.add(tReturn);
		});

		List<TraineeDTO> traineeList = trList;
		TraineeResponse response = new TraineeResponse();

		response.setTraineesList(traineeList);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TraineeResponse> getTraineebyID(long id) {
		TraineeResponse response = new TraineeResponse();

		try {
			if (trepo.findById(id).isEmpty())
				throw new NoSuchElementException(NOID + id);

			TraineeDTO tReturn = new TraineeDTO();
			TraineeDAO traineeA = trepo.findById(id).get();
			tReturn = setDTO(traineeA);

			List<TraineeDTO> traineeList = new ArrayList<>();

			traineeList.add(tReturn);
			response.setTraineesList(traineeList);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public ResponseEntity<TraineeResponse> getAllTraineesPages(int pnum, int psize, String sortDirection,
			String sortParam, Boolean idreq) {

		int i = 0;
		TraineeResponse response = new TraineeResponse();
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();

		if (sortDirection.equalsIgnoreCase("DESC")) {
			PageRequest pageRequest = PageRequest.of(pnum - 1, psize, Sort.by(Sort.Direction.DESC, sortParam));
			List<TraineeDAO> traineeList = trepo.findAll(pageRequest).getContent();

			for (TraineeDAO traineeA : traineeList) {

				TraineeDTO tReturn = setDTO(traineeA);

				trList.add(tReturn);
			}

			System.out.println(trList);

			response.setTraineesList(trList);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else if (sortDirection.equalsIgnoreCase("ASC")) {
			PageRequest pageRequest = PageRequest.of(pnum - 1, psize, Sort.by(Sort.Direction.ASC, sortParam));
			List<TraineeDAO> traineeList = trepo.findAll(pageRequest).getContent();

			for (TraineeDAO traineeA : traineeList) {
				// TraineeDTO tReturn = new
				// TraineeDTO(String.valueOf(traineeA.getId()),traineeA.getName(),traineeA.getLocation());
				TraineeDTO tReturn = setDTO(traineeA);

				trList.add(tReturn);
			}

			response.setTraineesList(trList);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else
			return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);
	}
	
	@Override
	public ResponseEntity<TraineeResponse> getAllTraineesPagesFilter(int pnum, int psize, String sortDirection,
			String sortParam, int gt) {

		int i = 0;
		TraineeResponse response = new TraineeResponse();
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();
		
		try {
			if (trepo.findByIdGreaterThan(gt).isEmpty())
				throw new NoSuchElementException(GTERROR + gt);
			

			if (sortDirection.equalsIgnoreCase("DESC")) {
				PageRequest pageRequest = PageRequest.of(pnum - 1, psize, Sort.by(Sort.Direction.DESC, sortParam));
				List<TraineeDAO> traineeList = trepo.findByIdGreaterThan(gt,pageRequest);

				for (TraineeDAO traineeA : traineeList) {

					TraineeDTO tReturn = setDTO(traineeA);

					trList.add(tReturn);
				}

				System.out.println(trList);

				response.setTraineesList(trList);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else if (sortDirection.equalsIgnoreCase("ASC")) {
				PageRequest pageRequest = PageRequest.of(pnum - 1, psize, Sort.by(Sort.Direction.ASC, sortParam));
				List<TraineeDAO> traineeList = trepo.findByIdGreaterThan(gt,pageRequest);

				for (TraineeDAO traineeA : traineeList) {
					// TraineeDTO tReturn = new
					// TraineeDTO(String.valueOf(traineeA.getId()),traineeA.getName(),traineeA.getLocation());
					TraineeDTO tReturn = setDTO(traineeA);

					trList.add(tReturn);
				}

				response.setTraineesList(trList);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else
				return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}
		
		
		

	@Override
	public ResponseEntity<TraineeResponse> getSortedTrainees(String sortDirection, String sortParam) {

		TraineeResponse response = new TraineeResponse();
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();

		if (sortDirection.equalsIgnoreCase("DESC")) {
			Sort sort = Sort.by(Sort.Direction.DESC, sortParam);
			List<TraineeDAO> traineeList = trepo.findAll(sort);

			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = setDTO(traineeA);
				trList.add(tReturn);
			});
			response.setTraineesList(trList);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else if (sortDirection.equalsIgnoreCase("ASC")) {
			Sort sort = Sort.by(Sort.Direction.ASC, sortParam);
			List<TraineeDAO> traineeList = trepo.findAll(sort);

			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = setDTO(traineeA);

				trList.add(tReturn);
			});
			response.setTraineesList(trList);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else
			return new ResponseEntity<>(response, HttpStatus.PRECONDITION_FAILED);

	}

	@Override
	public ResponseEntity<TraineeResponse> getTraineesGreaterThan(int gt) {

		TraineeResponse response = new TraineeResponse();
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();

		try {
			if (trepo.findByIdGreaterThan(gt).isEmpty())
				throw new NoSuchElementException(GTERROR + gt);

			List<TraineeDAO> traineeList = trepo.findByIdGreaterThan(gt);

			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = setDTO(traineeA);

				trList.add(tReturn);
			});
			response.setTraineesList(trList);
			return new ResponseEntity<>(response, HttpStatus.FOUND);
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	// Get trainees with joining date greater than.
	@Override
	public ResponseEntity<TraineeResponse> getTraineesGreaterThanDate(LocalDate joinDate) {

		TraineeResponse response = new TraineeResponse();
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();

		try {
			if (trepo.findByJoinDateGreaterThanEqual(joinDate).isEmpty())
				throw new NoSuchElementException(GTERROR + joinDate);

			List<TraineeDAO> traineeList = trepo.findByJoinDateGreaterThanEqual(joinDate);

			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = setDTO(traineeA);

				trList.add(tReturn);
			});
			response.setTraineesList(trList);
			return new ResponseEntity<>(response, HttpStatus.FOUND);
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<TraineeResponse> getTraineeByName(String name) {

		TraineeResponse response = new TraineeResponse();
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();

		try {
			if (trepo.findByName(name).isEmpty())
				throw new NoSuchElementException(NONAMERROR);

			List<TraineeDAO> traineeList = trepo.findByName(name);

			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = setDTO(traineeA);
				trList.add(tReturn);
			});
			response.setTraineesList(trList);
			return new ResponseEntity<>(response, HttpStatus.FOUND);

		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<TraineeResponse> getTraineeByNameAndLocation(String name, String loc) {

		TraineeResponse response = new TraineeResponse();
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();

		try {
			if (trepo.findByNameAndLocation(name, loc).isEmpty())
				throw new NoSuchElementException(NONAMELOCERROR);

			List<TraineeDAO> traineeList = trepo.findByNameAndLocation(name, loc);

			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = setDTO(traineeA);
				trList.add(tReturn);
			});
			response.setTraineesList(trList);
			return new ResponseEntity<>(response, HttpStatus.FOUND);
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<TraineeResponse> getTraineeByNameContaining(String expr) {

		TraineeResponse response = new TraineeResponse();
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();

		try {
			if (trepo.findByNameLike(expr).isEmpty())
				throw new NoSuchElementException(NAMEPATTERNERROR);

			List<TraineeDAO> traineeList = trepo.findByNameLike(expr);

			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = setDTO(traineeA);
				trList.add(tReturn);
			});
			response.setTraineesList(trList);
			return new ResponseEntity<>(response, HttpStatus.FOUND);
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<String> deleteTraineebyID(long id) {

		try {
			if (isNegativeId(id))
				throw new IllegalArgumentException(NEGVAL);
			if (trepo.findById(id).isPresent()) {
				trepo.deleteById(id);
				logger.info("Deleted Successfully");
				return new ResponseEntity<String>("Deleted Successfully",HttpStatus.OK);
			} else
				throw new NoSuchElementException(DELETERROR);
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<String>(DELETERROR,HttpStatus.BAD_REQUEST);

		}
	}

	@Override
	public int getTraineeCountByName(String name) {

		return trepo.countByName(name);
	}

	@Override
	public ResponseEntity<TraineeResponse> getTraineeByNameDispLoc(String name) {

		TraineeResponse response = new TraineeResponse();
		List<TraineeDTO> trList = new ArrayList<TraineeDTO>();

		try {
			if (trepo.findByName(name).isEmpty())
				throw new NoSuchElementException(NONAMERROR);

			List<TraineeDAO> traineeList = trepo.findByNameIncludeLocation(name);
			traineeList.forEach(traineeA -> {
				TraineeDTO tReturn = setDTO(traineeA);
				trList.add(tReturn);
			});
			response.setTraineesList(trList);
			return new ResponseEntity<>(response, HttpStatus.FOUND);
		} catch (NoSuchElementException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	// Setting the DAO to DTO object
	private TraineeDTO setDTO(TraineeDAO tCreated) {

		TraineeDTO tReturn = new TraineeDTO();

		tReturn.setId(String.valueOf(tCreated.getId()));
		tReturn.setName(tCreated.getName());
		tReturn.setLocation(tCreated.getLocation());

		if (tCreated.getJoinDate() != null)
			tReturn.setJoinDate(tCreated.getJoinDate().toString());

		if (tCreated.getJoinDate() != null)
			tReturn.setTestDate(tCreated.getTestDate().toString());
		return tReturn;
	}

}

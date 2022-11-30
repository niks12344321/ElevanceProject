package com.demo.mongodb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.mongodb.controller.TraineeControllerMainCRUD;
import com.demo.mongodb.dto.TraineeDTO;
import com.demo.mongodb.entity.TraineeDAO;
import com.demo.mongodb.repository.TraineeRepository;
import com.demo.mongodb.service.TraineeService;
import static com.demo.mongodb.util.Constants.*;


@RunWith(SpringRunner.class)
@SpringBootTest
class ApiConnectApplicationTests {

	/*
	 * @Test void contextLoads() { }
	 */
	
	@MockBean
	TraineeRepository trepo;
	
	@Autowired
	private TraineeControllerMainCRUD controller;
	
	@Autowired
	private TraineeService service;
	
	@SuppressWarnings("deprecation")
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	
	  @Test 
	  void traineeCreateTest() throws ParseException { 
		  TraineeDTO t = new TraineeDTO();
		  t.setId("5");
		  t.setName("testerhello"); 
		  t.setLocation("testedLocation");
		  t.setJoinDate("1997-11-25");
		  t.setTestDate("1996-10-12"); 
		  
		  TraineeDAO tSave = new TraineeDAO();
		  tSave.setId(Long.parseLong(t.getId()));
		  tSave.setName(t.getName());
		  tSave.setLocation(t.getLocation());
		  tSave.setJoinDate(LocalDate.parse(t.getJoinDate()));
		  tSave.setTestDate(LocalDate.parse(t.getTestDate()));
		  
		  when(trepo.save(tSave)).thenReturn(tSave);
		  
		  assertEquals(t, service.createTrainee(tSave).getBody()); 
		  assertEquals(HttpStatus.CREATED, service.createTrainee(tSave).getStatusCode());
	  }
	 
	  @Test 
	  void traineeCreateTestIdException() throws ParseException { 
		  TraineeDTO t = new TraineeDTO();
		  t.setId("-5");
		  t.setName("testerhello"); 
		  t.setLocation("testedLocation");
		  t.setJoinDate("1997-11-25");
		  t.setTestDate("1996-10-12"); 
		  
		  controller.saveTrainee(t);
		  thrown.expect(IllegalArgumentException.class);
	      thrown.expectMessage(NUMBERFORM);
	  }
	  
	  @Test 
	  void traineeCreateTestNameException() throws ParseException { 
		  TraineeDTO t = new TraineeDTO();
		  t.setId("5");
		  t.setName("tester23"); 
		  t.setLocation("testedLocation");
		  t.setJoinDate("1997-11-25");
		  t.setTestDate("1996-10-12"); 
		  
		  controller.saveTrainee(t);
		  thrown.expect(IllegalArgumentException.class);
	      thrown.expectMessage("name" + STRINGINCORRECT);
	  }
	
	  @Test 
	  void traineeCreateTestLocationException() throws ParseException { 
		  TraineeDTO t = new TraineeDTO();
		  t.setId("5");
		  t.setName("tester"); 
		  t.setLocation("th34");
		  t.setJoinDate("1997-11-25");
		  t.setTestDate("1996-10-12"); 
		  
		  controller.saveTrainee(t);
		  thrown.expect(IllegalArgumentException.class);
	      thrown.expectMessage("location" + STRINGINCORRECT);
	  }
	  
	  @Test 
	  void traineeCreateTestJdException() throws ParseException { 
		  TraineeDTO t = new TraineeDTO();
		  t.setId("5");
		  t.setName("tester"); 
		  t.setLocation("testerone");
		  t.setJoinDate("199-11-25");
		  t.setTestDate("1996-10-12"); 
		  
		  controller.saveTrainee(t);
		  thrown.expect(IllegalArgumentException.class);
	      thrown.expectMessage(INVALIDATE);
	  }
	  
	  @Test 
	  void traineeCreateTestTdException() throws ParseException { 
		  TraineeDTO t = new TraineeDTO();
		  t.setId("5");
		  t.setName("tester"); 
		  t.setLocation("testerone");
		  t.setJoinDate("1998-11-25");
		  t.setTestDate("1996-0-12"); 
		  
		  controller.saveTrainee(t);
		  thrown.expect(IllegalArgumentException.class);
	      thrown.expectMessage(INVALIDATE);
	  }
	@Test
	public void traineeGetAllTest() {

		when(trepo.findAll()).thenReturn(Stream.of(new TraineeDAO(7L,"tOne","tLocOne",LocalDate.of(2017,01,13),LocalDate.of(2019,11,23)),
				new TraineeDAO(7L,"tTwo","tLocTwo",LocalDate.of(2018,01,23),LocalDate.of(2020,10,12))).collect(Collectors.toList()));

		assertEquals(2, controller.getTrainees().getBody().getTraineesList().size());
		assertEquals(HttpStatus.OK, controller.getTrainees().getStatusCode());
	}
	
	@Test
	public void traineeDeleteTest() {
		
		 TraineeDTO t = new TraineeDTO();
		  t.setId("5");
		  t.setName("testerhello"); 
		  t.setLocation("testedLocation");
		  t.setJoinDate("1997-11-25");
		  t.setTestDate("1996-10-12"); 
		  
		  TraineeDAO tSave = new TraineeDAO();
		  tSave.setId(Long.parseLong(t.getId()));
		  tSave.setName("testerhello");
		  tSave.setLocation("testedLocation");
		  tSave.setJoinDate(LocalDate.parse(t.getJoinDate()));
		  tSave.setTestDate(LocalDate.parse(t.getTestDate()));
		  
		  Optional<TraineeDAO> tSaveOptional = Optional.ofNullable(tSave);

		  when(trepo.findById(5L)).thenReturn(tSaveOptional);

		  service.deleteTraineebyID(tSave.getId());
		  verify(trepo, times(1)).deleteById(tSave.getId());
		  assertEquals(HttpStatus.OK, service.deleteTraineebyID(tSave.getId()).getStatusCode());		
	}
	
	@Test
	public void traineeDeleteTestException() {
		
		  TraineeDAO tSave = new TraineeDAO();

		  service.deleteTraineebyID(tSave.getId());
		  
	      thrown.expect(IllegalArgumentException.class);
	      thrown.expectMessage(NUMBERFORM);
	}
	
	
}

/**
 * 
 */
package com.demo.mongodbserver.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.mongodbserver.dto.TraineeDTO;
import com.demo.mongodbserver.dto.TraineeResponse;
import com.demo.mongodbserver.proxy.ServerProxy;

/**
 * @author nikhilgupta2
 *
 */

@RequestMapping("ServerController")
@RestController
public class ServerController {
	
	@Autowired
	ServerProxy proxy;
	
	@GetMapping("/trainee")
	public ResponseEntity<TraineeResponse> getTrainees() {

		
		ResponseEntity<TraineeResponse> rentity = new RestTemplate().getForEntity("http://localhost:8080/CRUDoperations/trainee", TraineeResponse.class);

		TraineeResponse responses =  rentity.getBody();
		return new ResponseEntity<>(responses,rentity.getStatusCode());
	}
	
	@GetMapping("/trainee-feign") 
	public ResponseEntity<TraineeResponse> getTraineesFeign() {
		
		ResponseEntity<TraineeResponse> rEntity = proxy.getTrainees();
		
		return rEntity;
		
	}
	
	@GetMapping("/traineewithId-feign")
	public ResponseEntity<TraineeResponse> getTraineesbyIDFeign(@RequestBody TraineeDTO traineedto) {
		
		ResponseEntity<TraineeResponse> rEntity = proxy.getTraineeById(traineedto.getId());
		
		return rEntity;
		
	}
	
	@PostMapping("/traineepost-feign")
	public ResponseEntity<TraineeDTO> saveTraineeFeign(@RequestBody TraineeDTO traineedto) {
		
		ResponseEntity<TraineeDTO> rEntity = proxy.saveTrainee(traineedto);
		
		return rEntity;
		
	}


}

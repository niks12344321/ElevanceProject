package com.demo.mongodbserver.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.mongodbserver.dto.TraineeDTO;
import com.demo.mongodbserver.dto.TraineeResponse;

@FeignClient(name="apiconnect",url="localhost:8080")
public interface ServerProxy {
	
	@GetMapping("/CRUDoperations/trainee")
	public ResponseEntity<TraineeResponse> getTrainees();
	
	@GetMapping("/CRUDoperations/traineewithIdParam")
	public ResponseEntity<TraineeResponse> getTraineeById(@RequestParam String tid) ;

	@PostMapping("/CRUDoperations/trainee")
	public ResponseEntity<TraineeDTO> saveTrainee(@RequestBody TraineeDTO t);
}

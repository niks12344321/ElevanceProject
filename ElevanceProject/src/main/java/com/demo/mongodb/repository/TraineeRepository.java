/**
 * 
 */
package com.demo.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.mongodb.entity.Trainee;

/**
 * @author nikhilgupta2
 *
 */

@Repository
public interface TraineeRepository extends MongoRepository<Trainee, Long> {

	List<Trainee> findByIdGreaterThan(int gt);
	
	List<Trainee> findByName(String name);
	
	List<Trainee> findByNameAndLocation(String name, String location);

	List<Trainee> findByNameLike(String expr);
	
	int countByName(String nam);
	
	@Query(value="{ 'name' : ?0 }",fields="{ 'location' : 1}")
	List<Trainee> findByNameIncludeLocation(String name);

}

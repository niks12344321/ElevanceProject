	/**
 * 
 */
package com.demo.mongodb.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.mongodb.entity.TraineeDAO;

/**
 * @author nikhilgupta2
 *
 */

@Repository
public interface TraineeRepository extends MongoRepository<TraineeDAO, Long> {

	List<TraineeDAO> findByIdGreaterThan(int gt);
	
	List<TraineeDAO> findByJoinDateGreaterThanEqual(LocalDate joinDate);

	
	List<TraineeDAO> findByName(String name);
	
	List<TraineeDAO> findByNameAndLocation(String name, String location);

	List<TraineeDAO> findByNameLike(String expr);
	
	int countByName(String nam);
	
	@Query(value="{ 'name' : ?0 }",fields="{ 'location' : 1}")
	List<TraineeDAO> findByNameIncludeLocation(String name);

}

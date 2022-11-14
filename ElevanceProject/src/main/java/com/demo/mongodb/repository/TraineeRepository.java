/**
 * 
 */
package com.demo.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.mongodb.entity.Trainee;

/**
 * @author nikhilgupta2
 *
 */

@Repository
public interface TraineeRepository extends MongoRepository<Trainee, Long> {

	
}

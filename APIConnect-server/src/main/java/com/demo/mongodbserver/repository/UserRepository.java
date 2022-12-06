	/**
 * 
 */
package com.demo.mongodbserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.mongodbserver.entity.UserDAO;

/**
 * @author nikhilgupta2
 *
 */

@Repository
public interface UserRepository extends MongoRepository<UserDAO, String> {

	UserDAO findByUname(String username);
//
//	Boolean existsByUname(String username);
}

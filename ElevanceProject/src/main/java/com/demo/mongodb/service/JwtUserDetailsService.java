//package com.demo.mongodb.service;
//
//import java.util.ArrayList;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.demo.mongodb.dto.UserDTO;
//import com.demo.mongodb.entity.UserDAO;
//import com.demo.mongodb.repository.UserRepository;
//
//@Service
//public class JwtUserDetailsService implements UserDetailsService {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Autowired
//	private PasswordEncoder bcryptEncoder;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		UserDAO user = userRepository.findByUname(username);
//		if (user == null) {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
//		return new org.springframework.security.core.userdetails.User(user.getUname(), user.getPass(),
//				new ArrayList<>());
//	}
//
//	public UserDAO save(UserDTO user) {
//		UserDAO newUser = new UserDAO();
//		newUser.setUname(user.getUname());
//		newUser.setPass(bcryptEncoder.encode(user.getPass()));
//		return userRepository.save(newUser);
//	}
//}
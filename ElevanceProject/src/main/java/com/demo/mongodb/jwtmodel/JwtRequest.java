///**
// * 
// */
//package com.demo.mongodb.jwtmodel;
//
///**
// * @author nikhilgupta2
// *
// */
//import java.io.Serializable;
//
//public class JwtRequest implements Serializable {
//
//	private static final long serialVersionUID = 5926468583005150707L;
//	
//	private String uname;
//	private String pass;
//	
//	//need default constructor for JSON Parsing
//	public JwtRequest()
//	{
//		
//	}
//
//	public String getUname() {
//		return uname;
//	}
//
//	public void setUname(String uname) {
//		this.uname = uname;
//	}
//
//	public String getPass() {
//		return pass;
//	}
//
//	public void setPass(String pass) {
//		this.pass = pass;
//	}
//
//	public JwtRequest(String uname, String pass) {
//		super();
//		this.uname = uname;
//		this.pass = pass;
//	}
//
//
//}
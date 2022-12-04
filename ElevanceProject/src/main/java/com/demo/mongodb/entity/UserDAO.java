/**
 * 
 */
package com.demo.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author nikhilgupta2
 *
 */

@Document(collection = "UserDB")
public class UserDAO {

	@Id
	private String uname;
	private String pass;
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public UserDAO() {
		// TODO Auto-generated constructor stub
	}
	public UserDAO(String uname, String pass) {
		super();
		this.uname = uname;
		this.pass = pass;
	}
	@Override
	public String toString() {
		return "UserDAO [uname=" + uname + ", pass=" + pass + "]";
	}
	
}

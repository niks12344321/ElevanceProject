package com.demo.mongodb.util;

import java.util.Date;

public class InputDataValidation {

	
	public static boolean isNotAlpha(String s) {
	    return !(s.matches("^[a-zA-Z]*$"));
	}
	
	public static boolean isNotValidDate(String jd, String td) {
		
		if ((jd.matches("\\d{4}-\\d{2}-\\d{2}"))&&
				td.matches("\\d{4}-\\d{2}-\\d{2}"))
			
			return false;
		else 
			return true;
	}
	
	
	
	public static boolean isNegativeId(Long n) {
	    
		if(n<0)
			return true;
		else 
			return false;
	}

	public static boolean isNegativeVal(int n) {
	    
		if(n<0)
			return true;
		else 
			return false;
	}
	
	public static boolean isNegativeVal(int n, int q) {
	    
		if((n<0)||(q<0))
			return true;
		else 
			return false;
	}
	
	public static boolean dirnValidation(int n) {
		if((n!=-1)&&(n!=1))
			return true;
		else
			return false;
	}
	
	public static boolean isNotValidString(String s) {
		if((s.isBlank())||s.isEmpty()||isNotAlpha(s))
			return true;
		else
			return false;
	}
	
}

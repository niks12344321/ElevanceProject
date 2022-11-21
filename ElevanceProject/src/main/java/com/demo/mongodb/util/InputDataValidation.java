package com.demo.mongodb.util;

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
	
	public static boolean dirnValidation(String s) {
		if((s.isBlank())||s.isEmpty()||isNotAlpha(s))
			return true;
		
		else {
			if((s.equalsIgnoreCase("ASC"))||(s.equalsIgnoreCase("DESC")))
				return false;
			else
				return true;
		}
	}
	
	public static boolean isNotValidString(String s) {
		if((s.isBlank())||s.isEmpty()||isNotAlpha(s))
			return true;
		else
			return false;
	}
	
	public static boolean isNotValidNumber(String n) {
		if(n.matches("[1-9][0-9]*"))
			return false;
		else
			return true;
	}

	
}

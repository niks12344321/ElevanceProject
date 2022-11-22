package com.demo.mongodb.util;

public class InputDataValidation {

	
	public static boolean isAlpha(String s) {
	    return (s!=null&&s.matches("^[a-zA-Z]*$"));
	}
	
	public static boolean isValidDate(String jd, String td) {
				
		if ((jd!=null&&jd.matches("\\d{4}-\\d{2}-\\d{2}"))&&(jd!=null&&
				td.matches("\\d{4}-\\d{2}-\\d{2}")))
			
			return true;
		else 
			return false;
	}
	
	public static boolean isValidDate(String date) {
		
		if ((date!=null&&date.matches("\\d{4}-\\d{2}-\\d{2}")))
			return true;
		else 
			return false;
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
		if((s.isBlank())||s.isEmpty()||(!isAlpha(s)))
			return true;
		
		else {
			if((s.equalsIgnoreCase("ASC"))||(s.equalsIgnoreCase("DESC")))
				return false;
			else
				return true;
		}
	}
	
	public static boolean isValidString(String s) {
		if((s.isBlank())||s.isEmpty()||(!isAlpha(s)))
			return false;
		else
			return true;
	}
	
	public static boolean isValidNumber(String n) {
		if(n.matches("[1-9][0-9]*"))
			return true;
		else
			return false;
	}

	
}

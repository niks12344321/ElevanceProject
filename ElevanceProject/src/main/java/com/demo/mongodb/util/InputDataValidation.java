package com.demo.mongodb.util;

public class InputDataValidation {

	
	public static boolean isNotAlpha(String s) {
	    return !(s.matches("^[a-zA-Z]*$"));
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
	
	
}

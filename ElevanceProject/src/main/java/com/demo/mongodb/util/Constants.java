/**
 * 
 */
package com.demo.mongodb.util;

/**
 * @author nikhilgupta2
 *
 */
public final class Constants {
	
	private Constants() {
	}
	
	public static final String NEGVAL = "Input Values cannot be lesser than 0";
	public static final String NOID = "No element found with this id - ";
	public static final String DIRERROR = "direction values should be either ASC or DESC";
	public static final String GTERROR = "No element found which is greater than";
	public static final String NONAMERROR = "name not found, hence unable to display";
	public static final String NONAMELOCERROR = "name and location unable to be matched to a single element.";
	public static final String NAMEPATTERNERROR = "NO such Trainee with the pattern found in name";
	public static final String DELETERROR = "ID not found, hence unable to delete";
	public static final String CREATIONFAILURERROR = "Unable to create the resource due to internal server error.";
	public static final String NUMBERFORM = "Input Values cannot be lesser than 0 and must contain only digits";

	public static final String REQUIRED = "- is required and must contain only characters ";
	
	public static final String INVALIDATE = " Date must be of the format  yyyy-mm-dd";
}

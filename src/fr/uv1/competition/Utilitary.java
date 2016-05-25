package fr.uv1.competition;
import fr.uv1.utils.*;
public class Utilitary {
	
	/* This class provides some useful methods, including casting from char to int etc.
	 * 
	 * It also contains all the tests that are related to the client choices (name/birth date validation)
	 * 
	 *
	 //*/
	
	public static boolean isNumber(char c){
		return c >= '0' && c <= '9';
	}
	
	public static boolean isValidName(String name) {
		boolean valid = (name.length() > 3 && name.length() < 21);
		for (int i =0 ; i < name.length(); i++) {
			char c = name.charAt(i);
			valid = valid && ((c >= 'a' && c <='z') || (c >= 'A' && c <= 'Z') 
					|| (isNumber(c)) || (c == '-' || c == ' '));
		}
		return valid;
	}
	
	public static boolean isValidDate(MyCalendar calendar) {
		MyCalendar past = new MyCalendar(calendar);
		past.add(MyCalendar.YEAR, 18);
		return past.isInThePast();
	}
	
}

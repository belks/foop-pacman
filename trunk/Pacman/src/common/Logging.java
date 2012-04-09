package common;

import java.util.logging.Level;


public final class Logging {

	public static void log(String msg, Level lvl) {
		System.out.println(msg);
	}
	
	public static void logException(Exception ex, Level lvl) {
		System.out.println(ex.getMessage());
	}
	
	public static void logException(Exception ex) {
		logException(ex, Level.SEVERE);
	}
}
package common;

import java.util.logging.Level;


public final class Logging {

	protected static void log(String msg, Level lvl) {
		System.out.println(msg);
	}
	
	protected static void logException(Exception ex, Level lvl) {
		System.out.println(ex.getMessage());
	}
	
	protected static void logException(Exception ex) {
		logException(ex, Level.SEVERE);
	}
}
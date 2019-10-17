package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * 
 * @author Nolan Donaldson, Chase Patterson
 *
 */

public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		super("Error: File format not supported");
	}
	
	public BadConfigFormatException(String message) {
		super(message);
		try {
			PrintWriter log = new PrintWriter("logfile.txt");
			// Prints output to file
			log.println(message);
			log.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "Attempted file input format not supported";
	}

}

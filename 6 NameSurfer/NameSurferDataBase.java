import java.io.*;
import java.util.*;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	public NameSurferDataBase(String filename) {
		String line = "";
		BufferedReader rd = null;
		try {
			rd = new BufferedReader(new FileReader(filename));
		} catch (IOException ex) {
			return; // TODO need more meaningful catch
		}
		try {
			line = rd.readLine();
			while (line != null) {
        		NameSurferEntry entry = new NameSurferEntry(line);
        		entries.put(entry.getName(), entry);
        		line = rd.readLine();
        	}
			rd.close();
		} catch (IOException ex) {
			return; // TODO need more meaningful catch
		}
	}
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		if (entries.containsKey(name)) {
			return entries.get(name);
		}
		else {
			return null;
		}
	}
	
	private Map<String, NameSurferEntry> entries = new HashMap<String, NameSurferEntry>();
}


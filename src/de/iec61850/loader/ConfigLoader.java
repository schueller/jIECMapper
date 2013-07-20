package de.iec61850.loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class ConfigLoader {

	private static ConfigLoader instance = null;
	private File confFile;

	private String xslPath;
	private String umlPath;
	private Vector<String> entries;

	private ConfigLoader() {
		this.confFile = new File("config.ini");
		this.entries = new Vector<String>();
		this.readLinesFromFile();
	}

	private void readLinesFromFile() {
		try {
			Scanner reader = new Scanner(this.confFile);
			// zeilen weises einlesen
			while (reader.hasNext()) {
				String line = reader.nextLine();
				// kommentar
				if (line.substring(0,1).equals("#") == false) {
					// Exceldateien Pfad
					if (line.indexOf("xsl") > -1) {
						this.xslPath = line.substring(line.indexOf("=")+1).trim();
					}
					// UMLdateien Pfad
					if (line.indexOf("uml") > -1) {
						this.umlPath = line.substring(line.indexOf("=")+1).trim();
					}
					// einzulesende Exceldateien
					if (line.indexOf("entry") > -1) {
						this.entries.add(line.substring(line.indexOf("=")+1)
								.trim());
					}
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("No Config found");
			System.exit(0);
		}

	}

	public static ConfigLoader getInstance() {
		if (instance == null) {
			instance = new ConfigLoader();
		}
		return instance;
	}

	public String getXslPath() {
		return this.xslPath;
	}

	public String getUmlPath() {
		return this.umlPath;
	}

	public Vector<String> getEntries() {
		return this.entries;
	}

}

package de.iec61850.loader;

import java.util.HashMap;
import java.util.Vector;

import de.iec61850.typ.DataObject;

public class ExcelDataHandler {

	/**
	 * daten aus Excel
	 */
	private HashMap<String, DataObject> dataObjectList;

	public ExcelDataHandler() {
		// ExcelMap initialisieren
		this.dataObjectList = new HashMap<String, DataObject>();
	}

	/**
	 * laden der ExcelDaten
	 */
	public void loadAll() {
		// TODO besser über config File löse
		// einlesen aller Exceldateien aus der Config
		for (String umlEntry : ConfigLoader.getInstance().getEntries()) {
			ExcelBaseLoader excel = new ExcelBaseLoader(
					umlEntry.substring(0, umlEntry.indexOf(";"))
							.replace("\"", "").trim(),
					umlEntry.substring(umlEntry.indexOf(";")+1,
							umlEntry.lastIndexOf(";")).replace("\"", "").trim(),
					umlEntry.substring(umlEntry.lastIndexOf(";")+1)
							.replace("\"", "").trim());
			if (!excel.isNull()) {
				// wenn einlesen erfolgreich, hinzufügen
				this.dataObjectList.putAll(excel.load());
			}
		}
		System.out.println(this.dataObjectList.size());
	}

	/**
	 * 
	 * Suchen einer Klasse in den Exceldaten
	 * 
	 * @param classname
	 * @return DataObject
	 */
	public DataObject find(String name) {
		return this.dataObjectList.get(name);
	}

	/**
	 * 
	 * gibt alle Exceldaten zurück
	 * 
	 * @return Vector<DataObject>
	 */
	public Vector<DataObject> getObjectList () {
		return new Vector<DataObject>(this.dataObjectList.values());
	}
	
	/**
	 * 
	 * gibt die Anzahl der Klassen aus den Exceldaten zurück
	 * 
	 * @return int
	 */
	public int size() {
		return this.dataObjectList.size();
	}
}

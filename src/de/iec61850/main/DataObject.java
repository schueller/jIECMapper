package de.iec61850.main;

import java.util.Vector;

public class DataObject {

	private String name;
	private Vector<Attribute> attribute;
	private Vector<DataAttributes> dataAttributes;

	DataObject() {
		this.attribute = new Vector<Attribute>();
		this.dataAttributes = new Vector<DataAttributes>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	// abgelich
	/*
	 * TODO eine Methode "check" abgleich jedes einzelnen elements, rückgabe von
	 * Fehlern
	 */

}

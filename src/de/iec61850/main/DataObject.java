package de.iec61850.main;

import java.util.Vector;

public class DataObject {

	private String name;
	private Vector<DataAttribute> dataAttributes;

	public DataObject() {
		this.dataAttributes = new Vector<DataAttribute>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addDataAttributes (DataAttribute da) {
		this.dataAttributes.add(da);
	}

	@Override
	public String toString() {
		return "DataObject [name=" + name + ", dataAttributes="
				+ dataAttributes.toString() + "]";
	}

	// abgelich
	/*
	 * TODO eine Methode "check" abgleich jedes einzelnen elements, rückgabe von
	 * Fehlern
	 */

}

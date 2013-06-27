package de.iec61850.main;

import java.util.HashMap;
import java.util.Set;

public class DataObject {

	private String name;
	private HashMap<String, DataAttribute> dataAttributes;

	public DataObject() {
		this.dataAttributes = new HashMap<String, DataAttribute>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addDataAttributes (DataAttribute da) {
		this.dataAttributes.put(da.getName(), da);
	}
	
	public Set<String> getAttrNamen () {
		return this.dataAttributes.keySet();
	}
	
	public DataAttribute getAttr (String name) {
		return this.dataAttributes.get(name);
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

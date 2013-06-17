package de.iec61850.main;

import java.util.Vector;

public class IECVector {

	private Vector<DataObject> lDataObjects; 

	IECVector () {
		this.lDataObjects = new Vector<DataObject>();
	}
	
	public void add(DataObject dataObj) {
		this.lDataObjects.add(dataObj);
	}
	
	public int size () {
		return this.lDataObjects.size();
	}
	
	public DataObject get (int index) {
		return this.lDataObjects.get(index);
	}
	
	public void clear () {
		this.lDataObjects.clear();
	}

	public Vector<DataObject> getAll() {
		return this.lDataObjects;
	}
	
	@Override
	public String toString() {
		return this.lDataObjects.toString();
	}

	// abgelich
	/*
	 * TODO eine Methode "check" abgleich jedes einzelnen elements, rückgabe von
	 * Fehlern
	 */

	
}

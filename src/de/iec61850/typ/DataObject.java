package de.iec61850.typ;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;


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
	
	public Collection<DataAttribute> getAttrs () {
		return this.dataAttributes.values();
	}
	
	public DataAttribute getAttr (String name) {
		return this.dataAttributes.get(name);
	}

	@Override
	public String toString() {
		return "DataObject [name=" + name + ", dataAttributes="
				+ dataAttributes.toString() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataAttributes == null) ? 0 : dataAttributes.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataObject other = (DataObject) obj;
		if (dataAttributes == null) {
			if (other.dataAttributes != null)
				return false;
		} else if (!dataAttributes.equals(other.dataAttributes))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


}

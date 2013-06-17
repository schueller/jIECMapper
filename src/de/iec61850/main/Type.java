package de.iec61850.main;

public class Type {

	String name;
	String id;
	String type;

	public Type(String name, String id, String type) {
		this.name = name;
		this.id = id;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Type [name=" + name + ", id=" + id + ", type=" + type + "]";
	}

}

package de.iec61850.typ;

public class DataAttribute implements Comparable<DataAttribute> {

	private String name;
	private String typ;
	private String moc;

	public DataAttribute() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getMoc() {
		return moc;
	}

	public void setMoc(String moc) {
		this.moc = moc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((moc == null) ? 0 : moc.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((typ == null) ? 0 : typ.hashCode());
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
		DataAttribute other = (DataAttribute) obj;
		if (moc == null) {
			if (other.moc != null)
				return false;
		} else if (!moc.equals(other.moc))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (typ == null) {
			if (other.typ != null)
				return false;
		} else if (!typ.equals(other.typ))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataAttribute [name=" + name + ", typ=" + typ + ", moc=" + moc
				+ "]";
	}

	@Override
	public int compareTo(DataAttribute other) {
		// sortieren nach Name
		if (other.getName() == null && this.getName() == null) {
			return 0;
		}
		if (this.getName() == null) {
			return 1;
		}
		if (other.getName() == null) {
			return -1;
		}
		return this.getName().compareTo(other.getName());
	}

}
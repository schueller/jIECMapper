package de.iec61850.loader;

import java.util.HashMap;
import java.util.Vector;

import de.iec61850.typ.DataObject;

public class ExcelDataHandler {

	private HashMap<String, DataObject> dataObjectList;

	public ExcelDataHandler() {
		this.dataObjectList = new HashMap<String, DataObject>();
	}

	public void loadAll() {
		// TODO besser über config File löse
		for (String umlEntry : ConfigLoader.getInstance().getEntries()) {
			ExcelBaseLoader excel = new ExcelBaseLoader(
					umlEntry.substring(0, umlEntry.indexOf(";"))
							.replace("\"", "").trim(),
					umlEntry.substring(umlEntry.indexOf(";")+1,
							umlEntry.lastIndexOf(";")).replace("\"", "").trim(),
					umlEntry.substring(umlEntry.lastIndexOf(";")+1)
							.replace("\"", "").trim());
			if (!excel.isNull()) {
				this.dataObjectList.putAll(excel.load());
			}
		}
		System.out.println(this.dataObjectList.size());
	}

	public DataObject find(String name) {
		return this.dataObjectList.get(name);
	}

	public Vector<DataObject> getObjectList () {
		return new Vector<DataObject>(this.dataObjectList.values());
	}
	
	public int size() {
		return this.dataObjectList.size();
	}
}

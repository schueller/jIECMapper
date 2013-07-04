package de.iec61850.loader;

import java.util.HashMap;

import de.iec61850.typ.DataObject;

public class ExcelDataHandler {

	private HashMap<String, DataObject> dataObjectList;

	public ExcelDataHandler() {
		this.dataObjectList = new HashMap<String, DataObject>();
	}

	public void loadAll() {
		// TODO besser über config File lösen
		// ExcelBaseLoader excel;
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
		// excel = new ExcelBaseLoader("DIN EN 61850-7-4.xls",
		// "Data object name",
		// "Common data class");
		// if (!excel.isNull()) {
		// this.dataObjectList.putAll(excel.load());
		// }
		// excel = new ExcelBaseLoader("DIN EN 61850-7-420.xls",
		// "Data object name", "Common data class");
		// if (!excel.isNull()) {
		// this.dataObjectList.putAll(excel.load());
		// }
		// excel = new ExcelBaseLoader("DIN EN 61850-7-2.xls", "Attribute name",
		// "Attribute type");
		// if (!excel.isNull()) {
		// this.dataObjectList.putAll(excel.load());
		// }
		// excel = new ExcelBaseLoader("DIN EN 61400-25-2.xls",
		// "Attribute name",
		// "Attribute type");
		// if (!excel.isNull()) {
		// this.dataObjectList.putAll(excel.load());
		// }
		System.out.println(this.dataObjectList.size());
	}

	public DataObject find(String name) {
		return this.dataObjectList.get(name);
	}

	// public static void main(String args[]) {
	// System.out.println("ExcelLoader TEST");
	// ExcelDataHandler edh = new ExcelDataHandler();
	// edh.loadAll();
	// }

}

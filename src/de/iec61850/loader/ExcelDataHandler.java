package de.iec61850.loader;

import java.util.Vector;

import de.iec61850.main.DataObject;

public class ExcelDataHandler {

	private Vector<DataObject> dataObjectList;

	public ExcelDataHandler() {
		this.dataObjectList = new Vector<DataObject>();
	}

	public void loadAll() {
		// TODO besser über config File lösen
		ExcelBaseLoader excel = new ExcelBaseLoader("DIN EN 61850-7-4.xls",
				"Data object name", "Common data class");
		if (!excel.isNull()) {
			this.dataObjectList.addAll(excel.load());
		}
		excel = new ExcelBaseLoader("DIN EN 61850-7-420.xls", "Data object name",
				"Common data class");
		if (!excel.isNull()) {
			this.dataObjectList.addAll(excel.load());
		}
		excel = new ExcelBaseLoader("DIN EN 61850-7-2.xls", "Attribute name",
				"Attribute type");
		if (!excel.isNull()) {
			this.dataObjectList.addAll(excel.load());
		}
		excel = new ExcelBaseLoader("DIN EN 61400-25-2.xls", "Attribute name",
				"Attribute type");
		if (!excel.isNull()) {
			this.dataObjectList.addAll(excel.load());
		}
		System.out.println(this.dataObjectList.size());
	}
	
	public static void main(String args[]) {
		System.out.println("ExcelLoader TEST");
		ExcelDataHandler edh = new ExcelDataHandler();
		edh.loadAll();
	}

}

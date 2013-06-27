package de.iec61850.loader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import de.iec61850.main.DataAttribute;
import de.iec61850.main.DataObject;

public class ExcelBaseLoader {

	private Workbook workbook;
	private boolean inull = true;
	private String attrNameTag;
	private String attrTypTag;
	private int attrname = -1;
	private int attrtyp = -1;

	private HashMap<String, DataObject> dataObjectList;

	public ExcelBaseLoader(String dataname, String attrNameTag,
			String attrTypTag) {
		try {
			this.workbook = Workbook.getWorkbook(new File("excel/" + dataname));
			this.attrNameTag = attrNameTag;
			this.attrTypTag = attrTypTag;
			this.dataObjectList = new HashMap<String, DataObject>();
			this.inull = false;
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.inull = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.inull = true;
		}
	}

	public boolean isNull() {
		return this.inull;
	}

	public HashMap<String, DataObject> load() {
		if (this.isNull() == false) {
			for (Sheet sheet : workbook.getSheets()) {
				this.getDataObjectFromXLS(sheet);
			}
			System.out.println("Found classes in Excel: " +this.dataObjectList.size());
			return this.dataObjectList;
		} else {
			return null;
		}
	}

	private void detectColRow(Sheet sheet) {
		boolean anf = false, atf = false;
		for (int col = 0; col < sheet.getColumns(); col++) {
			// System.out.println(sheet.getCell(col,1).getContents());
			if (sheet.getCell(col, 1).getContents().trim().equals(attrNameTag)) {
				attrname = col;
				anf = true;
			}
			if (sheet.getCell(col, 1).getContents().trim().equals(attrTypTag)) {
				attrtyp = col;
				atf = true;
			}
		}
		if (anf == false || atf == false) {
			attrname = -1;
			attrtyp = -1;
		}
	}

	private void getDataObjectFromXLS(Sheet sheet) {
		if (sheet.getCell(0, 0).getContents().indexOf("class") > -1) {
			DataObject dobj = new DataObject();
			//System.out.println(sheet.getName().trim());
			dobj.setName(sheet.getCell(0, 0).getContents().replace("class", "")
					.trim());
			// spalten holen
			this.detectColRow(sheet);
			if (this.attrname > -1 && this.attrtyp > -1) {
				for (int row = 2; row < sheet.getRows(); row++) {
					this.getDataAttributeFromXLS(dobj, sheet, row);
				}
				// System.out.println(dobj);
				this.dataObjectList.put(dobj.getName(), dobj);
			}
		}
	}

	private void getDataAttributeFromXLS(DataObject dobj, Sheet sheet, int row) {
		if (!sheet.getCell(attrname, row).getContents().trim().isEmpty()) {
			DataAttribute da = new DataAttribute();
			da.setName(sheet.getCell(attrname, row).getContents().trim());
			da.setTyp(sheet.getCell(attrtyp, row).getContents().trim());
			dobj.addDataAttributes(da);
		}
	}

}

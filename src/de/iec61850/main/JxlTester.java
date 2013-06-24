package de.iec61850.main;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class JxlTester {

	public JxlTester() {
		
		try {
			Workbook workbook = Workbook.getWorkbook(new File(
					"excel/DIN EN 61850-7-2.xls"));
			for (Sheet sheet : workbook.getSheets()) {
				Cell a1 = sheet.getCell(0, 0);
				if (a1.getContents().indexOf("class") > -1) {
					DataObject dobj = new DataObject();
					System.out.println(sheet.getName().trim());
					dobj.setName(a1.getContents().replace("class", "").trim());
					// spalten holen
					int attrname = -1, attrtyp = -1;
					for (int col = 0; col < sheet.getColumns(); col++) {
						// System.out.println(sheet.getCell(col,1).getContents());
						if (sheet.getCell(col, 1).getContents().trim()
								.equals("Attribute name")) {
							attrname = col;
						}
						if (sheet.getCell(col, 1).getContents().trim()
								.equals("Attribute type")) {
							attrtyp = col;
						}
					}
					System.out.println("attrname=" + attrname + "|attrtyp="
							+ attrtyp);
					if (attrname > -1 && attrtyp > -1) {
						for (int row = 2; row < sheet.getRows(); row++) {
							if (!sheet.getCell(attrname, row).getContents()
									.trim().isEmpty()) {
								DataAttribute da = new DataAttribute();
								da.setName(sheet.getCell(attrname, row)
										.getContents().trim());
								da.setTyp(sheet.getCell(attrtyp, row)
										.getContents().trim());
								dobj.addDataAttributes(da);
							}
						}

					}
					System.out.println(dobj);
				}
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new JxlTester();
	}

}

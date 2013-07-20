package de.iec61850.loader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import de.iec61850.typ.DataAttribute;
import de.iec61850.typ.DataObject;

public class ExcelBaseLoader {

	private Workbook workbook;
	private boolean inull = true;
	private String attrNameTag;
	private String attrTypTag;
	private int attrname = -1;
	private int attrtyp = -1;
	private int firstelement = 0;

	private HashMap<String, DataObject> dataObjectList;

	public ExcelBaseLoader(String dataname, String attrNameTag,
			String attrTypTag) {
		System.out.println(dataname);
		try {
			// Exceldatei in Workbook laden
			this.workbook = Workbook.getWorkbook(new File(ConfigLoader
					.getInstance().getXslPath() + dataname));
			// Attributtags setzen
			this.attrNameTag = attrNameTag;
			this.attrTypTag = attrTypTag;
			// Dataobjectmap initialieren
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

	/**
	 * 
	 * läd Daten
	 * 
	 * @return HashMap<String, DataObject>
	 */
	public HashMap<String, DataObject> load() {
		// wenn erfolgreich initialisiert
		if (this.isNull() == false) {
			// für jede Tabelle in der Exceldatei
			for (Sheet sheet : workbook.getSheets()) {
				// Baue DataObject
				this.getDataObjectFromXLS(sheet);
			}
			System.out.println("Found classes in Excel: "
					+ this.dataObjectList.size());
			return this.dataObjectList;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * sucht Attributtags in der Tabelle 
	 * Spaltenindex
	 * 
	 * @param sheet
	 */
	private void detectColRow(Sheet sheet) {
		boolean anf = false, atf = false;
		// für jede Spalte
		for (int col = 0; col < sheet.getColumns(); col++) {
			// für jede Zeile
			for (int row = 0; row < sheet.getRows(); row++) {
				// wenn Attributname gefunden
				if (sheet.getCell(col, row).getContents().replace("\n", "")
						.replace(" ", "").trim().equalsIgnoreCase(attrNameTag)) {
					attrname = col;
					anf = true;
					this.firstelement = row;
				}
				// wenn Attributtyp gefunden
				if (sheet.getCell(col, row).getContents().replace("\n", "")
						.replace(" ", "").trim().equalsIgnoreCase(attrTypTag)) {
					attrtyp = col;
					atf = true;
					this.firstelement = row;
				}
			}
		}
		// wenn ein Tag nicht gefunden alles -1
		/*
		 * NOTIC es kann an dieser Stelle nicht zuverlässig gesagt werden, wo sich die
		 * AttributNamen bzw die Attributtypen befindet. Daruch kann diese Klasse nicht
		 * ausgewertet werden. 
		 */
		if (anf == false || atf == false) {
			attrname = -1;
			attrtyp = -1;
		}
	}

	private void getDataObjectFromXLS(Sheet sheet) {
		// wenn die Zelle 0,0 den Strin "class" enthält
		if (sheet.getCell(0, 0).getContents().indexOf("class") > -1) {
			// erzeuge neues DataObject
			DataObject dobj = new DataObject();
			// setze namen, ohne "class"
			dobj.setName(sheet.getCell(0, 0).getContents().replace("class", "")
					.trim());
			// spalten holen
			this.detectColRow(sheet);
			// wenn Attributspalten nicht -1
			if (this.attrname > -1 && this.attrtyp > -1) {
				// für jeden Spalteneintrag ermittle Attribut
				for (int row = this.firstelement + 1; row < sheet.getRows(); row++) {
					this.getDataAttributeFromXLS(dobj, sheet, row);
				}
				// wenn Teile einer Klasse schon einmal eingelesen wurden
				if (this.dataObjectList.containsKey(dobj.getName())) {
					DataObject summondobj = this.dataObjectList.get(dobj
							.getName());
					// addiere Attribute zusammen
					for (DataAttribute daaa : dobj.getAttrs()) {
						summondobj.addDataAttributes(daaa);
					}
					// aktualisiere DataObject
					this.dataObjectList.put(summondobj.getName(), summondobj);
				} else {
					// füge DataObject hinzu
					this.dataObjectList.put(dobj.getName(), dobj);
				}
			} else {
				// System.out.println("-11-");
			}
		}
	}

	private void getDataAttributeFromXLS(DataObject dobj, Sheet sheet, int row) {
		// wenn Zelle für Attributnamen nicht leer ist
		if (!sheet.getCell(attrname, row).getContents().trim().isEmpty()) {
			// wenn die Zelle daneben nicht leer ist 
			/*
			 * NOTIC Wird ein Attributname erkannt, aber die Zelle rechts daneben ist
			 * leer, so ist das erkannte Attribut ein Kommentar, darf somit nicht
			 * aufgenommen werden
			 */
			if (sheet.getCell(attrname + 1, row).getContents()
					.replace("\n", "").equals("") == false) {
				// TODO find a better way for false cells
				// wenn Zeichen zu lang, wahrscheinlich keit Attribut
				// wenn "DataName", dann nur Beschreibung
				if ((sheet.getCell(attrname, row).getContents().trim().length() < 100)
						&& (sheet.getCell(attrname, row).getContents()
								.replace("\n", "").replace(" ", "").trim().equals("DataName") == false)) {
					DataAttribute da = new DataAttribute();
					da.setName(sheet.getCell(attrname, row).getContents()
							.replace("\n", "").replace(" ", "").trim());
					da.setTyp(sheet.getCell(attrtyp, row).getContents()
							.replace("\n", "").replace(" ", "").trim());
					da.setMoc("");
					dobj.addDataAttributes(da);
				}
			}
		}
	}

}

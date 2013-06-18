package de.iec61850.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExcelLoader {

	public ExcelLoader(String baseDir) {
		this.grabFile(baseDir + "/DIN EN 61400-25-2.xls");
	}

	public void grabFile(String pfad) {
		File f = new File(pfad);
		if (f.exists()) {
			if (f.isDirectory() == false) {
				try {
					Scanner inreader = new Scanner(f);
					String classString = new String();
					boolean hasTableFound = false;
					while (inreader.hasNext()) {
						String helper = inreader.nextLine();
						//System.out.println(helper);
						if ((helper.indexOf("<Table>") > -1)) {
							hasTableFound = true;
							classString = "";
						} else if (helper.indexOf("</Table>") > -1) {
							hasTableFound = false;
							System.out.println(this.convertStringToData(classString));
						}
						if (hasTableFound) {
							classString += helper;
						}
					}
					inreader.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.err.println(pfad + " is no File");
			}
		} else {
			System.err.println(pfad + " is no File");
		}
	}

	private DataObject convertStringToData(String classString) {
		DataObject outputDataObject = new DataObject();
		String tmpworker = new String();
		String tmphelper = new String();
		if (classString.indexOf("class") > -1) {
			//System.out.println(classString);
			tmpworker = classString.substring(classString.indexOf("Row ss:Index=\"1\""),classString.indexOf("</Row>"));
			tmphelper = classString.substring(classString.indexOf("Row ss:Index=\"1\""),classString.lastIndexOf("</Row>"));
			if (tmpworker.indexOf("class") > -1) {
				outputDataObject.setName(tmpworker.substring(tmpworker.indexOf("<B>")+"<B>".length(), tmpworker.indexOf("class")).trim());
				//tmpworker = TODO attribute und DataAttributes auswerten
				//System.out.println(tmpworker.substring(tmpworker.indexOf("<B>")+"<B>".length(), tmpworker.indexOf("class")));
			}
		} else {
			System.err.println("no class found");
		}
		return outputDataObject;
	}
	
	private Attribute convertStringToAttribute(String attString) {
		Attribute outputAttribute = new Attribute();
		
		return outputAttribute;
	}

	public static void main(String args[]) {
		System.out.println("ExcelLoader TEST");
		new ExcelLoader("excel");
	}

}

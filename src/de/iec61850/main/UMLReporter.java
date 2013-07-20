package de.iec61850.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.Class;

import de.iec61850.loader.ConfigLoader;
import de.iec61850.loader.ExcelDataHandler;
import de.iec61850.typ.DataAttribute;
import de.iec61850.typ.DataObject;

public class UMLReporter {

	/**
	 * Daten aus den Excel
	 */
	private ExcelDataHandler exdatah;
	/**
	 * Daten aus der UML
	 */
	private HashMap<String, DataObject> umlVector;

	/*
	 * Attribute für den Report
	 */
	private int lnEquals = 0;
	private int missLNEquals = 0;
	private int missLNInExcel = 0;
	private int missLNInUML = 0;
	private int missLCInExcel = 0;
	private int missLCInUml = 0;

	/**
	 * 
	 */
	public UMLReporter() {
		// erzeuge Exceldatenobjekt
		exdatah = new ExcelDataHandler();
		// initialisiere UMLMap
		this.umlVector = new HashMap<String, DataObject>();
		// lade Exceldaten
		exdatah.loadAll();
		// lade UMLdaten
		this.loadUML();
	}

	public void doReport() {
		// Ausgabeheader für CSVdatem
		String output = new String(
				"Classname;Attributname;Attributtyp;inUML;inEXCEL;isEquals"
						+ System.lineSeparator());
		// Für jedes DataOject aus der UML 
		for (DataObject daobUML : this.umlVector.values()) {
			// finde DataObject aus UML in Exceldaten
			DataObject daobEXL = exdatah.find(daobUML.getName());
			// wenn etwas gefunden wurde
			if (daobEXL != null) {
				// vergleich ob Excel und UML DataObject gleich sind
				if (daobEXL.equals(daobUML) == false) {
					// Nicht equals hochzählen
					missLNEquals++;
					// Bei Auswertung attributtyp zwei spalten
					// in uml oder in excel, bzw beides
					TreeMap<DataAttribute, Integer> summonDAttr = new TreeMap<DataAttribute, Integer>();
					// pro attribut aus Excel
					for (DataAttribute attrExcl : daobEXL.getAttrs()) {
						// finde attribute in UML
						summonDAttr.put(attrExcl, 10);
					}
					// pro attribute aus UML
					for (DataAttribute attrUML : daobUML.getAttrs()) {
						// wenn attribute schon enthalten 30, sonst 20
						if (summonDAttr.put(attrUML, 20) != null) {
							summonDAttr.put(attrUML, 30);
						}
					}
					// zeilen für den Report schreiben
					for (DataAttribute sDA : summonDAttr.keySet()) {
						// attribut nur in Excel
						if (summonDAttr.get(sDA) <= 10) {
							output += daobEXL.getName() + ";" + sDA.getName()
									+ ";" + sDA.getTyp() + ";n;j;n"
									+ System.lineSeparator();
							this.missLCInUml++;
						// attribut nut in UML
						} else if (summonDAttr.get(sDA) <= 20) {
							output += daobUML.getName() + ";" + sDA.getName()
									+ ";" + sDA.getTyp() + ";j;n;n"
									+ System.lineSeparator();
							this.missLCInExcel++;
						// attribut in beidem
						} else {
							output += daobUML.getName() + ";" + sDA.getName()
									+ ";" + sDA.getTyp() + ";j;j;j"
									+ System.lineSeparator();
						}
					}
				} else {
					// komplette Klasse ist gleich
					this.lnEquals++;
					output += daobUML.getName() + ";;;j;j;j"
							+ System.lineSeparator();
				}
			} else {
				// Klasse nicht in Excel definiert
				this.missLNInExcel++;
				output += daobUML.getName() + ";;;j;n;n"
						+ System.lineSeparator();
			}
		}
		// Zählen der Übrigen Klassen aus excel die nicht in UML sind
		for (DataObject daobExcel : this.exdatah.getObjectList()) {
			DataObject daobUML = umlVector.get(daobExcel.getName());
			if (daobUML != null) {

			} else {
				missLNInUML++;
			}
		}
		// File zum ausschreiben
		File outFile = new File("report.csv");
		try {
			FileWriter outFileWriter = new FileWriter(outFile);
			/*
			 * Statistic schreiben
			 */
			outFileWriter.write("Number of Classes in UML;"
					+ this.umlVector.size() + System.lineSeparator());
			outFileWriter.write("Number of Classes in EXCEL;"
					+ this.exdatah.size() + System.lineSeparator());
			outFileWriter.write("Classes not in Excel;" + this.missLNInExcel
					+ System.lineSeparator());
			outFileWriter.write("Classes not in UML;" + this.missLNInUML
					+ System.lineSeparator());
			outFileWriter.write("Classes equals;" + this.lnEquals
					+ System.lineSeparator());
			outFileWriter.write("Classes not equals;" + this.missLNEquals
					+ System.lineSeparator());
			outFileWriter.write("Attributes not in Excel;" + this.missLCInExcel
					+ System.lineSeparator());
			outFileWriter.write("Attributes not in UML;" + this.missLCInUml
					+ System.lineSeparator());
			// Reportdaten schreiben
			outFileWriter.write(output);
			// speichern und schließen
			outFileWriter.flush();
			outFileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * laden der DataObjects aus der UML
	 */
	private void loadUML() {
		try {
			/*
			 * Zeug zum einlesen der UML
			 */
			ResourceSet resourceSet = new ResourceSetImpl();
			Registry packageRegistry = resourceSet.getPackageRegistry();
			packageRegistry.put("http://www.eclipse.org/uml2/3.0.0/UML",
					UMLPackage.eINSTANCE);
			resourceSet.getPackageRegistry().put(UMLPackage.eNS_URI,
					UMLPackage.eINSTANCE);
			resourceSet
					.getResourceFactoryRegistry()
					.getExtensionToFactoryMap()
					.put(UMLResource.FILE_EXTENSION,
							UMLResource.Factory.INSTANCE);

			final String model_file_name = ConfigLoader.getInstance()
					.getUmlPath() + "iec61850.uml";

			final Resource resource = resourceSet.getResource(
					URI.createFileURI(model_file_name), true);

			TreeIterator<Object> tree = EcoreUtil
					.getAllContents(resource, true);
			// Iteration über eingelesen Daten
			while (tree.hasNext()) {
				Object o = tree.next();
				try {
					Class c = (Class) o;
					// ist Object o ein Classobjekt
					if (c != null) {
						// wenn Klassenname nicht leer und Classe einen Namespace hat
						if (c.getName().equals("") == false && c.getNamespace() != null) {
							// erzeuge neues DataObject
							DataObject daobUML = new DataObject();
							// setzte Class Name = DataObject Name
							daobUML.setName(c.getName());
							// hole Attribute der Class
							java.util.List<Property> properties = c
									.getOwnedAttributes();
							// Baue für jedes Attribut aus Class ein DataAttribut
							for (Property property : properties) {
								DataAttribute daa = new DataAttribute();
								daa.setName(property.getName());
								daa.setTyp(property.getType().getName());
								daa.setMoc("");
								daobUML.addDataAttributes(daa);
							}
							// in UMLClassStore hinzufügen
							this.umlVector.put(daobUML.getName(), daobUML);
						}
					}
				} catch (java.lang.ClassCastException e) {

				}
			}

		} catch (WrappedException we) {
			we.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UMLReporter ur = new UMLReporter();
		ur.doReport();
	}
}

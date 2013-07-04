package de.iec61850.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Vector;

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

	private ExcelDataHandler exdatah;
	private Vector<DataObject> umlVector;

	public UMLReporter() {
		exdatah = new ExcelDataHandler();
		this.umlVector = new Vector<DataObject>();
		exdatah.loadAll();
		this.loadUML();
	}

	public void doReport() {
		String output = new String("Classname;Attributname;Attributtyp;inUML;inEXCEL;isEquals"
				+ System.lineSeparator());
		for (DataObject daobUML : this.umlVector) {
			DataObject daobEXL = exdatah.find(daobUML.getName());
			if (daobEXL != null) {
				if (daobEXL.equals(daobUML) == false) {
					// Bei Auswertung attributtyp zwei spalten 
					// in uml oder in excel, bzw beides
					TreeMap<DataAttribute, Integer> summonDAttr =  new TreeMap<DataAttribute, Integer>();
					// pro attribut aus Excel
					for (DataAttribute attrExcl : daobEXL.getAttrs()) {
						// finde attribute in UML
						summonDAttr.put(attrExcl, 10);
					}
					for (DataAttribute attrUML : daobUML.getAttrs()) {
						if (summonDAttr.put(attrUML, 20) != null) {
							summonDAttr.put(attrUML, 30);
						}
						
					}
					for( DataAttribute sDA : summonDAttr.keySet()) {
						if (summonDAttr.get(sDA) <= 10) {
							output += daobEXL.getName()+";"+sDA.getName()+";"+sDA.getTyp()+";;x;n"+System.lineSeparator();
						} else if (summonDAttr.get(sDA) <= 20) {
							output += daobUML.getName()+";"+sDA.getName()+";"+sDA.getTyp()+";x;;n"+System.lineSeparator();
						} else {
							output += daobUML.getName()+";"+sDA.getName()+";"+sDA.getTyp()+";x;x;j"+System.lineSeparator();
						}
					}
				} else {
//					System.out.println("Class " + daobUML.getName() + " total true");
					output += daobUML.getName()+";;;x;x;j"+System.lineSeparator();
				}
			} else {
//				System.err.println("Class " + daobUML.getName()
//						+ " not found in Excel");
				output += daobUML.getName()+";;;x;;n"+System.lineSeparator();
			}
		}
		File outFile = new File("report.csv");
		try {
			FileWriter outFileWriter = new FileWriter(outFile);
			outFileWriter.write(output);
			outFileWriter.flush();
			outFileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void loadUML() {
		try {
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

			final String model_file_name = ConfigLoader.getInstance().getUmlPath()+"iec61850.uml";

			final Resource resource = resourceSet.getResource(
					URI.createFileURI(model_file_name), true);

			TreeIterator<Object> tree = EcoreUtil
					.getAllContents(resource, true);
			while (tree.hasNext()) {
				Object o = tree.next();
				try {
					Class c = (Class) o;
					if (c != null) {
						DataObject daobUML = new DataObject();
						daobUML.setName(c.getName());
						// System.out.println("Found Class " + c.getName());

						java.util.List<Property> properties = c
								.getOwnedAttributes();
						for (Property property : properties) {
							DataAttribute daa = new DataAttribute();
							daa.setName(property.getName());
							daa.setTyp(property.getType().getName());
							daa.setMoc("");
							daobUML.addDataAttributes(daa);
							// System.out.println("--> " + property.getName()
							// + " : " + property.getType().getName());
						}
						this.umlVector.add(daobUML);

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

package de.iec61850.main;

import de.iec61850.loader.ExcelDataHandler;

public class UMLReaderTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExcelDataHandler exdatah = new ExcelDataHandler();
		exdatah.loadAll();
		try {
			org.eclipse.emf.ecore.resource.ResourceSet resourceSet = new org.eclipse.emf.ecore.resource.impl.ResourceSetImpl();

			org.eclipse.emf.ecore.EPackage.Registry packageRegistry = resourceSet
					.getPackageRegistry();

			packageRegistry.put("http://www.eclipse.org/uml2/3.0.0/UML",

			org.eclipse.uml2.uml.UMLPackage.eINSTANCE);

			resourceSet.getPackageRegistry().put(

			org.eclipse.uml2.uml.UMLPackage.eNS_URI,

			org.eclipse.uml2.uml.UMLPackage.eINSTANCE);

			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()

			.put(org.eclipse.uml2.uml.resource.UMLResource.FILE_EXTENSION,

			org.eclipse.uml2.uml.resource.UMLResource.Factory.INSTANCE);

			final String model_file_name = "uml/iec61850.uml";

			final org.eclipse.emf.ecore.resource.Resource resource = resourceSet

			.getResource(org.eclipse.emf.common.util.URI

			.createFileURI(model_file_name), true);

			org.eclipse.emf.common.util.TreeIterator<Object> tree = org.eclipse.emf.ecore.util.EcoreUtil
					.getAllContents(resource, true);
			while (tree.hasNext()) {
				Object o = tree.next();
				try {
					org.eclipse.uml2.uml.Class c = (org.eclipse.uml2.uml.Class) o;
					if (c != null) {
						String output = new String();
						DataObject daobUML = new DataObject();
						daobUML.setName(c.getName());
						// System.out.println("Found Class " + c.getName());

						java.util.List<org.eclipse.uml2.uml.Property> properties = c
								.getOwnedAttributes();
						for (org.eclipse.uml2.uml.Property property : properties) {
							DataAttribute daa = new DataAttribute();
							daa.setName(property.getName());
							daa.setTyp(property.getType().getName());
							daobUML.addDataAttributes(daa);
							// System.out.println("--> " + property.getName()
							// + " : " + property.getType().getName());
						}

						DataObject daobEXL = exdatah.find(daobUML.getName());
						if (daobEXL != null) {
							// System.out.println("Found match ->> "
							// + daobUML.getName());

							for (String attrname : daobEXL.getAttrNamen()) {
								DataAttribute daobUmlAttr = daobUML
										.getAttr(attrname);
								if (daobUmlAttr == null) {
									// System.out.println(attrname
									// + " not found in UML");
									output += attrname + " not found in UML";
								}
							}
							for (String attrname : daobUML.getAttrNamen()) {
								DataAttribute daobUmlAttr = daobEXL
										.getAttr(attrname);
								if (daobUmlAttr == null) {
									// System.out.println(attrname
									// + " not found in Excel");
									output += attrname + " not found in UML";
								}
							}
						} else {
							// System.err.println("Class " + daobUML.getName() +
							// " not found in Excel");
							output += daobUML.getName() + " not found in UML";
						}
						System.out.println(output);
					}
				} catch (java.lang.ClassCastException e) {

				}
				// System.out.println();
			}

		} catch (org.eclipse.emf.common.util.WrappedException we) {
			we.printStackTrace();
			System.exit(1);
		}

	}
}

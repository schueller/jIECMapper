package de.iec61850.main;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class UMLModel {

	private String l_uml;
	private Document l_doc;
	Vector<Type> l_types;

	public UMLModel(File uml) {
		// check if file end with .uml
		this.l_types = new Vector<Type>();
		if (uml.getName().toLowerCase().endsWith(".uml")) {
			try {
				this.l_doc = new SAXBuilder().build(uml);
				this.getPrimitveTypes(this.l_doc.getRootElement().getChildren());
				this.findClass("WTUR");
				System.out.println(this.l_types);
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
			}
		} else {
			this.l_uml = "wrong file extension";
			System.err.println("wrong file extension");
		}
	}

	private void findClass(String name) {
		for (Element child : this.l_doc.getRootElement().getChildren()) {
			if (child.getAttributeValue("type", Namespace
					.getNamespace("http://schema.omg.org/spec/XMI/2.1")) != null) {
				if (child
						.getAttributeValue(
								"type",
								Namespace
										.getNamespace("http://schema.omg.org/spec/XMI/2.1"))
						.equals("uml:Class")) {
					System.out.println("found");
					break;
				}
			}

		}
	}

	private void getPrimitveTypes(List<Element> childs) {
		Element pt = null;
		for (Element child : childs) {
			if (child.getAttribute("name") != null) {
				// System.out.println(child.getAttributeValue("name"));
				if ((child.getAttributeValue("name").equals("PrimitiveTypes"))) {
					// System.out.println(child);
					pt = child;
					break;
				}
			}
		}
		if (pt != null) {
			// System.out.println(pt);
			this.getPrimitiveType(pt.getChildren());
		}
	}

	private void getPrimitiveType(List<Element> childs) {
		for (Element child : childs) {
			// System.out.println(child.getAttributes().get(0).getNamespaceURI());
			// System.out.println(child.getAttributeValue("type", Namespace
			// .getNamespace("http://schema.omg.org/spec/XMI/2.1")));
			this.l_types
					.add(new Type(
							child.getAttributeValue("name"),
							child.getAttributeValue(
									"id",
									Namespace
											.getNamespace("http://schema.omg.org/spec/XMI/2.1")),
							child.getAttributeValue(
									"type",
									Namespace
											.getNamespace("http://schema.omg.org/spec/XMI/2.1"))));
		}
	}

	public static void main(String args[]) {
		System.out.println("UML Model");
		new UMLModel(new File("uml/iec61850.uml"));
	}

}

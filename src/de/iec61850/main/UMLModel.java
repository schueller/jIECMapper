package de.iec61850.main;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UMLModel {

	private String l_uml;

	public UMLModel(File uml) {
		if (uml.getName().toLowerCase().endsWith(".uml")) {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(true);
			try {
				DocumentBuilder builder = domFactory.newDocumentBuilder();
				Document doc = builder.parse(uml);
				XPath xpath = XPathFactory.newInstance().newXPath();
				XPathExpression expr = xpath
						.compile("//packagedElement[@name='Model']/*[@xmi:id]");
				Object result = expr.evaluate(doc, XPathConstants.NODESET);
				NodeList nodes = (NodeList) result;
				for (int i = 0; i < nodes.getLength(); i++) {
					System.out.println(nodes.item(i));
				}
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			this.l_uml = "wrong file extension";
			System.err.println("wrong file extension");
		}
	}

	public static void main(String args[]) {
		System.out.println("UML Model");
		new UMLModel(new File("uml/iec61850.uml"));
	}

}

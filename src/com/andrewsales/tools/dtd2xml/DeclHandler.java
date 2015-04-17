package com.andrewsales.tools.dtd2xml;

import org.xml.sax.SAXException;

public class DeclHandler implements org.xml.sax.ext.DeclHandler {

	private DTD2XML parser;

	DeclHandler(DTD2XML parser) {
		this.parser = parser;
	}

	@Override
	public void attributeDecl(String eName, String aName, String type,
			String mode, String value) throws SAXException {
		parser.getDTD().addAttributeDecl(
				new AttributeDecl(eName, aName, type, mode, value));
	}

	@Override
	public void elementDecl(String name, String model) throws SAXException {
		parser.getDTD().addElementDecl(name, model);
	}

	@Override
	public void externalEntityDecl(String name, String publicId, String systemId)
			throws SAXException {
		parser.getDTD().addEntityDecl(new ExternalEntityDecl(name, publicId, systemId));

	}

	@Override
	public void internalEntityDecl(String name, String value)
			throws SAXException {
		if (!name.startsWith("%")) // a parameter entity
			parser.getDTD().addEntityDecl(new InternalEntityDecl(name, value));
	}

}

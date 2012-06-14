package com.andrewsales.tools.dtd2xml;

import org.xml.sax.SAXException;

public class DTDHandler implements org.xml.sax.DTDHandler {

	DTD2XML parser;
	
	DTDHandler(DTD2XML parser) {
		this.parser = parser;
	}
	
	@Override
	public void notationDecl(String name, String publicId, String systemId)
			throws SAXException {
		parser.getDTD().addNotationDecl( new NotationDecl(name, publicId, systemId));

	}

	@Override
	public void unparsedEntityDecl(String name, String publicId, String systemId,
			String notationName) throws SAXException {
		
//		System.err.println(name);

	}

}

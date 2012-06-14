package com.andrewsales.tools.dtd2xml;

import org.xml.sax.SAXException;

public class LexicalHandler implements org.xml.sax.ext.LexicalHandler {

	private DTD2XML parser;
	
	LexicalHandler(DTD2XML parser) {
		this.parser = parser;
	}

	@Override
	public void comment(char[] arg0, int arg1, int arg2) throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void endCDATA() throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void endDTD() throws SAXException {
		parser.getDTD().resolveParents();
		parser.getDTD().resolveAttributeDecls();
	}

	@Override
	public void endEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void startCDATA() throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void startDTD(String name, String publicId, String systemId)
			throws SAXException {
		this.parser.getDTD().setPublicId(publicId);
		this.parser.getDTD().setSystemId(systemId);
	}

	@Override
	public void startEntity(String name) throws SAXException {
//		System.err.println(name);

	}

}

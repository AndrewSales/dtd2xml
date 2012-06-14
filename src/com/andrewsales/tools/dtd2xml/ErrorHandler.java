package com.andrewsales.tools.dtd2xml;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ErrorHandler implements org.xml.sax.ErrorHandler {

	@Override
	public void error(SAXParseException arg0) throws SAXException {
		System.err.println("error: "+arg0);

	}

	@Override
	public void fatalError(SAXParseException arg0) throws SAXException {
		System.err.println("FATAL: "+arg0);

	}

	@Override
	public void warning(SAXParseException arg0) throws SAXException {
		System.err.println("warning: "+arg0);

	}

}

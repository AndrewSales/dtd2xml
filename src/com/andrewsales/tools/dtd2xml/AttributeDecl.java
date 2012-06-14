package com.andrewsales.tools.dtd2xml;

public class AttributeDecl {

	private String name;
	private String type;
	private String mode;
	private String value;
	private String element;

	AttributeDecl(String element, String name, String type, String mode,
			String value) {
		this.name = name;
		this.type = type;
		this.mode = mode;
		this.value = value;
		this.element = element;
	}
	
	String getElementName(){
		return this.element;
	}

	String getName() {
		return name;
	}

	String getType() {
		return type;
	}

	String getMode() {
		return mode;
	}

	String getValue() {
		return value;
	}
	
	public String asXML(){
		String s = "<attribute name=\"" + name + "\" type=\"" + type + "\"";
		if( mode != null)
			s += " mode=\"" + mode + "\"";
		if(value != null)
			s += " value=\"" + value+ "\"";
		s += "/>";
		return s;
	}

}

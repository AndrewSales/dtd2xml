package com.andrewsales.tools.dtd2xml;


public class InternalEntityDecl implements EntityDecl {
	
	private String name;
	private String value;
	
	InternalEntityDecl(String name, String value){
		this.name = name;
		this.value = value;
	}

	@Override
	public String asXML() {
		String s = "";
		return "<internalEntity name=\"" +  name + "\" value=\"" + 
				(value.equals("\"") ? "&quot;" : UnicodeFormatter.encodeAsAsciiXml(value)) 
				+ "\"/>";
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int compareTo(EntityDecl o) {
		// TODO Auto-generated method stub
		return o.getName().compareTo(getName());
	}

}

package com.andrewsales.tools.dtd2xml;

public class NotationDecl implements Comparable<NotationDecl> {
	
	private String name;
	private String publicId;
	private String systemId;
	
	NotationDecl(String name, String publicId, String systemId) {
		this.name = name;
		this.publicId = publicId;
		this.systemId = systemId;
	}
	
	public String asXML(){
		String s = "<notation name='" + name + "'";
		if( publicId != null)
			s += " publicId='" + publicId + "'";
		if( systemId != null)
			s += " systemId='" + systemId + "'";
		return s + "/>";
	}

	@Override
	public int compareTo(NotationDecl o) {
		return this.name.compareTo(o.name);
	}
}

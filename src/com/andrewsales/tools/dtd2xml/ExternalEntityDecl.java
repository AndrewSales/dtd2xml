package com.andrewsales.tools.dtd2xml;

public class ExternalEntityDecl implements EntityDecl {
	
	private String name;
	private String publicId;
	private String systemId;
	
	public ExternalEntityDecl(String name, String publicId, String systemId){
		this.name = name;
		this.publicId = publicId;
		this.systemId = systemId;
	}

	@Override
	public String asXML() {
		String s = "<externalEntity name=\"" + name + "\"";
		if( publicId != null)
			s += " publicId=\"" + publicId + "\"";
		if( systemId != null)
			s += " systemId=\"" + systemId + "\"";
		return s + "/>";
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(EntityDecl o) {
		return name.compareTo(o.getName());
	}

}

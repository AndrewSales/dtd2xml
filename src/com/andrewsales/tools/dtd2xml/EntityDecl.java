package com.andrewsales.tools.dtd2xml;

public interface EntityDecl extends Comparable<EntityDecl> {
	String asXML();
	
	String getName();

	@Override
	public int compareTo(EntityDecl o);
}

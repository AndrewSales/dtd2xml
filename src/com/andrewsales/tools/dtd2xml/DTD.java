package com.andrewsales.tools.dtd2xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DTD {

	private HashMap<String, ElementDecl> elemDecls;
	private List<AttributeDecl> attDecls;
	private List<NotationDecl> notationDecls;
	private List<EntityDecl> entityDecls;

	private String systemId;
	private String publicId;

	DTD() {
		elemDecls = new HashMap<String, ElementDecl>();
		attDecls = new ArrayList<AttributeDecl>();
		notationDecls = new ArrayList<NotationDecl>();
		entityDecls = new ArrayList<EntityDecl>();
	}

	void addElementDecl(String name, String model) {
		if (elemDecls.containsKey(name))
			System.err.println("element " + name + " already declared!");
		elemDecls.put(name, new ElementDecl(name, model, this));
	}

	Map<String, ElementDecl> getElementDecls() {
		return elemDecls;
	}

	void addAttributeDecl(AttributeDecl a) {
		attDecls.add(a);
	}

	void addNotationDecl(NotationDecl n) {
		notationDecls.add(n);
	}
	
	void addEntityDecl(EntityDecl e){
		entityDecls.add(e);
	}

	void resolveAttributeDecls() {
		Iterator<AttributeDecl> iter = attDecls.iterator();
		while (iter.hasNext()) {
			AttributeDecl attDecl = (AttributeDecl) iter.next();
			String elementName = attDecl.getElementName();
			ElementDecl e = elemDecls.get(elementName);
			if (e != null) {
				e.addAttributeDecl(attDecl);
			} else {
				System.err.println("no such element " + elementName
						+ " for attribute " + attDecl.getName());
			}
		}
	}

	public String asXML() {
		StringBuffer s = new StringBuffer("<dtd");

		if (systemId != null)
			s.append(" systemId=\"" + systemId + "\"");

		if (publicId != null)
			s.append(" publicId=\"" + publicId + "\"");

		s.append(">\n");

		s.append(elementDeclsAsXML());
		s.append(notationDeclsAsXML());
		s.append(entityDeclsAsXML());

		s.append("</dtd>");

		return s.toString();
	}

	private StringBuffer elementDeclsAsXML() {
		StringBuffer s = new StringBuffer();

		Map<String, ElementDecl> sortedMap = new TreeMap<String, ElementDecl>(
				elemDecls);
		Collection<ElementDecl> values = sortedMap.values();
		Iterator<ElementDecl> iter = values.iterator();
		while (iter.hasNext()) {
			ElementDecl e = (ElementDecl) iter.next();
			s.append(e.asXML());
			s.append("\n");
		}
		return s;
	}

	private StringBuffer notationDeclsAsXML() {
		StringBuffer s = new StringBuffer();

		Collections.sort(notationDecls);
		Iterator<NotationDecl> iter = notationDecls.iterator();
		while (iter.hasNext()) {
			NotationDecl e = (NotationDecl) iter.next();
			s.append(e.asXML());
			s.append("\n");
		}
		return s;
	}
	
	private StringBuffer entityDeclsAsXML() {
		StringBuffer s = new StringBuffer();

		Iterator<EntityDecl> iter = entityDecls.iterator();
		while (iter.hasNext()) {
			EntityDecl e = (EntityDecl) iter.next();
			s.append(e.asXML());
			s.append("\n");
		}
		return s;
	}

	void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	void resolveParents() {
		Collection<ElementDecl> elems = elemDecls.values();
		Iterator<ElementDecl> iter = elems.iterator();
		while (iter.hasNext()) {
			ElementDecl elementDecl = (ElementDecl) iter.next();
			String parent = elementDecl.getName();
			Iterator<String> childIterator = elementDecl.getChildren()
					.iterator();
			while (childIterator.hasNext()) {
				String child = (String) childIterator.next();
				getElementDeclByName(child).addParent(parent);
			}
		}
	}

	ElementDecl getElementDeclByName(String name) {
		return this.elemDecls.get(name);
	}
}
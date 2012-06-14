package com.andrewsales.tools.dtd2xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class ElementDecl {

	private String name;
	private String model;
	private DTD dtd;
	private HashMap<String, AttributeDecl> attributes;
	private static final String TOKENS = "()*+?,|";
	private String tokenizedModel;
	private HashMap<String, String> parents;
	private HashMap<String, String> children;

	ElementDecl(String name, String model, DTD dtd) {
		this.name = name;
		this.model = model;
		this.dtd = dtd;
		this.attributes = new HashMap<String, AttributeDecl>();
		parents = new HashMap<String, String>();
		children = new HashMap<String,String>();
		tokenizedModel = tokenizeModel();
	}

	void addParent(String parent) {
		parents.put(parent, parent);
	}

	String getName() {
		return this.name;
	}

	String getModel() {
		return this.model;
	}

	private String tokenizeModel() {
		if (tokenizedModel == null) {
			StringTokenizer st = new StringTokenizer(model, TOKENS, true);
			StringBuffer sb = new StringBuffer();
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				if (TOKENS.contains(token)) {
					if (token.equals(",")) {
						sb.append(", ");
					} else if (token.equals("|")) {
						sb.append(" | ");
					} else
						sb.append(token);
				} else if (token.equals("EMPTY") || token.equals("ANY")
						|| token.equals("#PCDATA")) {
					sb.append(token);
				} else {
					sb.append("<xref target='" + token.replace(':', '_') + "'>"
							+ token + "</xref>");
					children.put(token, token);
				}
			}
			return sb.toString();
		}
		return tokenizedModel;
	}

	void addAttributeDecl(AttributeDecl a) {
		if (attributes.containsKey(a.getName()))
			System.err.println("attribute " + a.getName()
					+ " already declared for element " + name);
		attributes.put(a.getName(), a);
	}

	String escapedName() {
		return name.replace(':', '_');
	}

	public String asXML() {
		String open = "<element id=\"" + escapedName() + "\" name=\"" + name
				+ "\">\n<model>";
		open += tokenizedModel + "</model>\n";
		Map<String, AttributeDecl> sortedMap = new TreeMap<String, AttributeDecl>(
				attributes);
		Collection<AttributeDecl> values = sortedMap.values();
		Iterator<AttributeDecl> iter = values.iterator();
		while (iter.hasNext()) {
			AttributeDecl attDecl = (AttributeDecl) iter.next();
			open += attDecl.asXML() + "\n";
		}
		open += "<parents>";
		List<String> parents = new ArrayList<String>(this.parents.keySet());
		Collections.sort(parents);
		if (parents != null) {
			Iterator<String> pi = parents.iterator();
			while (pi.hasNext()) {
				String parent = (String) pi.next();
				open += "<xref target='" + parent.replace(':', '_') + "'>"
						+ parent + "</xref>";
			}
		}
		return open + "</parents>\n</element>";
	}
	
	Collection<String> getChildren(){
		return children.values();
	}
}
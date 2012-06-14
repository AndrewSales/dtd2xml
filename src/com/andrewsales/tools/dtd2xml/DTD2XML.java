package com.andrewsales.tools.dtd2xml;

import java.io.IOException;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Generates an XML representation of an XML DTD.
 * 
 * The document produced will have the format, in RELAX NG syntax:
 * 
 * <pre>start =
    element dtd {
        attribute systemId { text },
        attribute publicId { text }?,
        element element {
            attribute id { xsd:ID },
            attribute name { xsd:NMTOKEN },
            element model { (text | xref)+ },
            element attribute {
                attribute mode { text }?,
                attribute name { xsd:NMTOKEN },
                attribute type { text },
                attribute value { text }?
            }*,
            element parents {
                xref*
            }
        }+,
        element notation {
        	attribute name { xsd:NMTOKEN },
        	(attribute publicId { text }|attribute systemId { text })
        }*
    }
xref =
    element xref {
        attribute target { xsd:IDREF },
        text
    }</pre>
 * 
 * <p>Element declarations are sorted alphabetically by element name.
 * Attribute declarations are sorted alphabetically within elements.</p>
 * 
 * <p>The <code>id</code> attribute will be the same as the element name,
 * with any colons replaced by an underscore, to meet the requirements of
 * type <code>ID</code>.</p>
 * 
 * <p>An empty <code>parents</code> element means that the element is a root
 * element.</p>
 * 
 * @author salesan
 *
 */
public class DTD2XML {

	private XMLReader parser;
	private ErrorHandler errorHandler;
	private DeclHandler declHandler;
	private LexicalHandler lexicalHandler;
	private DTDHandler dtdHandler;
	private DTD dtd;

	DTD2XML() throws SAXException {
		parser = XMLReaderFactory.createXMLReader();
		errorHandler = new ErrorHandler();
		parser.setErrorHandler(errorHandler);
		declHandler = new DeclHandler(this);
		parser.setProperty("http://xml.org/sax/properties/declaration-handler",
				declHandler);
		lexicalHandler = new LexicalHandler(this);
		parser.setProperty("http://xml.org/sax/properties/lexical-handler",
				lexicalHandler);
		dtdHandler = new DTDHandler( this );
		parser.setDTDHandler(dtdHandler);
		dtd = new DTD();
	}

	DTD getDTD() {
		return dtd;
	}

	void parse(String uri) throws IOException, SAXException {
		parser.parse(uri);
	}

	private static void usage() {
		System.err.println("Generates an XML representation of an XML 1.0 DTD.");
		System.err.println("usage: DTD2XML.jar instance-uri");
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			DTD2XML.usage();
			System.exit(-1);
		}
		DTD2XML dtdParser = null;
		try {
			dtdParser = new DTD2XML();

		} catch (SAXException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		try {
			dtdParser.parse(args[0]);
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			System.exit(-1);
		} catch (SAXException e) {
			System.err.println("SAXException: " + e.getMessage());
			System.exit(-1);
		}

		System.out.print(dtdParser.getDTD().asXML());
	}

}

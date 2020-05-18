dtd2xml
=======

Creates an XML representation of an XML 1.0 DTD

This tool generates an XML representation of an XML 1.0 DTD.

Usage syntax is:

`java -jar DTDParser.jar my-instance.xml`

where `my-instance.xml` is a document containing a `DOCTYPE` declaration to the DTD.
Note that this instance does not have to be valid to the DTD.
The XML representation of the DTD appears on standard output.

The format of the XML produced is described below, as per the schema in `rnc/dtd2xml.rnc`.

<pre>start =
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

To produce a normalised version of the DTD, run the stylesheet `xsl/norm-dtd.xsl` on the output.
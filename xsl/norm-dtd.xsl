<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">
    
    <xsl:output method="text"/>
    
    <xsl:strip-space elements="*"/>
    
    <doc xmlns="http://www.oxygenxml.com/ns/doc/xsl">
        <desc>Produces a normalised version of an XML 1.0 DTD. The input to this stylesheet should be output from the dtd2xml 
            (https://github.com/AndrewSales/dtd2xml) tool.</desc>
    </doc>
    
    <xsl:template match="/dtd">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="element">
        <xsl:text>&lt;!ELEMENT </xsl:text>
        <xsl:value-of select="@name"/>
        <xsl:text> </xsl:text>
        <xsl:value-of select="model"/>
        <xsl:text>>&#xA;</xsl:text>
        <xsl:if test="attribute">
            <xsl:text>&lt;!ATTLIST </xsl:text>
            <xsl:value-of select="@name"/>
            <xsl:text>&#xA;</xsl:text>
            <xsl:apply-templates select="attribute"/>
            <xsl:text>>&#xA;&#xA;</xsl:text>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="attribute">
        <xsl:text>          </xsl:text>
        <xsl:value-of select="@name"/>
        <xsl:text> </xsl:text>
        <xsl:value-of select="@type"/>
        <xsl:if test="@mode">
            <xsl:text> </xsl:text>
            <xsl:value-of select="@mode"/>
        </xsl:if>
        <xsl:if test="@value">
            <xsl:text> "</xsl:text>
            <xsl:value-of select="@value"/>
            <xsl:text>"</xsl:text>
        </xsl:if>
        <xsl:if test="following-sibling::attribute">
            <xsl:text>&#xA;</xsl:text>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="internalEntity">
        <xsl:text>&lt;!ENTITY </xsl:text>
        <xsl:value-of select="@name"/>
        <xsl:text> "&amp;#</xsl:text>
        <xsl:value-of select="string-to-codepoints(@value)"/>
        <xsl:text>;">&#xA;</xsl:text>
    </xsl:template>
    
</xsl:stylesheet>
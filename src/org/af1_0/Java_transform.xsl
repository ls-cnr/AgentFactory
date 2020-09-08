<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" indent="yes" encoding="UTF-8"/>
	
	<xsl:template match="Agent">
		<xsl:apply-templates select="Visibility"/>&#160;
		<xsl:apply-templates select="Modifier"/>
		class <xsl:value-of select="@name"/> 
		<xsl:apply-templates select="Extends"/>
		<xsl:apply-templates select="Implements"/>
		{
		<xsl:apply-templates select="Attribute"/>
		<xsl:apply-templates select="Constructor"/>
		<xsl:apply-templates select="Method"/>
		<xsl:apply-templates select="Task"/>
		}
	</xsl:template>
	<xsl:template match="Task">
		<xsl:apply-templates select="Visibility"/>&#160;
		<xsl:apply-templates select="Modifier"/>
		class <xsl:value-of select="@name"/> 
		<xsl:apply-templates select="Extends"/>
		<xsl:apply-templates select="Implements"/>
		{
		<xsl:apply-templates select="Attribute"/>
		<xsl:apply-templates select="Constructor"/>
		<xsl:apply-templates select="Method"/>
		}
	</xsl:template>

	<xsl:template match="Attribute">
		<xsl:apply-templates select="Visibility"/>&#160;
		<xsl:apply-templates select="Modifier"/>
		<xsl:value-of select="@type"/>&#160;
		<xsl:value-of select="@name"/>&#160;
		<xsl:apply-templates select="Value"/>&#160;
		;
	</xsl:template>
	<xsl:template match="Constructor">
		<xsl:apply-templates select="Visibility"/>&#160;
		<xsl:apply-templates select="Modifier"/>
		<xsl:value-of select="@name"/>&#160;
		<xsl:apply-templates select="Throws"/>
		(
		<xsl:apply-templates select="Argoment"/>
		) {
		<xsl:apply-templates select="Code"/>
		}
	</xsl:template>
	<xsl:template match="Method">
		<xsl:apply-templates select="Visibility"/>&#160;
		<xsl:apply-templates select="Modifier"/>
		<xsl:value-of select="@type"/>&#160;
		<xsl:value-of select="@name"/>&#160;
		<xsl:apply-templates select="Throws"/>
		(
		<xsl:apply-templates select="Argoment"/>
		) {
		<xsl:apply-templates select="Code"/>
		}
	</xsl:template>

	<xsl:template match="Modifier">
		<xsl:value-of select="text()"/>&#160;
	</xsl:template>
	<xsl:template match="Visibility">
		<xsl:value-of select="text()"/>
	</xsl:template>
	<xsl:template match="Extends">
		extends <xsl:value-of select="text()"/>
	</xsl:template>
	<xsl:template match="Implements">
		<xsl:if test="position()=1">implements </xsl:if>
		<xsl:if test="position()>1">, </xsl:if>
		<xsl:value-of select="text()"/>
	</xsl:template>
	<xsl:template match="Value">
		= <xsl:value-of select="text()"/>
	</xsl:template>
	<xsl:template match="Argoment">
		<xsl:if test="position()>1">, </xsl:if>
		<xsl:value-of select="@type"/>&#160;
		<xsl:value-of select="@name"/>
	</xsl:template>
	<xsl:template match="Code">
		#^<xsl:value-of select="text()"/>^#
	</xsl:template>
	<xsl:template match="Throws">
		<xsl:if test="position()=1">throws </xsl:if>
		<xsl:if test="position()>1">, </xsl:if>
		<xsl:value-of select="text()"/>
	</xsl:template>


</xsl:stylesheet>

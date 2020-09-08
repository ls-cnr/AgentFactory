<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" indent="yes" encoding="UTF-8"/>
	
	<xsl:template match="Agent">
		<xsl:apply-templates select="Visibility"/> <xsl:text> </xsl:text>
		<xsl:apply-templates select="Modifier"/> <xsl:text> </xsl:text>
		class <xsl:value-of select="@name"/> <xsl:text> </xsl:text> 
		<xsl:apply-templates select="Extends"/> <xsl:text> </xsl:text>
		<xsl:apply-templates select="Implements"/> <xsl:text> </xsl:text>
		{
		<xsl:apply-templates select="Attribute"/> <xsl:text> </xsl:text>
		<xsl:apply-templates select="Constructor"/> <xsl:text> </xsl:text>
		<xsl:apply-templates select="Method"/> <xsl:text> </xsl:text>
		<xsl:apply-templates select="Task"/> <xsl:text> </xsl:text>
		}
	</xsl:template>
	<xsl:template match="Task">
		<xsl:apply-templates select="Visibility"/> <xsl:text> </xsl:text>
		<xsl:apply-templates select="Modifier"/> <xsl:text> </xsl:text>
		class <xsl:value-of select="@name"/> <xsl:text> </xsl:text> 
		<xsl:apply-templates select="Extends"/> <xsl:text> </xsl:text>
		<xsl:apply-templates select="Implements"/> <xsl:text> </xsl:text>
		{
		<xsl:apply-templates select="Attribute"/> <xsl:text> </xsl:text>
		<xsl:apply-templates select="Constructor"/> <xsl:text> </xsl:text>
		<xsl:apply-templates select="Method"/> <xsl:text> </xsl:text>
		}
	</xsl:template>

	<xsl:template match="Attribute">
		<xsl:apply-templates select="Visibility"/><xsl:text> </xsl:text>
		<xsl:apply-templates select="Modifier"/> <xsl:text> </xsl:text>
		<xsl:value-of select="@type"/> <xsl:text> </xsl:text>
		<xsl:value-of select="@name"/> <xsl:text> </xsl:text>
		<xsl:apply-templates select="Value"/> <xsl:text> </xsl:text>
		;
	</xsl:template>
	<xsl:template match="Constructor">
                <xsl:if test="boolean(ActionPattern)">
                /** 
                 * @PCL action_pattern=<xsl:value-of select="ActionPattern/text()"/>
                <xsl:for-each select="Activate">* @PCL activate=<xsl:value-of select="Activate/text()"/></xsl:for-each>               
                 */
                </xsl:if>
		<xsl:apply-templates select="Visibility"/> <xsl:text> </xsl:text>
		<xsl:apply-templates select="Modifier"/> <xsl:text> </xsl:text>
		<xsl:value-of select="@name"/> <xsl:text> </xsl:text>
		<xsl:apply-templates select="Throws"/><xsl:text> </xsl:text>
		(
		<xsl:apply-templates select="Argoment"/><xsl:text> </xsl:text>
		) {
		<xsl:apply-templates select="ActionPattern"/><xsl:text> </xsl:text>
		<xsl:apply-templates select="Code"/><xsl:text> </xsl:text>
		}
	</xsl:template>
	<xsl:template match="Method">
                <xsl:if test="boolean(ActionPattern)">
                /** 
                 * @PCL action_pattern=<xsl:value-of select="ActionPattern/text()"/>
                <xsl:for-each select="Activate">* @PCL activate=<xsl:value-of select="Activate/text()"/></xsl:for-each>               
                 */
                </xsl:if>
		<xsl:apply-templates select="Visibility"/> <xsl:text> </xsl:text>
		<xsl:apply-templates select="Modifier"/><xsl:text> </xsl:text>
		<xsl:value-of select="@type"/> <xsl:text> </xsl:text>
		<xsl:value-of select="@name"/> <xsl:text> </xsl:text>
		<xsl:apply-templates select="Throws"/><xsl:text> </xsl:text>
		(
		<xsl:apply-templates select="Argoment"/><xsl:text> </xsl:text>
		) {
		<xsl:apply-templates select="ActionPattern"/><xsl:text> </xsl:text>
		<xsl:apply-templates select="Code"/><xsl:text> </xsl:text>
		}
	</xsl:template>

	<xsl:template match="Modifier">
		<xsl:value-of select="text()"/><xsl:text> </xsl:text>
	</xsl:template>
	<xsl:template match="Visibility">
		<xsl:value-of select="text()"/><xsl:text> </xsl:text>
	</xsl:template>
	<xsl:template match="Extends">
		extends <xsl:value-of select="text()"/><xsl:text> </xsl:text>
	</xsl:template>
	<xsl:template match="Implements">
		<xsl:if test="position()=1">implements </xsl:if>
		<xsl:if test="position()>1">, </xsl:if>
		<xsl:value-of select="text()"/>
	</xsl:template>
	<xsl:template match="Value">
		= <xsl:value-of select="text()"/><xsl:text> </xsl:text>
	</xsl:template>
	<xsl:template match="Argoment">
		<xsl:if test="position()>1">, </xsl:if>
		<xsl:value-of select="@type"/> <xsl:text> </xsl:text>
		<xsl:value-of select="@name"/>
	</xsl:template>
	<xsl:template match="ActionPattern">
		#^<xsl:value-of select="text()"/>^#
	</xsl:template>
	<xsl:template match="Code">
		<xsl:value-of select="text()"/>
	</xsl:template>
	<xsl:template match="Throws">
		<xsl:if test="position()=1">throws </xsl:if>
		<xsl:if test="position()>1">, </xsl:if>
		<xsl:value-of select="text()"/>
	</xsl:template>


</xsl:stylesheet>
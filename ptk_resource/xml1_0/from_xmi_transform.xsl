<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:UML="//org.omg/UML/1.3">
	<xsl:template match="XMI">
		<Model>
			<xsl:for-each select="XMI.content/UML:Model/UML:Namespace.ownedElement">
				<xsl:apply-templates select="UML:Package"/>
			</xsl:for-each>
			<xsl:for-each select="XMI.content/UML:Model/UML:Namespace.ownedElement/UML:DataType">
				<Datatype id="{@xmi.id}" type="{@name}"></Datatype>
			</xsl:for-each>
		</Model>
	</xsl:template>
	<xsl:template match="UML:Package">
		<xsl:for-each select="UML:Namespace.ownedElement/UML:Class">
			<xsl:if test="@name=../../@name">
				<Agent name="{@name}">
					<xsl:for-each select="UML:Classifier.feature">
						<xsl:apply-templates select="UML:Attribute"/>
						<xsl:apply-templates select="UML:Operation"/>
						<xsl:for-each select="../../UML:Class">
							<xsl:if test="@name!=../../@name">
								<Task name="{@name}">
								
									<xsl:for-each select="UML:Classifier.feature">
										<xsl:apply-templates select="UML:Attribute"/>
										<xsl:apply-templates select="UML:Operation"/>
									</xsl:for-each>
								
								</Task>
							</xsl:if>
						</xsl:for-each>
					</xsl:for-each>
				</Agent>
			</xsl:if>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="UML:Attribute">
		<Attribute name="{@name}" typeref="{UML:StructuralFeature.type/Foundation.Core.Classifier/@xmi.idref}"><Value><xsl:value-of select="UML:Attribute.initialValue/UML:Expression/@body"></xsl:value-of></Value></Attribute>
	</xsl:template>
	<xsl:template match="UML:Operation">
		<Method name="{@name}" typeref="{UML:BehavioralFeature.parameter/UML:Parameter[@kind='return']/UML:Parameter.type/Foundation.Core.Classifier/@xmi.idref}">
		<xsl:for-each select="UML:BehavioralFeature.parameter/UML:Parameter">
		<xsl:if test="@kind!='return'">
		<Argoment name="{@name}" typeref="{UML:Parameter.type/Foundation.Core.Classifier/@xmi.idref}"></Argoment></xsl:if></xsl:for-each>
	</Method>
	</xsl:template>
</xsl:stylesheet>

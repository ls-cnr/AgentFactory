<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:UML="//org.omg/UML/1.3">
	<xsl:template match="Model">
		<XMI xmi.version="1.1" xmlns:UML="//org.omg/UML/1.3" timestamp="Mon Mar 03 11:28:30 2003">
			<XMI.header>
				<XMI.documentation>
					<XMI.exporter>AgentFactory</XMI.exporter>
					<XMI.exporterVersion>0.2.3</XMI.exporterVersion>
				</XMI.documentation>
				<XMI.metamodel xmi.name="UML" xmi.version="1.3"/>
			</XMI.header>
			<XMI.content>
				<UML:Model xmi.id="{@platform}_model" name="{@platform}_agent" visibility="public" isSpecification="false" isRoot="false" isLeaf="false" isAbstract="false">
					<UML:Namespace.ownedElement>
						<xsl:apply-templates select="Agent">
						
						</xsl:apply-templates>
						<!-- inserire AgentShell e taskShell -->
						<UML:Class xmi.id="AgentShell_class" name="AgentShell" visibility="public" isSpecification="false" isRoot="true" isLeaf="false" isAbstract="false" isActive="false">
							<UML:ModelElement.namespace>
								<Foundation.Core.Namespace xmi.idref="{@platform}_model"/>
							</UML:ModelElement.namespace>
							<UML:GeneralizableElement.specialization>
								<xsl:for-each select="Agent">
									<Foundation.Core.Generalization xmi.idref="{@name}_class"/>
								</xsl:for-each>
							</UML:GeneralizableElement.specialization>
						</UML:Class>
						<!-- ==================== agente_visione::TaskShell    [Class] ==================== -->
						<UML:Class xmi.id="TaskShell_class" name="TaskShell" visibility="public" isSpecification="false" isRoot="true" isLeaf="false" isAbstract="false" isActive="false">
							<UML:ModelElement.namespace>
								<Foundation.Core.Namespace xmi.idref="{@platform}_model"/>
							</UML:ModelElement.namespace>
							<UML:GeneralizableElement.specialization>
								<xsl:for-each select="Agent">
									<xsl:for-each select="Task">
										<Foundation.Core.Generalization xmi.idref="{../@name}_{@name}_class"/>
									</xsl:for-each>
								</xsl:for-each>
							</UML:GeneralizableElement.specialization>
						</UML:Class>
						<!-- inserire Stereotipi -->
						<UML:Stereotype xmi.id="Agent_stereotype" name="Agent" visibility="public" isSpecification="false" isRoot="false" isLeaf="false" isAbstract="false" icon="" baseClass="Class">
							<UML:Stereotype.extendedElement>
								<xsl:for-each select="Agent">
									<Foundation.Core.ModelElement xmi.idref="{../@name}_{@name}_class"/>
								</xsl:for-each>
							</UML:Stereotype.extendedElement>
						</UML:Stereotype>
						<UML:Stereotype xmi.id="Task_stereotype" name="Task" visibility="public" isSpecification="false" isRoot="false" isLeaf="false" isAbstract="false" icon="" baseClass="Class">
							<UML:Stereotype.extendedElement>
								<xsl:for-each select="Agent">
									<xsl:for-each select="Task">
										<Foundation.Core.ModelElement xmi.idref="{../@name}_{@name}_class"/>
									</xsl:for-each>
								</xsl:for-each>
							</UML:Stereotype.extendedElement>
						</UML:Stereotype>
						<!-- inserire DataType -->
						<xsl:for-each select="Agent">
							<xsl:for-each select="Attribute">
								<UML:DataType xmi.id="{@type}_datatype" name="{@type}" visibility="public" isSpecification="false" isRoot="false" isLeaf="false" isAbstract="false"/>
							</xsl:for-each>
							<xsl:for-each select="Method">
								<UML:DataType xmi.id="{@type}_datatype" name="{@type}" visibility="public" isSpecification="false" isRoot="false" isLeaf="false" isAbstract="false"/>
								<xsl:for-each select="Argoment">
									<UML:DataType xmi.id="{@type}_datatype" name="{@type}" visibility="public" isSpecification="false" isRoot="false" isLeaf="false" isAbstract="false"/>
								</xsl:for-each>
							</xsl:for-each>
							<xsl:for-each select="Task">
								<xsl:for-each select="Attribute">
									<UML:DataType xmi.id="{@type}_datatype" name="{@type}" visibility="public" isSpecification="false" isRoot="false" isLeaf="false" isAbstract="false"/>
								</xsl:for-each>
								<xsl:for-each select="Method">
									<UML:DataType xmi.id="{@type}_datatype" name="{@type}" visibility="public" isSpecification="false" isRoot="false" isLeaf="false" isAbstract="false"/>
									<xsl:for-each select="Argoment">
										<UML:DataType xmi.id="{@type}_datatype" name="{@type}" visibility="public" isSpecification="false" isRoot="false" isLeaf="false" isAbstract="false"/>
									</xsl:for-each>
								</xsl:for-each>
							</xsl:for-each>
						</xsl:for-each>
					</UML:Namespace.ownedElement>
				</UML:Model>
			</XMI.content>
		</XMI>
	</xsl:template>
	<xsl:template match="Agent">
		<UML:Package xmi.id="{@name}_package" name="{@name}" visibility="public" isSpecification="false" isRoot="false" isLeaf="false" isAbstract="false">
			<UML:ModelElement.namespace>
				<Foundation.Core.Namespace xmi.idref="{@name}_package"/>
				<!-- agenti_visione_engctrl -->
			</UML:ModelElement.namespace>
			<UML:Namespace.ownedElement>
				<UML:Class xmi.id="{../@name}_{@name}_class" name="{@name}" visibility="public" isSpecification="false" isRoot="false" isLeaf="true" isAbstract="false" isActive="false">
					<UML:ModelElement.namespace>
						<Foundation.Core.Namespace xmi.idref="{@name}_model"/>
					</UML:ModelElement.namespace>
					<UML:GeneralizableElement.generalization>
						<Foundation.Core.Generalization xmi.idref="{@name}_as_generalization"/>
					</UML:GeneralizableElement.generalization>
					<UML:Namespace.ownedElement>
						<UML:Generalization xmi.id="{@name}_as_generalization" name="" visibility="public" isSpecification="false" discriminator="">
							<UML:Generalization.child>
								<Foundation.Core.GeneralizableElement xmi.idref="{../@name}_{@name}_class"/>
							</UML:Generalization.child>
							<UML:Generalization.parent>
								<Foundation.Core.GeneralizableElement xmi.idref="AgentShell_class"/>
							</UML:Generalization.parent>
						</UML:Generalization>
					</UML:Namespace.ownedElement>
					<UML:Classifier.feature>
						<xsl:apply-templates select="Attribute"/>
						<xsl:apply-templates select="Method"/>
					</UML:Classifier.feature>
				</UML:Class>
				<xsl:apply-templates select="Task"/>
			</UML:Namespace.ownedElement>
		</UML:Package>
	</xsl:template>
	<xsl:template match="Task">
		<UML:Class xmi.id="{../@name}_{@name}_class" name="{@name}" visibility="public" isSpecification="false" isRoot="false" isLeaf="true" isAbstract="false" isActive="false">
			<UML:ModelElement.namespace>
				<Foundation.Core.Namespace xmi.idref="{../@name}_package"/>
			</UML:ModelElement.namespace>
			<UML:GeneralizableElement.generalization>
				<Foundation.Core.Generalization xmi.idref="{@name}_ts_generalization"/>
			</UML:GeneralizableElement.generalization>
			<UML:Namespace.ownedElement>
				<UML:Generalization xmi.id="{@name}_ts_generalization" name="" visibility="public" isSpecification="false" discriminator="">
					<UML:Generalization.child>
						<Foundation.Core.GeneralizableElement xmi.idref="{../@name}_{@name}_class"/>
					</UML:Generalization.child>
					<UML:Generalization.parent>
						<Foundation.Core.GeneralizableElement xmi.idref="TaskShell_class"/>
					</UML:Generalization.parent>
				</UML:Generalization>
			</UML:Namespace.ownedElement>
			<UML:Classifier.feature>
				<xsl:apply-templates select="Attribute"/>
				<xsl:apply-templates select="Method"/>
			</UML:Classifier.feature>
		</UML:Class>
	</xsl:template>
	<xsl:template match="Attribute">
		<UML:Attribute xmi.id="{@name}_attribute" name="{@name}" visibility="private" isSpecification="false" ownerScope="instance" changeability="changeable" targetScope="instance">
			<UML:StructuralFeature.multiplicity>
				<UML:Multiplicity>
					<UML:Multiplicity.range>
						<UML:MultiplicityRange lower="1" upper="1"/>
					</UML:Multiplicity.range>
				</UML:Multiplicity>
			</UML:StructuralFeature.multiplicity>
			<UML:Attribute.initialValue>
				<UML:Expression language="Java" body="{Value}"/>
			</UML:Attribute.initialValue>
			<UML:StructuralFeature.type>
				<Foundation.Core.Classifier xmi.idref="{@type}_datatype"/>
				<!-- String -->
			</UML:StructuralFeature.type>
		</UML:Attribute>
	</xsl:template>
	<xsl:template match="Method">
		<UML:Operation xmi.id="{@name}_{../@name}_{../../@name}_method" name="{@name}" visibility="public" isSpecification="false" ownerScope="instance" isQuery="false" concurrency="sequential" isRoot="false" isLeaf="false" isAbstract="false" specification="">
			<UML:BehavioralFeature.parameter>
			
			<UML:Operation.method>
	<Foundation.Core.Method xmi.idref="{@name}_{../@name}_{../../@name}_action"/>
	<!-- step_2_modified_by_rr::SequencialShareResource::Listener::Listener -->
</UML:Operation.method>
			
				<UML:Parameter xmi.id="{@name}_return" name="{@name}.Return" visibility="public" isSpecification="false" kind="return">
					<UML:Parameter.type>
						<Foundation.Core.Classifier xmi.idref="{@type}_datatype"/>
					</UML:Parameter.type>
				</UML:Parameter>
				<xsl:for-each select="Argoment">
					<UML:Parameter xmi.id="{../@name}_{@name}_argoment" name="{@name}" visibility="public" isSpecification="false" kind="inout">
						<UML:Parameter.type>
							<Foundation.Core.Classifier xmi.idref="{@type}_datatype"/>
						</UML:Parameter.type>
					</UML:Parameter>
				</xsl:for-each>
			</UML:BehavioralFeature.parameter>
		</UML:Operation>
		
<UML:Method xmi.id="{@name}_{../@name}_{../../@name}_action" name="{@name}" visibility="public" isSpecification="false" ownerScope="instance" isQuery="false">
	<UML:Method.body>
		<UML:ProcedureExpression language="{/Model/@platform}" body="{Code/text()}"/>
	</UML:Method.body>
	<UML:Method.specification>
		<Foundation.Core.Operation xmi.idref="{@name}_{../@name}_{../../@name}_method"/>
		<!-- step_2_modified_by_rr::SequencialShareResource::Listener::Listener -->
	</UML:Method.specification>
</UML:Method>		
		
	</xsl:template>
</xsl:stylesheet>

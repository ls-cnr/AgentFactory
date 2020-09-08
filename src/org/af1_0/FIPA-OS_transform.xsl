<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
    <xsl:output method="xml" indent="yes" encoding="UTF-16"/>

    <!-- ROOT ELEMENT -->
    <xsl:template match="Pattern">
        <Pattern>
            <xsl:apply-templates select="Agent"></xsl:apply-templates>
            <xsl:apply-templates select="Task"></xsl:apply-templates>
            <xsl:apply-templates select="PlatformConstraint"></xsl:apply-templates>
        </Pattern>
    </xsl:template>

    <!-- RELATION SPECIFIC TRANSFORMATION -->
    <xsl:template match="Relation">
        <xsl:copy-of select="." />
    </xsl:template>

    <!-- CONSTRAINT SPECIFIC TRANSFORMATION -->
    <xsl:template match="PlatformConstraint">
        <xsl:if test="@platform='FIPAOS' or @platform='ALL'">
            <xsl:if test="@target='model'">
                <Constraint target="{@target}">
                    <xsl:apply-templates select="Agent"></xsl:apply-templates>            
                    <xsl:apply-templates select="Relation"></xsl:apply-templates>
                </Constraint>
            </xsl:if>
            <xsl:if test="@target!='model'">
                <Constraint target="{@target}">
                    <xsl:apply-templates select="Modifier"></xsl:apply-templates>
                    <xsl:apply-templates select="Attribute"></xsl:apply-templates>
                    <xsl:apply-templates select="AgentId"></xsl:apply-templates>
                    <xsl:apply-templates select="AgentConstructor"></xsl:apply-templates>
                    <xsl:apply-templates select="AgentSetup"></xsl:apply-templates>
                    <xsl:apply-templates select="TaskConstructor"></xsl:apply-templates>
                    <xsl:apply-templates select="TaskSetup"></xsl:apply-templates>
                    <xsl:apply-templates select="Method"></xsl:apply-templates>
                    <xsl:apply-templates select="Task"></xsl:apply-templates>            
                </Constraint>
            </xsl:if>
        </xsl:if>
    </xsl:template>

    <!-- AGENT SPECIFIC TRANSFORMATION -->
    <xsl:template match="Agent">
            <Agent name="{@name}" extends="{@extends}">
                    <xsl:apply-templates select="Visibility"></xsl:apply-templates>
                    <xsl:apply-templates select="Modifier"></xsl:apply-templates>
                    <xsl:apply-templates select="ExtendsAgentShell"></xsl:apply-templates>
                    <xsl:apply-templates select="Attribute"></xsl:apply-templates>
                    <xsl:apply-templates select="AgentId"></xsl:apply-templates>
                    <xsl:apply-templates select="AgentConstructor"></xsl:apply-templates>
                    <xsl:apply-templates select="AgentSetup"></xsl:apply-templates>
                    <xsl:apply-templates select="Method"></xsl:apply-templates>
                    <xsl:apply-templates select="Task"></xsl:apply-templates>
            </Agent>
    </xsl:template>

    <xsl:template match="ExtendsAgentShell">
            <Extends>FIPAOSAgent</Extends>
    </xsl:template>
    <xsl:template match="AgentConstructor">
        <Method name="{parent::*/@name}" type="">
            <Visibility>public</Visibility>
            <Argoment type="String" name="platform"></Argoment>
            <Argoment type="String" name="agent_name"></Argoment>
            <Argoment type="String" name="profile"></Argoment>
            <xsl:copy-of select="Code" />
        </Method>
    </xsl:template>
    <xsl:template match="AgentSetup">
        <Method name="setup" type="void">
            <Visibility>private</Visibility>
            <xsl:copy-of select="Code" />
        </Method>
    </xsl:template>


    <!-- TASK SPECIFIC TRANSFORMATION -->
    <xsl:template match="Task">
            <Task name="{@name}" extends="{@extends}">
                    <xsl:apply-templates select="Visibility"></xsl:apply-templates>
                    <xsl:apply-templates select="Modifier"></xsl:apply-templates>
                    <xsl:apply-templates select="ExtendsTaskShell"></xsl:apply-templates>
                    <xsl:apply-templates select="Attribute"></xsl:apply-templates>
                    <xsl:apply-templates select="AgentId"></xsl:apply-templates>
                    <xsl:apply-templates select="TaskConstructor"></xsl:apply-templates>
                    <xsl:apply-templates select="TaskSetup"></xsl:apply-templates>
                    <xsl:apply-templates select="Method"></xsl:apply-templates>
            </Task>
    </xsl:template>	

    <xsl:template match="ExtendsTaskShell">
            <Extends>Task</Extends>
    </xsl:template>
    <xsl:template match="TaskConstructor">
        <Method name="{parent::*/@name}" type="">
            <Visibility>public</Visibility>
            <xsl:copy-of select="Code" />
        </Method>
    </xsl:template>
    <xsl:template match="TaskSetup">
        <Method name="startTask" type="void">
            <Visibility>public</Visibility>
            <xsl:copy-of select="Code" />
        </Method>
    </xsl:template>

    <!-- ATTRIBUTE SPECIFIC TRANSFORMATION -->
    <xsl:template match="Attribute">
            <xsl:copy-of select="." />
    </xsl:template>
    <xsl:template match="AgentId">
        <Attribute type="AgentID" name="{@name}">
            <xsl:copy-of select="Visibility" />
            <xsl:copy-of select="Modifier" />
            <xsl:copy-of select="Value" />
        </Attribute>
    </xsl:template>
    
    <!-- METHOD SPECIFIC TRANSFORMATION -->
    <xsl:template match="Constructor">
            <xsl:copy-of select="." />
    </xsl:template>
    <xsl:template match="Method">
        <Method name="{@name}" type="{@type}">
            <xsl:copy-of select="Visibility" />
            <xsl:copy-of select="Modifier" />
            <xsl:apply-templates select="HandleArgoment"></xsl:apply-templates>
            <xsl:apply-templates select="AgentIdArgoment"></xsl:apply-templates>
            <xsl:copy-of select="Argoment" />
            <xsl:copy-of select="Code" />
            <xsl:copy-of select="Throws" />
        </Method>
    </xsl:template>
    <xsl:template match="HandleArgoment">
        <Argoment type="Conversation" name="{@name}"/>
    </xsl:template>
    <xsl:template match="AgentIdArgoment">
        <Argoment type="AgentID" name="{@name}"/>
    </xsl:template>

    <!-- GENERIC -->
    <xsl:template match="Modifier">
        <xsl:copy-of select="." />
    </xsl:template>
    <xsl:template match="Visibility">
            <xsl:copy-of select="." />
    </xsl:template>
    
</xsl:stylesheet>

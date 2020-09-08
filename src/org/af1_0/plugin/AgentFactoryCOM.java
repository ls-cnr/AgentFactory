/*
 * AgentFactoryCOM.java
 *
 * Created on 23 dicembre 2002, 9.56
 */

package org.af1_0.plugin;

import java.util.*;

import org.af1_0.gui.xml.*;
import org.af1_0.entity.*;
import org.af1_0.mas.*;
import org.af1_0.pattern.*;

/** Interfaccia tra Visual Basic e Java
 * @author Luca
 * @version 2.0
 */
public class AgentFactoryCOM {
    private String platform;
    private org.af1_0.mas.multi_agent_system mas;
    private Vector external_agent_list;
    
    /** Creates a new instance of AgentFactoryCOM */
    public AgentFactoryCOM() {
        platform = "Jade";
        mas = new org.af1_0.mas.multi_agent_system(platform);
        external_agent_list = new Vector();
    }
    
    /** Change the agent platform reference.
     * WARNING: this method delete al the entity of the current system.
     * @param name Name of platform. Currently supported: "Jade" and "FIPAOS"
     */    
    public void setPlatform(String name) {
        platform = name;
        mas = new org.af1_0.mas.multi_agent_system(platform);
        external_agent_list = new Vector();
    }
    
    /** Restituisce il nome della piattaforma correntemente impostata.
     * @return string che descrive la piattaforma correntemente impostata.
     */    
    public String getPlatform() {
        return platform;
    }
    
    public void addExternalAgent(String name) {
        external_agent_list.add( new external_agent(name) );
    }
     
    public void addExternalAgent(external_agent external) {
        external_agent_list.add( external );
    }
   
    public external_agent getExternalAgent(String name) {
	Iterator it = external_agent_list.iterator();
	while (it.hasNext()) {
		external_agent external = (external_agent) it.next();
		if (external.getName().equals(name))
			return external;
	}
	
	return null;
    }
    
    public Iterator getExternalAgents() {
        return external_agent_list.iterator();
    }
    
    public int getMasAgentNumber() {
        int count = 0;
        Iterator it = mas.getStatic().values().iterator();
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isAgent()) 
                count++;
        }
        return count;
    }

    public java_class getMasAgent(int num) {
        java_class agent = new java_class();
        int count = 0;
        
        Iterator it = mas.getStatic().values().iterator();
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isAgent()) {
                if (count == num)
                    agent = (java_class) ent;
                else
                    count++;
            }
        }
            
        return agent;
    }
    
    public void addAgent(java_class agent) {
        entity_merging merging = new entity_merging(mas);
        merging.addAgent(agent);
    }
    
    public boolean SelectComponentPattern() {
        WizardDialogCOM wizard = new WizardDialogCOM(null, true);
        
        component_wizard_com comando = new component_wizard_com(wizard,mas,external_agent_list);
        comando.execute();
        
        wizard.show();
        
        return wizard.patternHasApplied();
    }

    public boolean SelectBehaviourPattern() {
        WizardDialogCOM wizard = new WizardDialogCOM(new javax.swing.JFrame(), true);
        
        behaviour_wizard_com comando = new behaviour_wizard_com(wizard,mas,external_agent_list);
        comando.execute();
        
        wizard.show();
        
        return wizard.patternHasApplied();
    }

    public String GenerateCode(java_class agent) {
        agent_to_java transf = new agent_to_java(agent,mas);
        return transf.getJava();
    }
    
    public static void main(String args[]) {
        AgentFactoryCOM afc = new AgentFactoryCOM();
        afc.setPlatform("Jade");
        afc.addExternalAgent("agente1");
        afc.addExternalAgent("agente2");
        external_agent ext = afc.getExternalAgent("agente1");
        ext.addTask("task1");
        ext.addTask("task2");
        
        ext = afc.getExternalAgent("agente2");
        ext.addTask("task3");
        ext.addTask("task4");
        
        afc.SelectComponentPattern();
        java_class agent = afc.getMasAgent(0);
        String code = afc.GenerateCode(agent);
        System.out.println(code);
    }
}


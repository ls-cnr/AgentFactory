/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.gui.command.behaviour_wizard;

import java.util.Vector;
import java.util.Iterator;

import org.af1_0.gui.command.model.*;
import org.af1_0.gui.command.*;
import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.pattern.*;
import org.af1_0.mas.*;

import org.af1_0.gui.xml.*;

/**
 *
 * @author  Luca
 */
public class behaviour_wizard_sheet4 implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected TreeModelUpdate tree_model;
    protected pattern the_pattern;
    
    /** Creates a new instance of component_wizard_start */
    public behaviour_wizard_sheet4(controls_panel the_controls, multi_agent_system current, TreeModelUpdate model, pattern a_pattern) {
        current_model = current;
        controls = the_controls;
        tree_model = model;
        the_pattern = a_pattern;
    }
    
    public void execute() {
        // 1.0 rinomina l'agente container del task in base all'agente selezionato
        String agent_name = controls.getWizardPanel().getAgentTargetPanel().getAgentName();
        if (agent_name == null)   // non è stato selezionato nessun agente
            return;
        
        java_class agent = searchAgent(the_pattern);
        entity_renaming renaming = new entity_renaming(the_pattern);
        renaming.renameAgent(agent,agent_name);
        
        /*agent_to_java tran = new agent_to_java(agent,current_model);
        System.out.println("[pattern]");
        System.out.println( tran.getJava() );*/
        
        // 2.0 preleva tutti i task dell'agente selezionato e li mostra nella Entity Selection
        Vector task_list = new Vector();
        java_class model_agent = searchAgent(current_model,agent_name);
        
        Iterator it = model_agent.getInnerClassList();
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isTask()) {
                java_class task = (java_class) ent;
                task_list.add(task.getName());
            }
        }
        
        controls.getWizardPanel().getSelectionPanel().setEntityList(task_list);
        
        
        pattern_description patt = controls.getWizardPanel().getPatternListPanel().getPatternSelected();
        controls.getWizardPanel().getSelectionPanel().setAgentName(patt.getName());

        // 2.0 mostra la scheda ENTITY SELECTION
        controls.getWizardPanel().showCard("target");
        
        // 3.0 imposta i pulsanti
        controls.getWizardPanel().setCancel( new cancel(controls) );
        controls.getWizardPanel().setPrevious( new behaviour_wizard_sheet2(controls,current_model,tree_model) );
        controls.getWizardPanel().setNext( null );
        controls.getWizardPanel().setOk( new behaviour_apply(controls,current_model,tree_model,the_pattern) ); //new component_apply(controls,current_model,tree_model,the_pattern)
    }
    
    private java_class searchAgent(pattern a_pattern) {
        java_class agente = null;
        
        Iterator it = a_pattern.getStatic().values().iterator();
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isAgent())
                agente = (java_class) ent;
        }
        
        return agente;
    }
    
    private java_class searchAgent(model a_model,String a_name) {
        java_class agente = null;
        
        Iterator it = a_model.getStatic().values().iterator();
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isAgent()) {
                java_class temp = (java_class) ent;
                if (temp.getName().equals(a_name))
                    agente = temp;
            }
        }
        
        return agente;
    }
}

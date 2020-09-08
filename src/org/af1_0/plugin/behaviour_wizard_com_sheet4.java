/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.plugin;

import java.util.Vector;
import java.util.Iterator;

import org.af1_0.gui.command.*;
import org.af1_0.gui.*;
import org.af1_0.gui.wizard.wizard_control;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.mas.*;
import org.af1_0.pattern.*;

import org.af1_0.gui.xml.*;

/**
 *
 * @author  Luca
 */
public class behaviour_wizard_com_sheet4 implements command {
    protected WizardDialogCOM wizard_dialog;
    protected multi_agent_system current_model;
    protected pattern the_pattern;
    protected Vector lista_agenti_esterni;
    
    /** Creates a new instance of component_wizard_start */
    public behaviour_wizard_com_sheet4(WizardDialogCOM dialog, multi_agent_system project, Vector agenti_esterni,pattern a_pattern) {
        wizard_dialog = dialog;
        current_model = project;
        the_pattern = a_pattern;
        lista_agenti_esterni = agenti_esterni;
    }
    
    public void execute() {
        wizard_control wizard_panel = wizard_dialog.getWizardPanel();
        
        // 1.0 rinomina l'agente container del task in base all'agente selezionato
        java_class agent = searchAgent(the_pattern);
        String agent_name = wizard_panel.getAgentTargetPanel().getAgentName();
        if (agent_name == null)   // non è stato selezionato nessun agente
            return;
        
        entity_renaming renaming = new entity_renaming(the_pattern);
        renaming.renameAgent(agent,agent_name);
        
        // 2.0 preleva tutti i task dell'agente selezionato e li mostra nella Entity Selection
        Vector task_list = new Vector();
        external_agent model_agent = searchAgent(lista_agenti_esterni,agent_name);
        
        Iterator it = model_agent.taskList();
        while (it.hasNext()) {
            String task_name = (String) it.next();
            task_list.add(task_name);
        }
        
        wizard_panel.getSelectionPanel().setEntityList(task_list);
        
        
        pattern_description patt = wizard_panel.getPatternListPanel().getPatternSelected();
        wizard_panel.getSelectionPanel().setAgentName(patt.getName());

        // 2.0 mostra la scheda ENTITY SELECTION
        wizard_panel.showCard("target");
        
        // 3.0 imposta i pulsanti
        wizard_panel.setCancel( new cancel_com(wizard_dialog) );
        wizard_panel.setPrevious( new behaviour_wizard_com_sheet3(wizard_dialog,current_model,lista_agenti_esterni,the_pattern) );
        wizard_panel.setNext( null );
        wizard_panel.setOk( new behaviour_com_apply(wizard_dialog,current_model,the_pattern) ); //new component_apply(controls,current_model,tree_model,the_pattern)
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
    
    private external_agent searchAgent(Vector a_list,String a_name) {
        external_agent agente = null;
        
        Iterator it = a_list.iterator();
        while (it.hasNext()) {
            external_agent ent = (external_agent) it.next();
            if (ent.getName().equals(a_name))
                agente = ent;
            
        }
        
        return agente;
    }
}

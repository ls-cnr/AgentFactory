/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.plugin;

import java.util.Vector;
import java.util.Iterator;

import org.af1_0.gui.command.*;
import org.af1_0.gui.command.panels.*;

import org.af1_0.gui.*;
import org.af1_0.gui.wizard.wizard_control;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.pattern.*;
import org.af1_0.mas.*;

/**
 *
 * @author  Luca
 */
public class component_com_apply implements command {
    protected WizardDialogCOM wizard_dialog;
    protected multi_agent_system current_model;
    protected pattern the_pattern;
    
    /** Creates a new instance of component_wizard_start */
    public component_com_apply(WizardDialogCOM dialog, multi_agent_system project, pattern a_pattern) {
        wizard_dialog = dialog;
        current_model = project;
        the_pattern = a_pattern;
    }
    
    public void execute() {
        wizard_control wizard_panel = wizard_dialog.getWizardPanel();
        
        String agent_name = wizard_panel.getSelectionPanel().getEntityName();
        if ( agent_name == null)   // non è stato selezionato nessun task
            return;

        // 1.0 imposta il nome corrente dell'agente
        java_class agent = searchAgent(the_pattern);
        
        if (!agent.getName().equals(agent_name)) {
            entity_renaming renaming = new entity_renaming(the_pattern);
            renaming.renameAgent(agent,agent_name);
        }
        
        current_model.apply_pattern(the_pattern,false);
        
        wizard_dialog.terminate(true);
    }
    
    private java_class searchAgent(pattern a_pattern) {
        java_class agente = null;
        
        Iterator it = a_pattern.getStatic().values().iterator();
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isAgent()) {
                agente = (java_class) ent;
                //System.out.println(agente.getName());
            }
        }
        
        return agente;
    }
    
}

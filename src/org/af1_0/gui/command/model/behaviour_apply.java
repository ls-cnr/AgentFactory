/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.gui.command.model;

import java.util.Vector;
import java.util.Iterator;

import org.af1_0.gui.command.*;
import org.af1_0.gui.command.panels.*;

import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.pattern.*;
import org.af1_0.mas.*;

/**
 *
 * @author  Luca
 */
public class behaviour_apply implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected TreeModelUpdate tree_model;
    protected pattern the_pattern;
    
    /** Creates a new instance of component_wizard_start */
    public behaviour_apply(controls_panel the_controls, multi_agent_system current, TreeModelUpdate model, pattern a_pattern) {
        current_model = current;
        controls = the_controls;
        tree_model = model;
        the_pattern = a_pattern;
    }
    
    public void execute() {
        // 1.0 imposta il nome corrente dell'agente
        String task_name = controls.getWizardPanel().getSelectionPanel().getEntityName();
        if (task_name == null)   // non è stato selezionato nessun task
            return;
        
        pattern_description patt = controls.getWizardPanel().getPatternListPanel().getPatternSelected();

        java_class agent = searchAgent(the_pattern);
        
        // 2.0 trova il task e fa il rename
        if (!patt.getName().equals(task_name)) {
            Iterator it = agent.getInnerClassList();
            while (it.hasNext()) {
                entity ent = (entity) it.next();
                if (ent.isTask()) {
                    java_class task = (java_class) ent;

                    if (task.getName().equals(patt.getName()) ) {
                        entity_renaming renaming = new entity_renaming(the_pattern);
                        renaming.renameTask(task,task_name);
                    }
                }
            }
        }

        tree_model.insert_pattern(the_pattern);
        current_model.apply_pattern(the_pattern,false);

        // 3. aggiorna la vista ad albero
        agent.notifyChange();
        controls.updateAgentShow();
        
        new cancel(controls).execute();
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

/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.gui.command.component_wizard;

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

/**
 *
 * @author  Luca
 */
public class component_wizard_sheet3 implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected TreeModelUpdate tree_model;
    protected pattern the_pattern;
    
    /** Creates a new instance of component_wizard_start */
    public component_wizard_sheet3(controls_panel the_controls, multi_agent_system current, TreeModelUpdate model, pattern a_pattern) {
        current_model = current;
        controls = the_controls;
        tree_model = model;
        the_pattern = a_pattern;
    }
    
    public void execute() {
        // 1.0 imposta il nome corrente dell'agente
        java_class agent = searchAgent(the_pattern);
        controls.getWizardPanel().getSelectionPanel().setAgentName( agent.getName() );
        
        // 2.0 mostra la scheda ENTITY SELECTION
        controls.getWizardPanel().showCard("target");
        
        // 3.0 imposta i pulsanti
        controls.getWizardPanel().setCancel( new cancel(controls) );
        controls.getWizardPanel().setPrevious( new component_wizard_sheet2(controls,current_model,tree_model) );
        controls.getWizardPanel().setNext( null );
        controls.getWizardPanel().setOk( new component_apply(controls,current_model,tree_model,the_pattern) );
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
    
}

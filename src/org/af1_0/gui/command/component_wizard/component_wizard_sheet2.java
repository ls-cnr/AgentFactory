/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.gui.command.component_wizard;

import java.util.Vector;
import java.util.Iterator;
import java.util.LinkedList;

import org.af1_0.gui.command.*;
import org.af1_0.gui.command.model.*;
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
public class component_wizard_sheet2 implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected TreeModelUpdate tree_model;
    
    /** Creates a new instance of component_wizard_start */
    public component_wizard_sheet2(controls_panel the_controls, multi_agent_system current, TreeModelUpdate model) {
        current_model = current;
        controls = the_controls;
        tree_model = model;
    }
    
    public void execute() {
        // 1.0 preleva il nome del pattern selezionato dalla scheda precedente
        pattern_description patt = controls.getWizardPanel().getPatternListPanel().getPatternSelected();
        if (patt == null)   // non è stato selezionato nessun pattern
            return;

        pattern_parser resolver = new pattern_parser(current_model);
        pattern the_pattern = resolver.getPattern( patt );
        
        java_class agent = searchAgent( the_pattern );
	    if (agent == null) {
		    System.out.println("pattern non valido");
		    return;
	    }

        // 2.0 imposta il nome corrente dell'agente
        controls.getWizardPanel().getSelectionPanel().setAgentName( agent.getName() );
        
        // 3.0 mostra la scheda ENTITY SELECTION
        controls.getWizardPanel().showCard("target");
        
        // 4.0 imposta i pulsanti
        controls.getWizardPanel().setCancel( new cancel(controls) );
        controls.getWizardPanel().setPrevious( new component_wizard_sheet1(controls,current_model,tree_model) );
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

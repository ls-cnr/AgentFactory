/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.gui.command.behaviour_wizard;

import java.util.Vector;
import java.util.Iterator;
import java.util.LinkedList;

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
public class behaviour_wizard_sheet2 implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected TreeModelUpdate tree_model;
    
    /** Creates a new instance of component_wizard_start */
    public behaviour_wizard_sheet2(controls_panel the_controls, multi_agent_system current, TreeModelUpdate model) {
        current_model = current;
        controls = the_controls;
        tree_model = model;
    }
    
    public void execute() {
        // 1.0 releva il pattern selezionato dalla scheda precedente
        pattern_description patt = controls.getWizardPanel().getPatternListPanel().getPatternSelected();
        if (patt == null)   // non è stato selezionato nessun pattern
            return;

        //pattern_resolver resolver = new pattern_resolver(current_model); 
        pattern_parser resolver = new pattern_parser(current_model); 
        pattern the_pattern = resolver.getPattern( patt );
        
        java_class agent = searchAgent( the_pattern );       
        //LinkedList rel_list = new LinkedList(the_pattern.getDynamic().values());   
        
        //controls.getWizardPanel().getPatternOverviewPanel().setRelations( rel_list );
        //controls.getWizardPanel().getPatternOverviewPanel().setAgent( agent );
        
        // 2.0 mostra la scheda OVERVIEW
/*        controls.getWizardPanel().showCard("overview");
        
        // 3.0 imposta i pulsanti
        controls.getWizardPanel().setCancel( new cancel(controls) );
        controls.getWizardPanel().setPrevious( new behaviour_wizard_sheet1( controls, current_model, tree_model) );
        controls.getWizardPanel().setNext( new behaviour_wizard_sheet3(controls,current_model,tree_model,the_pattern)  );
        controls.getWizardPanel().setOk( null );*/

        // 1.0 mostra la scheda AGENT SELECTION
        controls.getWizardPanel().showCard("agent");
        
        // 3.0 imposta i pulsanti
        controls.getWizardPanel().setCancel( new cancel(controls) );
        controls.getWizardPanel().setPrevious( new behaviour_wizard_sheet1( controls, current_model, tree_model) );
        controls.getWizardPanel().setNext( new behaviour_wizard_sheet4( controls, current_model, tree_model,the_pattern) );
        controls.getWizardPanel().setOk( null );
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

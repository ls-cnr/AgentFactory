/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.gui.command.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;

import org.af1_0.gui.command.*;
import org.af1_0.gui.command.panels.*;
import org.af1_0.gui.command.service_wizard.*;

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
public class service_apply implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected TreeModelUpdate tree_model;
    protected pattern the_pattern;
    protected String names[];
    
    /** Creates a new instance of component_wizard_start */
    public service_apply(controls_panel the_controls, multi_agent_system current, TreeModelUpdate model, pattern a_pattern,String array[]) {
        current_model = current;
        controls = the_controls;
        tree_model = model;
        the_pattern = a_pattern;
        names = array;
    }
    
    public void execute() {
		entity_search search = new entity_search(the_pattern);
		List role_list = search.getAgentList();

		String agent_name = controls.getWizardPanel().getRoleTargetPanel().getEntityName();
		//System.out.println("precedente digitazione: "+agent_name);
		if ( agent_name == null)   // non è stato selezionato nessun task
			return;

		names[names.length-1] = agent_name;
		
		for (int i=0; i<role_list.size(); i++) {
			java_class agent = (java_class) role_list.get(i);
			agent_name = names[i];

			if (!agent.getName().equals(agent_name)) {
				entity_renaming renaming = new entity_renaming(the_pattern);
				renaming.renameAgent(agent,agent_name);
			}
		}

		tree_model.insert_pattern(the_pattern);
		current_model.apply_pattern(the_pattern,false);

		// 3. aggiorna la vista ad albero
		for (int i=0; i<role_list.size(); i++) {
			java_class agent = (java_class) role_list.get(i);
			agent.notifyChange();
		}
		controls.updateAgentShow();

		new cancel(controls).execute();        
    }
        
}

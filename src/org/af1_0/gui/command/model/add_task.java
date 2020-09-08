/*
 * add_new_agent.java
 *
 * Created on 2 novembre 2002, 10.49
 */

package org.af1_0.gui.command.model;

import javax.swing.tree.MutableTreeNode;

import org.af1_0.gui.command.*;
import org.af1_0.gui.command.panels.*;

import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.mas.*;

/** Questa classe svolge il compito di aggiungere un nuovo agente al multi-agent system corrente
 * @author Luca
 */
public class add_task implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected java_class _class;
    protected TreeModelUpdate model_update;
    protected DynamicTreePanel tree_panel;
    
    /** Creates a new instance of add_new_agent */
    public add_task(controls_panel the_controls, multi_agent_system model, java_class the_class, DynamicTreePanel the_tree_panel, TreeModelUpdate tree_model_update) {
        current_model = model;
        controls = the_controls;
        _class = the_class;
        model_update = tree_model_update;
        tree_panel = the_tree_panel;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {     
        entity_factory factory = new entity_factory(current_model);
        
        java_class task=null;
	    String string_base = "a_task";
	    int count = 0;

	    while (task==null) {
		    String name = string_base + "_" + count;
		    task = factory.createTask(_class,name);
		    count++;
	    }

        MutableTreeNode parent_node = tree_panel.getSelectNode();
        model_update.insert_task(parent_node,task);       

        show_task_entity comm = new show_task_entity(controls,current_model,task);
        comm.execute();
    }
    
}

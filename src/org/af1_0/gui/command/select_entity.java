/*
 * add_new_agent.java
 *
 * Created on 2 novembre 2002, 10.49
 */

package org.af1_0.gui.command;

import java.util.*;
import javax.swing.tree.MutableTreeNode;

import org.af1_0.gui.command.model.*;
import org.af1_0.gui.command.panels.*;

import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.mas.*;

/** Questa classe svolge il compito di visualizzare il pannello relativo ad una entita' del sistema
 * corrispondente all'elemento selezionato dal JTree del progetto
 * @author Luca
 */
public class select_entity implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected DynamicTreePanel tree_panel;
    protected TreeModelUpdate model_update;
    
    /** Creates a new instance of show_entity */
    public select_entity(controls_panel the_controls, multi_agent_system current, DynamicTreePanel the_tree_panel, TreeModelUpdate tree_model_update) {
        current_model = current;
        controls = the_controls;
        tree_panel = the_tree_panel;
        model_update = tree_model_update;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {
        // 1. si preleva l'entita'
        MutableTreeNode select_node = tree_panel.getSelectNode();
        TreeNode my_node = model_update.searchNode(select_node);
        if (my_node == null) {
            tree_panel.setNonePopMenu();
            return;
        }
        
        entity node = my_node.getNodeEntity();
        
        if (node.isAgent()) {
            java_class agent = (java_class) node;
            
            command edit = new show_agent_entity(controls,current_model,agent);
            command show = new show_mas(controls,current_model,agent);
            command task = new add_task(controls,current_model,agent,tree_panel,model_update);
            command attribute = new add_attribute(controls,current_model,agent,tree_panel,model_update);
            command method = new add_method(controls,current_model,agent,tree_panel,model_update);
            tree_panel.setAgentPopMenu(show,edit,task,method,attribute);
            
        } else if (node.isRelation()) {
            relation rel = (relation) node;
            
            command edit = new show_relation_entity(controls,current_model,rel);
            tree_panel.setRelationPopMenu(edit);
        } else if (node.isTask()) {
            java_class task = (java_class) node;
            
            command edit = new show_task_entity(controls,current_model,task);
            command attribute = new add_attribute(controls,current_model,task,tree_panel,model_update);
            command method = new add_method(controls,current_model,task,tree_panel,model_update);
            tree_panel.setTaskPopMenu(edit,method,attribute);
        } else if (node.isAttribute()) {
            java_attribute attribute = (java_attribute) node;
            
            command edit = new show_attribute_entity(controls,current_model,attribute);
            tree_panel.setAttributePopMenu(edit);
        } else if (node.isMethod()) {
            java_method method = (java_method) node;
            
            command edit = new show_method_entity(controls,current_model,method);
            tree_panel.setMethodPopMenu(edit);
        } else {
            tree_panel.setNonePopMenu();
        }
    }
    
    public void setOnEnd(command onend) {
    }
    
}

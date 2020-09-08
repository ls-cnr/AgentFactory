/*
 * add_new_agent.java
 *
 * Created on 2 novembre 2002, 10.49
 */

package org.af1_0.gui.command.model;

import java.util.*;

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
public class insert_relation implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected TreeModelUpdate tree_model;
    
    /** Creates a new instance of add_new_agent */
    public insert_relation(controls_panel the_controls, multi_agent_system current, TreeModelUpdate model) {
        current_model = current;
        controls = the_controls;
        tree_model = model;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {
        // 1. genera un nome univoco
        String rel_name = "relation";
        int count = 0;
        boolean fine = false;
        while (!fine) {
           if ( search(rel_name+"_"+count) == false)
               fine = true;
           else
               count++;
        }
        rel_name += "_"+count;
               
        // 2. aggiunge una nuova relazione (nulla) al modello
        entity_factory factory = new entity_factory(current_model);
        relation rel = factory.createRelation(rel_name,null,null);
        current_model.getDynamic().put(rel_name, rel);
        tree_model.insert_relation(rel);
        controls.updateAgentShow();
        
        // 3. determina una lista di metodi del modello
        LinkedList method_list = new LinkedList();
        Map mappa_statica = current_model.getStatic();
        Iterator it = mappa_statica.values().iterator();
        while (it.hasNext()) {
            static_entity entity = (static_entity) it.next();
            if (entity.isMethod())
                method_list.add( entity );
        }

        // 4. visualizza la vista del nome
        controls.getRelationPanel().setMethodList(method_list);
        controls.getRelationPanel().setSuggested(rel);
        ((java.awt.CardLayout) controls.getLayout()).show(controls,"relation_view");
        
        // 5. assegna a tale vista i commanda di ok e cancel
        controls.getRelationPanel().setOkCommand(new edit_relation(controls,current_model,rel));
        controls.getRelationPanel().setCancelCommand(new cancel(controls));
    }
    
    private boolean search(String name) {
        return current_model.getDynamic().containsKey(name);
    }
    
    public void setOnEnd(command onend) {
    }
    
}

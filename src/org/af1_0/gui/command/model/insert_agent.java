/*
 * add_new_agent.java
 *
 * Created on 2 novembre 2002, 10.49
 */

package org.af1_0.gui.command.model;

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
public class insert_agent implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected TreeModelUpdate tree_model;
    
    /** Creates a new instance of add_new_agent */
    public insert_agent(controls_panel the_controls, multi_agent_system current, TreeModelUpdate model) {
        current_model = current;
        controls = the_controls;
        tree_model = model;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {
        // 1. si genera un nome univoco
        String agent_name = "agent";
        int count = 0;
        boolean fine = false;
        while (!fine) {
           if ( search(agent_name+"_"+count) == false)
               fine = true;
           else
               count++;
        }
        agent_name += "_"+count;
               
        // 2. aggiunge un nuovo agente al modello
        entity_factory factory = new entity_factory(current_model);
        java_class agente = factory.createAgent(agent_name);
        current_model.getStatic().put(agent_name, agente);
        tree_model.insert_agent(agente);
        
        // 3. visualizza la vista del nome
        controls.getNamePanel().setSuggested(agente);
        ((java.awt.CardLayout) controls.getLayout()).show(controls,"name_view");
        
        // 4. assegna a tale vista i commanda di ok e cancel
        controls.getNamePanel().setOkCommand(new edit_agent(controls,current_model,agente));
        controls.getNamePanel().setCancelCommand(new cancel(controls));
    }
    
    private boolean search(String name) {
        return current_model.getStatic().containsKey(name);
    }
    
    public void setOnEnd(command onend) {
    }
    
}

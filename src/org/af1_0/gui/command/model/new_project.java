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
public class new_project implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected TreeModelUpdate tree_model;
    protected DynamicTreePanel tree_panel;
    
    /** Creates a new instance of add_new_agent */
    public new_project(controls_panel the_controls, multi_agent_system current, TreeModelUpdate model,DynamicTreePanel tree) {
        current_model = current;
        controls = the_controls;
        tree_model = model;
        tree_panel = tree;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {
        current_model.clearAll();
        
        String platform = controls.getOptionPanel().getProjectPanel().getPlatform();
        
        //System.out.println(platform);
            
        current_model.setAgentPlatform(platform);
        tree_panel.setSystemString("System: "+  platform);  
        new refresh_model(current_model,tree_model).execute();        
        controls.clear();
        
        new cancel(controls).execute();
    }

}

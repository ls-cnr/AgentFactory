/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.gui.command.options;

import java.util.Vector;
import java.util.Iterator;

import org.af1_0.gui.command.*;
import org.af1_0.gui.command.model.*;
import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.mas.*;
import org.af1_0.repository.*;

/**
 *
 * @author  Luca
 */
public class new_project_option implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected TreeModelUpdate tree_model_update;
    protected DynamicTreePanel tree_panel;
    
    /** Creates a new instance of component_wizard_start */
    public new_project_option(controls_panel the_controls, multi_agent_system current,TreeModelUpdate tree_model,DynamicTreePanel tree) {
        current_model = current;
        controls = the_controls;
        tree_model_update = tree_model;
        tree_panel = tree;
    }
    
    public void execute() {
        controls.getOptionPanel().setTitle("Create a new Multi-Agents System");
        
        controls.getOptionPanel().getProjectPanel().suggestPlatform(0);
        controls.getOptionPanel().showCard("project");
        
        controls.getOptionPanel().setOk( new new_project(controls,current_model,tree_model_update,tree_panel) );
        controls.getOptionPanel().setCancel( new cancel(controls) );

        ((java.awt.CardLayout) controls.getLayout()).show(controls,"options");
     }
    
}

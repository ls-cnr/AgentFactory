/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.gui.command.options;

import java.util.Vector;
import java.util.Iterator;

import org.af1_0.gui.command.*;
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
public class change_repository implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    
    /** Creates a new instance of component_wizard_start */
    public change_repository(controls_panel the_controls, multi_agent_system current) {
        current_model = current;
        controls = the_controls;
    }
    
    public void execute() {
        
        repository_designer rep = controls.getOptionPanel().getRepositoryPanel().getRepository();
        if (rep != null) 
            current_model.setRepository(rep);
        
        new cancel(controls).execute();
     }
    
}

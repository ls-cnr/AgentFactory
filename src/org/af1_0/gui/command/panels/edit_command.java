/*
 * edit_command.java
 *
 * Created on 5 novembre 2002, 15.54
 */

package org.af1_0.gui.command.panels;

import org.af1_0.gui.command.*;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.gui.panel.controls_panel;

/**
 *
 * @author  Luca
 */
public abstract class edit_command extends control_command implements command {
    protected multi_agent_system current_model;
    
    /** Creates a new instance of edit_command */
    public edit_command(controls_panel controls, multi_agent_system current) {
        super(controls);
        current_model = current;        
    }
    
    public void setOnEnd(command onend) {
    }
    
}

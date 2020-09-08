/*
 * control_command.java
 *
 * Created on 5 novembre 2002, 15.50
 */

package org.af1_0.gui.command.panels;

import org.af1_0.gui.command.*;
import org.af1_0.gui.panel.controls_panel;

/**
 *
 * @author  Luca
 */
abstract class control_command implements command {
    protected controls_panel controls;
    
    /** Creates a new instance of control_command */
    public control_command(controls_panel the_controls) {
        controls = the_controls;
    }
    
    public abstract void execute();
    
    public void setOnEnd(command onend) {
    }
    
}

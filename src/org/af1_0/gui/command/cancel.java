/*
 * add_new_agent.java
 *
 * Created on 2 novembre 2002, 10.49
 */

package org.af1_0.gui.command;

import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.mas.*;

/** Questa classe svolge il compito di aggiungere un nuovo agente al multi-agent system corrente
 * @author Luca
 */
public class cancel implements command {
    protected controls_panel controls;
    
    /** Creates a new instance of add_new_agent */
    public cancel(controls_panel the_controls) {
        controls = the_controls;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {     
        // 1. annulla la vista corrente
        
        ((java.awt.CardLayout) controls.getLayout()).show(controls,"mas_view");
    }
    
    public void setOnEnd(command onend) {
    }
    
}

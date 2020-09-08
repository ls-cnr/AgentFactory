/*
 * add_new_agent.java
 *
 * Created on 2 novembre 2002, 10.49
 */

package org.af1_0.gui.command.panels;

import java.util.*;

import org.af1_0.gui.command.*;
import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.mas.*;

/** Questa classe svolge il compito di visualizzare il pannello relativo ad una entita' del sistema
 * corrispondente all'elemento selezionato dal JTree del progetto
 * @author Luca
 */
public class show_agent_entity implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected java_class agent;
    
    /** Creates a new instance of show_entity */
    public show_agent_entity(controls_panel the_controls, multi_agent_system current, java_class the_agent) {
        current_model = current;
        controls = the_controls;
        agent = the_agent;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {          
        // 1. visualizza la vista corrispondente
        controls.getNamePanel().setSuggested(agent);
        ((java.awt.CardLayout) controls.getLayout()).show(controls,"name_view");

        // 2. assegna a tale vista i commanda di ok e cancel
        controls.getNamePanel().setOkCommand(new edit_agent(controls,current_model,agent));
        controls.getNamePanel().setCancelCommand(new cancel(controls));
    }
    
}

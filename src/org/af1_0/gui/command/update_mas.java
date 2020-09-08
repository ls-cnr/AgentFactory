/*
 * add_new_agent.java
 *
 * Created on 2 novembre 2002, 10.49
 */

package org.af1_0.gui.command;

import java.util.*;

import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.mas.*;

/** Questa classe svolge il compito di visualizzare il pannello relativo ad una entita' del sistema
 * corrispondente all'elemento selezionato dal JTree del progetto
 * @author Luca
 */
public class update_mas implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected java_class agent;
    
    /** Creates a new instance of show_entity */
    public update_mas(controls_panel the_controls, multi_agent_system current, java_class the_agent) {
        current_model = current;
        controls = the_controls;
        agent = the_agent;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {          
        // 1. imposta la vista delle classi
        controls.getClassPanel().setAgent(agent);
        
        // 2. imposta la lista delle activity
        LinkedList rel_list = new LinkedList(current_model.getDynamic().values());   
        controls.getActivityPanel().setRelations(rel_list);
        controls.getActivityPanel().setAgent(agent);

        controls.getCodePanel().setAgent(agent,current_model);
    }
    
}

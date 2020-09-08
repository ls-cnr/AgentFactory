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

/** Questa classe svolge il compito di visualizzare il pannello relativo ad una entita' del sistema
 * corrispondente all'elemento selezionato dal JTree del progetto
 * @author Luca
 */
public class show_attribute_entity implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected java_attribute attribute;
    
    /** Creates a new instance of show_entity */
    public show_attribute_entity(controls_panel the_controls, multi_agent_system current, java_attribute the_attribute) {
        current_model = current;
        controls = the_controls;
        attribute = the_attribute;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {          
        // 1. visualizza la vista corrispondente
        controls.getAttributePanel().setSuggested(attribute);
        ((java.awt.CardLayout) controls.getLayout()).show(controls,"attribute_view");

        // 2. assegna a tale vista i commanda di ok e cancel
        controls.getAttributePanel().setOkCommand(new edit_attribute(controls,current_model,attribute));
        controls.getAttributePanel().setCancelCommand(new cancel(controls));
    }
    
}

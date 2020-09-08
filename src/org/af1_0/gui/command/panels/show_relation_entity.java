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
public class show_relation_entity implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected relation rel;
    
    /** Creates a new instance of show_entity */
    public show_relation_entity(controls_panel the_controls, multi_agent_system current, relation the_rel) {
        current_model = current;
        controls = the_controls;
        rel = the_rel;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {          
        // 1. determina una lista di metodi del modello
        LinkedList method_list = new LinkedList();
        Map mappa_statica = current_model.getStatic();
        Iterator it = mappa_statica.values().iterator();
        while (it.hasNext()) {
            static_entity entity = (static_entity) it.next();
            if (entity.isMethod())
                method_list.add( entity );
        }

        // 2. visualizza la vista corrispondente
        controls.getRelationPanel().setMethodList(method_list);
        controls.getRelationPanel().setSuggested(rel);
        ((java.awt.CardLayout) controls.getLayout()).show(controls,"relation_view");

        // 3. assegna a tale vista i commanda di ok e cancel
        controls.getRelationPanel().setOkCommand(new edit_relation(controls,current_model,rel));
        controls.getRelationPanel().setCancelCommand(new cancel(controls));
    }
    
}

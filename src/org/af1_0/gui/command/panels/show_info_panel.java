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
public class show_info_panel implements command {
    protected controls_panel controls;
    
    /** Creates a new instance of show_entity */
    public show_info_panel(controls_panel the_controls) {
        controls = the_controls;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {          
        // 1. visualizza la vista corrispondente
        ((java.awt.CardLayout) controls.getLayout()).show(controls,"about");

        // 2. assegna a tale vista i commanda di ok e cancel
        //controls.getNamePanel().setOkCommand(new edit_task(controls,current_model,task));
        //controls.getNamePanel().setCancelCommand(new cancel(controls));
    }
    
}

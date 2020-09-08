/*
 * edit_method.java
 *
 * Created on 4 novembre 2002, 10.07
 */

package org.af1_0.gui.command.panels;

import java.util.Iterator;

import org.af1_0.gui.command.*;
import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;

/**
 *
 * @author  Luca
 */
public class edit_method extends edit_command implements command {
    protected java_method method;
    
    /** Creates a new instance of edit_method */
    public edit_method(controls_panel controls, multi_agent_system model, java_method the_method) {
        super(controls,model);
        method = the_method;
    }

    /** Esegue l'operazione
     */    
    public void execute() {     
        // 1. preleva l'attributo
        java_method new_method = controls.getMethodPanel().getMethod();

        // 2. opera il rename
        current_model.getStatic().remove(method.getIDString());
        method.setName( new_method.getName() );
        method.setType( new_method.getType() );
        method.setVisibility( new_method.getVisibility() );
        
        Iterator deleting = method.getModifierList();
        while(deleting.hasNext()) {
            deleting.next();
            deleting.remove();
        }      
        Iterator adding = new_method.getModifierList();
        while(adding.hasNext()) {
            method.addModifier( (String) adding.next() );
        }
        
        current_model.getStatic().put(method.getIDString(), method);
        
        // 3. aggiorna la vista ad albero
        method.notifyChange();
        controls.updateAgentShow();
        
        // 4. annulla la vista corrente
        ((java.awt.CardLayout) controls.getLayout()).show(controls,"mas_view");
    }
    
    public void setOnEnd(command onend) {
    }
    
}

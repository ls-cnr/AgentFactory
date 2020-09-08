/*
 * add_new_agent.java
 *
 * Created on 2 novembre 2002, 10.49
 */

package org.af1_0.gui.command.panels;

import java.util.Iterator;

import org.af1_0.gui.command.*;
import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;

/** Questa classe svolge il compito di aggiungere un nuovo agente al multi-agent system corrente
 * @author Luca
 */
public class edit_attribute extends edit_command implements command {
    protected java_attribute attribute;
    
    /** Creates a new instance of add_new_agent */
    public edit_attribute(controls_panel controls, multi_agent_system model, java_attribute the_attribute) {
        super(controls,model);
        attribute = the_attribute;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {     
        // 1. preleva l'attributo
        java_attribute new_attribute = controls.getAttributePanel().getAttribute();

        // 2. opera il rename
        current_model.getStatic().remove(attribute.getIDString());
        attribute.setName( new_attribute.getName() );
        attribute.setType( new_attribute.getType() );
        attribute.setStartValue( new_attribute.getStartValue() );
        attribute.setVisibility( new_attribute.getVisibility() );
        current_model.getStatic().put(attribute.getIDString(), attribute);
        
        Iterator deleting = attribute.getModifierList();
        while(deleting.hasNext()) {
            deleting.next();
            deleting.remove();
        }      
        Iterator adding = new_attribute.getModifierList();
        while(adding.hasNext()) {
            attribute.addModifier( (String) adding.next() );
        }
        
        // 3. aggiorna la vista ad albero
        attribute.notifyChange();
        controls.updateAgentShow();
        
        // 4. annulla la vista corrente
        ((java.awt.CardLayout) controls.getLayout()).show(controls,"mas_view");
    }
    
}

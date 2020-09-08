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
import org.af1_0.mas.*;

/** Questa classe svolge il compito di aggiungere un nuovo agente al multi-agent system corrente
 * @author Luca
 */
public class edit_agent extends edit_command implements command {
    protected java_class agent;
    
    /** Creates a new instance of add_new_agent */
    public edit_agent(controls_panel controls, multi_agent_system model, java_class the_agent) {
        super(controls,model);
        agent = the_agent;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {     
        // 1. preleva il nome dell'agente
        java_class new_agent = controls.getNamePanel().getClassObject();
        //String agent_name = controls.getNamePanel().getName();
        //String new_agent_name = controls.getNamePanel().getName();

        // 2. opera il rename dell'agente
        entity_renaming renaming = new entity_renaming(current_model);
        renaming.renameAgent(agent, new_agent.getName());
        agent.setExtends( new_agent.getExtends() );
        
        Iterator deleting = agent.getModifierList();
        while(deleting.hasNext()) {
            deleting.next();
            deleting.remove();
        }      
        Iterator adding = new_agent.getModifierList();
        while(adding.hasNext()) {
            agent.addModifier( (String) adding.next() );
        }

        //current_model.getStatic().remove(agent.getName());
        //agent.setName( new_agent_name );
        //current_model.getStatic().put(new_agent_name, agent);
        
        // 3. aggiorna la vista ad albero
        agent.notifyChange();
        controls.updateAgentShow();
        
        // 4. annulla la vista corrente
        ((java.awt.CardLayout) controls.getLayout()).show(controls,"mas_view");
    }
    
    public void setOnEnd(command onend) {
    }
    
}

/*
 * add_new_agent.java
 *
 * Created on 2 novembre 2002, 10.49
 */

package org.af1_0.gui.command.panels;

import org.af1_0.gui.command.*;
import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.mas.*;

/** Questa classe svolge il compito di aggiungere un nuovo agente al multi-agent system corrente
 * @author Luca
 */
public class edit_relation extends edit_command implements command {
    protected relation rel;
    
    /** Creates a new instance of add_new_agent */
    public edit_relation(controls_panel controls, multi_agent_system model, relation the_rel) {
        super(controls,model);
        rel = the_rel;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {     
        // 1. preleva il nome dell'agente
        relation new_rel = controls.getRelationPanel().getRelation();

        // 2. opera il rename
        current_model.getDynamic().remove(rel.getName());
        rel.setName( new_rel.getName() );
        rel.setStart( new_rel.getStart() );
        rel.setEnd( new_rel.getEnd() );
        current_model.getDynamic().put(new_rel.getName(), rel);
        
        // 3. aggiorna la vista ad albero
        rel.notifyChange();
        controls.updateAgentShow();
        
        // 4. annulla la vista corrente
        ((java.awt.CardLayout) controls.getLayout()).show(controls,"mas_view");
    }
    
    public void setOnEnd(command onend) {
    }
    
}

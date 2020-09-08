/*
 * add_new_agent.java
 *
 * Created on 2 novembre 2002, 10.49
 */

package org.af1_0.gui.command;

import java.util.*;
import javax.swing.tree.MutableTreeNode;

import org.af1_0.gui.command.model.*;
import org.af1_0.gui.command.panels.*;

import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.mas.*;

/** Questa classe svolge il compito di visualizzare il pannello relativo ad una entita' del sistema
 * corrispondente all'elemento selezionato dal JTree del progetto
 * @author Luca
 */
public class refresh_model implements command {
    protected multi_agent_system current_model;
    protected TreeModelUpdate model_update;
    
    /** Creates a new instance of show_entity */
    public refresh_model(multi_agent_system current, TreeModelUpdate tree_model_update) {
        current_model = current;
        model_update = tree_model_update;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {
        model_update.clearAll();
        Iterator it = current_model.getStatic().values().iterator();
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isAgent()) {
                java_class agent = (java_class) ent;
                model_update.insert_agent(agent);
            }
        }
        
        it = current_model.getDynamic().values().iterator();
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isRelation()) {
                relation rel = (relation) ent;
                model_update.insert_relation(rel);
            }
        }
    }
    
}

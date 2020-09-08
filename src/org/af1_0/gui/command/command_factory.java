/*
 * command_factory.java
 *
 * Created on 23 novembre 2002, 8.57
 */

package org.af1_0.gui.command;

import org.af1_0.gui.command.model.*;
import org.af1_0.gui.command.panels.*;

import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;

/**
 *
 * @author  Luca
 */
public class command_factory {
    public final static int AGENT = 0;
    public final static int TASK = 1;
    public final static int RELATION = 2;
    public final static int ATTRIBUTE = 3;
    public final static int METHOD = 4;
    
    
    protected multi_agent_system model;
    protected controls_panel controls;
    protected DynamicTreePanel tree_panel;
    protected TreeModelUpdate model_update;
    
    /** Creates a new instance of command_factory */
    public command_factory( multi_agent_system the_model,
                            controls_panel the_controls,
                            DynamicTreePanel the_tree_panel,
                            TreeModelUpdate the_model_update ) {
                            
        model = the_model;
        controls = the_controls;
        tree_panel = the_tree_panel;
        model_update = the_model_update;
    }
    
    /** restituisce un oggetto command che inserisce una entità nel sistema.
     * @param type può essere AGENT, TASK, RELATION, METHOD, ATTRIBUTE; in base a quale
     * entità si fornisce come parametro l'oggetto command inserisce una entità
     * di tipo diverso.
     * Per TASK, METHOD, ATTRIBUTE è necessario specificare anche un ulteriore
     * parametro che rappresenta la java_class "parent" nel quale l'entità sarà
     * creata.
     * @return restituisce un oggetto command da attivare con il meotodo execute().
     */    
    public command createInsertEntity(int type,java_class container) {
        switch (type) {
            case AGENT:
                return new insert_agent(controls,model,model_update);
            case TASK:
                return new add_task(controls,model,container,tree_panel,model_update);
            case RELATION:
                return new insert_relation(controls,model,model_update);
            case ATTRIBUTE:
                return new add_attribute(controls,model,container,tree_panel,model_update);
            case METHOD:
                return new add_method(controls,model,container,tree_panel,model_update);
        }
        return null;
    }
    
    /** restituisce un oggetto command che visualizza il pannello per la modifica di
     * una entità nel sistema.
     * @param type può essere AGENT, TASK, RELATION, METHOD, ATTRIBUTE; in base a quale
     * entità si fornisce come parametro l'oggetto command visualizza un pannello
     * di tipo diverso.
     * @return restituisce un oggetto command da attivare con il meotodo execute().
     */    
    public command createShowEditPanel(int type,entity obj) throws IncorrectEnityType {
        switch (type) {
            case AGENT:
                if (!obj.isAgent())
                    throw new IncorrectEnityType();
                return new show_agent_entity(controls,model,(java_class) obj);
            case TASK:
                if (!obj.isTask())
                    throw new IncorrectEnityType();
                return new show_task_entity(controls,model,(java_class) obj);
            case RELATION:
                if (!obj.isRelation())
                    throw new IncorrectEnityType();
                return new show_relation_entity(controls,model,(relation) obj);
            case ATTRIBUTE:
                if (!obj.isAttribute())
                    throw new IncorrectEnityType();
                return new show_attribute_entity(controls,model,(java_attribute) obj);
            case METHOD:
                if (!obj.isMethod())
                    throw new IncorrectEnityType();
                return new show_method_entity(controls,model,(java_method) obj);
        }
        return null;
    }

    /** restituisce un oggetto command che visualizza il pannello con le viste relative
     * al multi agent system.
     * @param agent rappresenta l'agente che deve essere visualizzato.
     * @return restituisce un oggetto command da attivare con il meotodo execute().
     */    
    public command createShowMasViews(java_class agent) {
        return new show_mas(controls,model,agent);
    }
    
    /** restituisce un oggetto command che interroga l'albero del progetto per
     * determinare quale entità è stata selezionata.
     * @return restituisce un oggetto command da attivare con il meotodo execute().
     */    
    public command createSelectEntity() {
        return new select_entity(controls,model,tree_panel,model_update);
    }
}

/*
 * entity_factory.java
 *
 * Created on 24 ottobre 2002, 16.29
 */

package org.af1_0.mas;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.af1_0.entity.*;

/**
 *
 * @author  Luca
 */
public class entity_search {
    model myModel;
    
    /** Creates a new instance of entity_factory */
    public entity_search(model a_model) {
        myModel = a_model;
    }
    
    public List getAgentList() {       
        LinkedList agent_list = new LinkedList();
        Iterator it = myModel.getStatic().values().iterator();
        while (it.hasNext()) {
            entity ent = (entity) it.next();
        
            if (ent != null && ent.isAgent())
                agent_list.add(ent);
        }
        
        return agent_list;
    }
        
    public java_class searchAgent(String name) {       
        entity ent = (entity) myModel.getStatic().get(name);
        
        if (ent != null && ent.isAgent())
            return (java_class) ent;
        
        return null;
    }
        
    public relation searchRelation(String label) {       
        entity ent = (entity) myModel.getDynamic().get(label);
        
        if (ent != null && ent.isRelation())
            return (relation) ent;
        
        return null;
    }

    public java_class searchFirstAgent() {
        java_class agente = null;
        
        Iterator it = myModel.getStatic().values().iterator();
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isAgent()) {
                agente = (java_class) ent;
            }
        }
        
        return agente;
    }

    public java_class searchTask(java_class container,String name) {
        String complete_name = container.getIDString()+"."+name;
        entity ent = (entity) myModel.getStatic().get(complete_name);
        
        if (ent != null && ent.isTask())
            return (java_class) ent;
        
        return null;
    }

    public java_attribute searchAttribute(java_class container,String name) {
        String complete_name = container.getIDString()+"."+name;
        entity ent = (entity) myModel.getStatic().get(complete_name);
        
        if (ent != null && ent.isAttribute())
            return (java_attribute) ent;
        
        return null;
    }
    
    public java_method searchMethod(java_class container,String name) {            
        String complete_name = container.getIDString()+"."+name;
        entity ent = (entity) myModel.getStatic().get(complete_name);
        
        if (ent != null && ent.isMethod())
            return (java_method) ent;
        
        return null;
    }
}

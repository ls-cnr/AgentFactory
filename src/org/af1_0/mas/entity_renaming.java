/*
 * entity_factory.java
 *
 * Created on 24 ottobre 2002, 16.29
 */

package org.af1_0.mas;

import java.util.Iterator;
import org.af1_0.entity.*;

/**
 *
 * @author  Luca
 */
public class entity_renaming {
    model myModel;
    
    /** Creates a new instance of entity_factory */
    public entity_renaming(model a_model) {
        myModel = a_model;
    }
    
    public void renameAgent(java_class agent, String name) {
        // rimuove tutti i sotto-item
        removeComponent(agent);
        // elimina l'entry dell'item dalla tabella
        myModel.getStatic().remove( agent.getName() );
        
        // cambia il nome dell'agente e dei suoi costruttori
        String old_name = agent.getName();
        agent.setName( name );
        Iterator it = agent.getMethodList();
        while( it.hasNext() ) {
            java_method meth = (java_method) it.next();
            if (meth.getName().equals(old_name))
                meth.setName(name);
        }
        
        // riinserisce l'entry nella tabella
        entity_merging merging = new entity_merging(myModel);
        merging.addAgent(agent);  
    }
        
    public void renameTask(java_class task, String name) {
        // rimuove tutti i sotto-item
        removeComponent(task);
        // elimina l'entry dell'item dalla tabella
        myModel.getStatic().remove(task.getIDString());
        
        // cambia il nome dell'agente e dei suoi costruttori
        String old_name = task.getName();
        task.setName( name );
        Iterator it = task.getMethodList();
        while( it.hasNext() ) {
            java_method meth = (java_method) it.next();
            if (meth.getName().equals(old_name))
                meth.setName(name);
        }

        // riinserisce l'entry nella tabella
        entity_merging merging = new entity_merging(myModel);
        merging.addTask(task,task.getIDString() );  
    }
    
    public void renameRelation(relation rel, String name) {
        // elimina l'entry dell'item dalla tabella
        myModel.getDynamic().remove(rel.getName());
        // cambia il nome
        rel.setName( name );
        // riinserisce l'entry nella tabella
        myModel.getDynamic().put(name, rel);  
    }
    
    public void renameAttribute(java_attribute attribute, String name) {
        // elimina l'entry dell'item dalla tabella
        myModel.getStatic().remove(attribute.getIDString());
        // cambia il nome
        attribute.setName( name );
        // riinserisce l'entry nella tabella
        myModel.getStatic().put(attribute.getIDString(), attribute);  
    }
    
    public void renameMethod(java_method method, String name) {
        // elimina l'entry dell'item dalla tabella
        myModel.getStatic().remove(method.getIDString());
        // cambia il nome
        method.setName( name );
        // riinserisce l'entry nella tabella
        myModel.getStatic().put(method.getIDString(), method);  
    }
    
    private void removeComponent(java_class the_class) {
        Iterator it;
        it = the_class.getAttributeList();
        while (it.hasNext()) {
            java_attribute attribute = (java_attribute) it.next();
            myModel.getStatic().remove( attribute.getIDString() );
        }
        it = the_class.getMethodList();
        while (it.hasNext()) {
            java_method method = (java_method) it.next();
            myModel.getStatic().remove(method.getIDString());
        }
        it = the_class.getInnerClassList();
        while (it.hasNext()) {
            java_class task = (java_class) it.next();
            myModel.getStatic().remove(task.getIDString());
            removeComponent(task);
        }
    }
}

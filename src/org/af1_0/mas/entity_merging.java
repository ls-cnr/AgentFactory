/*
 * entity_merging.java
 *
 * Created on 24 ottobre 2002, 17.10
 */

package org.af1_0.mas;

import java.util.Iterator;
import org.af1_0.entity.*;

/**
 *
 * @author  Luca
 */
public class entity_merging {
    model myModel;
    
    /** Creates a new instance of entity_merging */
    public entity_merging(model a_model) {
        myModel = a_model;
    }
    
    public void addModel(model other_model) {
        Iterator it = other_model.getStatic().values().iterator();
        
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isAgent()) {
                java_class agent = (java_class) ent;               
                addAgent(agent);               
            }
        }
        
        it = other_model.getDynamic().values().iterator();
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isRelation()) {
                relation rel = (relation) ent;               
                addRelation(rel);               
            }
        }
    }
    
    public void addAgent(java_class agent) {
        // se nel modello e' presente un agente con lo stesso nome
        // effettua il MERGE altrimenti effettua l'UNIONE
        if (myModel.getStatic().containsKey(agent.getName())) {
            java_class old_agent = (java_class) myModel.getStatic().get(agent.getName());
            merge_entity(old_agent,agent);
            
        } else {
            myModel.getStatic().put(agent.getName(),agent);
            add_inner_entity(agent);
        }
    }
    
    public void addRelation(relation rel) {
        myModel.getDynamic().put(rel.getName(),rel);
    }
    
    public void addTask(java_class task, String path) {
        // se nel modello e' presente un agente con lo stesso nome
        // effettua il MERGE altrimenti effettua l'UNIONE
        if (myModel.getStatic().containsKey(path)) {
            java_class old_task = (java_class) myModel.getStatic().get(path);
            merge_entity(old_task,task);
            
        } else {
            myModel.getStatic().put(path,task);
            add_inner_entity(task);
        }
    }
    
    private void add_inner_entity(java_class a_class) {
        Iterator iter;
        
        iter = a_class.getAttributeList();
        while (iter.hasNext()) {
            java_attribute attribute = (java_attribute) iter.next();
            attribute.setParent(a_class);
            myModel.getStatic().put(attribute.getIDString(),attribute);
            //myModel.getStatic().put(prefix+attribute.getName(),attribute);
        }
        
        iter = a_class.getMethodList();
        while (iter.hasNext()) {
            java_method method = (java_method) iter.next();
            method.setParent(a_class);
            myModel.getStatic().put(method.getIDString(),method);
            //myModel.getStatic().put(prefix+method.getName(),method);
        }
    
        iter = a_class.getInnerClassList();
        while (iter.hasNext()) {
            java_class sub_class = (java_class) iter.next();
            sub_class.setParent(a_class);
            addTask(sub_class,sub_class.getIDString());
            
            /*myModel.getStatic().put(prefix+sub_class.getName(),sub_class);
            add_inner_entity(sub_class,prefix+sub_class.getName()+".");*/
        }
    }

    private void merge_entity(java_class old_class,java_class new_class) {
        Iterator iter;
        
        // deve miscelare anche gli attributi
        // AGGIUNGERE
        
        iter = new_class.getAttributeList();
        while (iter.hasNext()) {
            java_attribute attribute = (java_attribute) iter.next();
            merge_attribute(old_class, attribute); 
        }
        
        iter = new_class.getMethodList();
        while (iter.hasNext()) {
            java_method method = (java_method) iter.next();
            merge_method(old_class, method); 
        }
    
        iter = new_class.getInnerClassList();
        while (iter.hasNext()) {
            java_class sub_class = (java_class) iter.next();
            merge_innerclass(old_class, sub_class); 
        }
    }
    
    public void merge_attribute(java_class old_class, java_attribute attribute) {
        Iterator iter;
        boolean exist = false;
        
        iter = old_class.getAttributeList();
        while (iter.hasNext()) {
            java_attribute att = (java_attribute) iter.next();
            if (att.getName().equals(attribute.getName()))
                exist = true;
        }
        
        if (!exist) {
            old_class.addAttribute(attribute);   
            myModel.getStatic().put(attribute.getIDString(),attribute);
        }
    }
    
    public void merge_method(java_class old_class, java_method method) {
        Iterator iter;
        boolean exist = false;
        
        iter = old_class.getMethodList();
        while (iter.hasNext()) {
            java_method meth = (java_method) iter.next();
            if (meth.getName().equals(method.getName()))
                exist = true;
        }
        
        if (!exist) {
            old_class.addMethod(method);   
            myModel.getStatic().put(method.getIDString(),method);
        }
    }

    public void merge_innerclass(java_class old_class, java_class subclass) {
        Iterator iter;
        boolean exist = false;
        java_class existing_class = null;
     
        iter = old_class.getInnerClassList();
        while (iter.hasNext()) {
            java_class cl = (java_class) iter.next();
            if (cl.getName().equals(subclass.getName())) {
                exist = true;
                existing_class = cl;
            }
        }
        
        if (!exist) {
            java_class a_class = new java_class();
            a_class.setName(subclass.getName());
            a_class.setStereotype(subclass.getStereotype());
            a_class.setParent(old_class);
            a_class.setExtends(subclass.getExtends());

            old_class.addInnerClass(a_class);
            myModel.getStatic().put(a_class.getIDString(),a_class);
            
            merge_entity(a_class,subclass);
        } else 
            merge_entity(existing_class,subclass);
    }
}

/*
 * entity_merging.java
 *
 * Created on 24 ottobre 2002, 17.10
 */

package org.af1_0.mas;

import java.util.Iterator;
import org.af1_0.entity.*;

/**
 * @author Luca
 * @deprecated Questa classe è obsoleta. Usare entity_merging.
 */
public class entity_merge {
    multi_agent_system myModel;
    boolean overwrite = false;
    
    protected entity_factory factory;
    protected entity_search search;
    protected entity_copy copy;
    
    /** Creates a new instance of entity_merging */
    public entity_merge(multi_agent_system a_model) {
        myModel = a_model;
        
        factory = new entity_factory(myModel,false);
        search = new entity_search(myModel);
        copy = new entity_copy(myModel);
    }
    
    public entity_merge(multi_agent_system a_model,boolean write) {
        myModel = a_model;
        overwrite = write;
        
        factory = new entity_factory(myModel,false);
        search = new entity_search(myModel);
        copy = new entity_copy(myModel);
    }
    
    public void mergeModel(model other_model) {
        /*System.out.println("[I want to merge this (original)]");
        System.out.println(myModel);
        System.out.println("[with this (new)]");
        System.out.println(other_model);*/
        
        Iterator it = other_model.getStatic().values().iterator();        
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isAgent()) {
                java_class agent = (java_class) ent;               
                mergeAgent(agent);               
            }
        }
        
        it = other_model.getDynamic().values().iterator();
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isRelation()) {
                relation rel = (relation) ent;               
                mergeRelation(rel);               
            }
        }
        
        //System.out.println("[result]");
        //System.out.println(myModel);
    }
    
    public java_class mergeAgent(java_class agente) {  
        java_class model_agent = search.searchAgent(agente.getName());
        
        if (model_agent == null) {
            model_agent = factory.createAgent(agente.getName());
            copy.copyClass(agente,model_agent);
        } else {
            if (overwrite)
                copy.copyClass(agente,model_agent);
        }
        
        mergeInnerEntity(model_agent,agente);
        
        return model_agent;
    }
    
    public relation mergeRelation(relation rel) {        
        relation model_rel = search.searchRelation(rel.getName());
        
        if (model_rel == null)       
            model_rel = factory.createRelation(rel.getName(),rel.getStart(),rel.getEnd());
        
        return model_rel;
    }

    public java_class mergeTask(java_class container,java_class task) {       
        java_class model_task = search.searchTask(container,task.getName());
        
        if (model_task == null) {
            model_task = factory.createTask(container,task.getName());
            copy.copyClass(task,model_task);
        } else {
            if (overwrite)
                copy.copyClass(task,model_task);
        }

        mergeInnerEntity(model_task,task);
        
        return model_task;
    }
    
    public java_attribute mergeAttribute(java_class container,java_attribute attribute) {       
        java_attribute model_attribute = search.searchAttribute(container,attribute.getName());
        if (model_attribute == null) {
            model_attribute = factory.createAttribute(container,attribute.getType(),attribute.getName());
            copy.copyAttribute(attribute,model_attribute);
        } else {
            if (overwrite)
                copy.copyAttribute(attribute,model_attribute);
        }

        return model_attribute;
    }

    public java_method mergeMethod(java_class container,java_method method) {       
        java_method model_method = search.searchMethod(container,method.getName());
        if (model_method == null) {
            model_method = factory.createMethod(container,method.getType(),method.getName());
            copy.copyMethod(method,model_method);
        } else {
            if (overwrite) {
                copy.copyMethod(method,model_method);
            }
        }
       
        return method;
    }

    private void mergeInnerEntity(java_class model_class,java_class a_class) {
        Iterator iter;
        
        iter = a_class.getAttributeList();
        while (iter.hasNext()) {
            java_attribute attribute = (java_attribute) iter.next();
            attribute.setParent(a_class);
            mergeAttribute(model_class,attribute);
        }
        
        iter = a_class.getMethodList();
        while (iter.hasNext()) {
            java_method method = (java_method) iter.next();
            method.setParent(a_class);
            mergeMethod(model_class,method);
        }
    
        iter = a_class.getInnerClassList();
        while (iter.hasNext()) {
            java_class sub_class = (java_class) iter.next();
            sub_class.setParent(a_class);
            mergeTask(model_class,sub_class);
        }
    }
}

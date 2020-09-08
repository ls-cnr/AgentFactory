/*
 * entity_factory.java
 *
 * Created on 24 ottobre 2002, 16.29
 */

package org.af1_0.mas;

import org.af1_0.entity.*;

/**
 * Questa classe <b>deve</b> essere usata per creare entità del sistema
 * @author  Luca
 */
public class entity_factory {
    multi_agent_system myModel;
    boolean overwrite = false;
    
    /**
     * Creates a new instance of entity_factory
     * @param a_model systema al quale aggingere nuove entità
     */
    public entity_factory(multi_agent_system a_model) {
        myModel = a_model;
    }

    /**
     * Creates a new instance of entity_factory
     * @param a_model systema al quale aggingere nuove entità
     * @param over determina come comportarsi nel caso in cui una entità con lo stesso nome esistà già nel sistema: true sovrascrive l'entità, false ignora l'operazione
     */
    public entity_factory(multi_agent_system a_model,boolean over) {
        myModel = a_model;
        overwrite = over;
    }

    /**
     * Crea un agente
     * @param name nome dell'agente
     * @return la classe dell'agente
     */
    public java_class createAgent(String name) {
        // controlla che non ci sia un duplicato nel modello
        entity_search search = new entity_search(myModel);
        java_class a_class = search.searchAgent(name);

        if (a_class != null) {
            if (overwrite==false)
                return null;
        } else 
            a_class = new java_class();
        
        a_class.setName(name);
        a_class.setStereotype("Agent");
        a_class.setExtends(myModel.getAgentShell());
        
        myModel.getStatic().put(name,a_class);
        
        return a_class;
    }

    /**
     * Crea una relazione tra due metodi del sistema
     * @param label label della relazione
     * @param meth1 riferimento al metodo che avvia la transizione
     * @param meth2 riferimento al metodo che riceve la transizione
     * @return l'entità dinamica creata
     */
    public relation createRelation(String label, java_method meth1, java_method meth2) {
        // controlla che non ci sia un duplicato nel modello
        entity_search search = new entity_search(myModel);
        relation rel = search.searchRelation(label);

        if (rel != null) {
            if (overwrite==false)
                return null;
        } else 
            rel = new relation();

        rel.setName(label);
        rel.setStart(meth1);
        rel.setEnd(meth2);
        myModel.getDynamic().put(label,rel);
        
        return rel;
    }

    /**
     * Crea un task ad un agente
     * @param agent agente al quale aggiungere il task
     * @param name nome del task da aggiungere
     * @return entità creata
     */
    public java_class createTask(java_class agent,String name) {
        entity_search search = new entity_search(myModel);
        java_class a_class = search.searchTask(agent,name);
        if (a_class != null) {
            if (overwrite==false)
                return null;
        } else 
            a_class = new java_class();

        a_class.setName(name);
        a_class.setStereotype("Task");
        a_class.setParent(agent);
        a_class.setExtends(myModel.getTaskShell());

        agent.addInnerClass(a_class);
        myModel.getStatic().put(agent.getName()+"."+name,a_class);
        
        return a_class;
    }

    /**
     * Crea un attributo ad un container (o un agente o un task)
     * @param a_class riferimento del container
     * @param type stringa che specifica il datatype dell'attributo
     * @param name stringa che specifica il nome dell'attributo
     * @return l'entità creata
     */
    public java_attribute createAttribute(java_class a_class,String type,String name) {
        entity_search search = new entity_search(myModel);
        java_attribute attribute = search.searchAttribute(a_class,name);
        if (attribute != null) {
            if (overwrite==false)
                return null;
        } else 
            attribute = new java_attribute();

        attribute.setName(name);
        attribute.setType(type);
        attribute.setParent(a_class);
        a_class.addAttribute(attribute);
        myModel.getStatic().put(attribute.getIDString(),attribute);
        
        return attribute;
    }

    /**
     * Crea un metodo ad un container (o un agente o un task)
     * @param a_class riferimento del container
     * @param ret_type stringa che definisce il tipo restituito dal metodo
     * @param name stringa che definisce il nome del metodo
     * @return entità creata
     */
    public java_method createMethod(java_class a_class,String ret_type,String name) {
        entity_search search = new entity_search(myModel);
        java_method method = search.searchMethod(a_class,name);
        if (method != null) {
            if (overwrite==false)
                return null;
        } else 
            method = new java_method();

        method.setName(name);
        method.setType(ret_type);
        method.setParent(a_class);
        a_class.addMethod(method);
        myModel.getStatic().put(method.getIDString(),method);
        
        return method;
    }
    
}

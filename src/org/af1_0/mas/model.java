/*
 * model.java
 *
 * Created on 23 ottobre 2002, 16.02
 */

package org.af1_0.mas;

import java.util.Hashtable ;
import java.util.Map;
import java.util.Iterator;
import java.util.Enumeration;


import org.af1_0.entity.selector.*;
import org.af1_0.entity.*;

/**
 * Questa classe rappresenta un modello astratto del progetto che non specifica per nessuna piattaforma ad agenti
 * @author  Luca
 * @see MultiAgentSystem per l'implementazione concreta
 */
public class model {
    protected Hashtable static_structure;
    protected Hashtable dynamic_behaviour;
    
    /**
     *  crea una nuova istanza
     */
    public model() {
        static_structure = new Hashtable();
        dynamic_behaviour = new Hashtable();
    }
    
    /**
     * Il modello tiene conto delle entit� statiche registrandole su una hastable
     * La notazione interna per la registrazione �:
     *    nome_agente per gli agenti
     *    nome_agente.nome_entity per task, attributi e metodi dell'agente
     *    nome_agente.nome_task.nome_entity per attributi e metodi del task
     * @return la hastable relativa alle entit� statiche
     */
    public Map getStatic() {
        return static_structure;
    }
    
    /**
     * Il modello tiene conto delle entit� dinamiche registrandole su una hastable
     * @return la hastable relativa alle entit� dinamiche
     */
    public Map getDynamic() {
        return dynamic_behaviour;
    }

    /**
     * @deprecated � preferibile usare l'oggetto entity_merging
     * @param submodel modello da unire al modello corrente
     */
    public void addSubmodel(model submodel) {
        // effettua il merge degli elementi dei due modelli
    }
    
    /**
     * Questo metodo permette di ottenere un sotto-modello del modello corrente.
     * Gli elementi del sottomodello sono riferimenti agli elementi del modello di partenza,
     * non elementi clonati, quindi ogni modifica si rispecchia sul modello di partenza.
     * La selezione degli elementi da includere avviene specificando un filtro, ottenuto
     * dal package org.af1_0.entity.selection
     *
     * @param selector
     * @param insert_relation
     * @return un nuovo modello
     * @deprecated questo metodo non � pi� utile
     */
    public model getSubmodel(agent_selector selector, boolean insert_relation) {
        model submodel = new model();
        entity_merging merging = new entity_merging(submodel);
        
        Iterator iter = selector.getAgentNameList().listIterator();
        while (iter.hasNext()) {
            String agent_name = (String) iter.next();         
            java_class agent = (java_class) static_structure.get(agent_name);
            if (agent != null)
                merging.addAgent(agent);
        }
        
        // se specificato dal parametro insert_relation si aggiunge tutte le relazioni che
        // coinvolgono gli elementi del sotto modello
        if (insert_relation) {
            Enumeration e = dynamic_behaviour.elements();
            while (e.hasMoreElements()) {
                relation rel = (relation) e.nextElement();
                
                java_method start = rel.getStart();
                java_method end = rel.getEnd();
                
                String start_id[] = start.getID();
                String end_id[] = end.getID();
                
                // se entrambi gli estremi sono relativi ad agenti del sotto-modello
                if (submodel.getStatic().containsKey(start_id[0]) &&
                    submodel.getStatic().containsKey(end_id[0]) )
                    submodel.getDynamic().put(rel.getName(),rel);
            }
        }
        
        return submodel;
    }

    /**
     * elimina tutte le entit� del sistema (avvia un nuovo progetto)
     */
    public void clearAll() {
        static_structure = new Hashtable();
        dynamic_behaviour = new Hashtable();
    }

    /**
     * metodo usato per il debug
     * @return una stringa che descrive le entit� presenti nel sistema
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        
        Iterator it;
        
        it = static_structure.keySet().iterator();
        while (it.hasNext())
            buffer.append("[static] " + it.next()+"\n");
        
        it = dynamic_behaviour.keySet().iterator();
        while (it.hasNext())
            buffer.append("[dynamic] " +it.next()+"\n");
        
        return buffer.toString();
    }
}

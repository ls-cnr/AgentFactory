/*
 * entity_factory.java
 *
 * Created on 24 ottobre 2002, 16.29
 */

package org.af1_0.mas;

import org.af1_0.entity.*;

/**
 * Questa classe viene usata per realizzare delle clone di entità del sistema
 * @author  Luca
 * @see multi_agent_system for the entity container
 */
public class entity_copy {
    multi_agent_system myModel;
    
    /**
     * Creates a new instance of entity_factory
     * @param a_model mas che deve essere soggetto alla clonazione
     */
    public entity_copy(multi_agent_system a_model) {
        myModel = a_model;
    }
    
    // Warning: non rinomina l'agente (o il task) ne gli cambia gli elementi delle liste
    public void copyClass(java_class source, java_class dest) {
        dest.setExtends( source.getExtends( ) );
        dest.setVisibility( source.getVisibility( ) );

        java.util.Iterator it; 
        
        it = source.getModifierList();
        while(it.hasNext()) {
            String mod = (String) it.next();
            
            dest.addModifier(mod);
        }

    }
    
    public void copyAttribute(java_attribute source, java_attribute dest) {
        dest.setType( source.getType( ) );
        dest.setStartValue( source.getStartValue( ) );
        dest.setVisibility( source.getVisibility( ) );

        java.util.Iterator it; 
        
        it = source.getModifierList();
        while(it.hasNext()) {
            String mod = (String) it.next();
            
            dest.addModifier(mod);
        }

    }
    
    public void copyMethod(java_method source, java_method dest) {
        dest.setType( source.getType( ) );
        dest.setCode( source.getCode( ) );
        dest.setActionPattern( source.isActionPattern() );
        dest.setVisibility( source.getVisibility( ) );
        
        java.util.Iterator it; 
        
        it = source.getArgomentList();
        while(it.hasNext()) {
            argoment arg = (argoment) it.next();
            
            dest.addArgoment(arg);
        }
        
        it = source.getModifierList();
        while(it.hasNext()) {
            String mod = (String) it.next();
            
            dest.addModifier(mod);
        }

        it = source.getExceptionList();
        while(it.hasNext()) {
            String ex = (String) it.next();
            
            dest.addException(ex);
        }

    }
    
}

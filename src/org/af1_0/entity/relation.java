/*
 * relation.java
 *
 * Created on 23 ottobre 2002, 16.21
 */

package org.af1_0.entity;

import java.util.Observable;

/**
 *
 * @author  Luca
 */
public class relation extends Observable implements entity,dynamic_entity {
    protected java_method start;
    protected java_method end;
    protected String label;
    
    /** Creates a new instance of relation */
    public relation() {
        start = null;
        end = null;
    }
    
    public String getName() {
        return label;
    }
    
    public java_method getStart() {
        return start;
    }

    public String getStartMethodID() {
        if (start != null)
            return start.getIDString();
        else
            return "#unassigned#";
    }

    public java_method getEnd() {
        return end;
    }

    public String getEndMethodID() {
        if (end != null)
            return end.getIDString();
        else
            return "#unassigned#";
    }

    public void setName(String a_name) {
        label = a_name;
    }
    
    public void setStart(java_method a_method) {
        start = a_method;
    }

    public void setEnd(java_method a_method) {
        end = a_method;
    }
    
    public String toString() {
        if (start != null && end != null)
            return start.getIDString()+"->"+end.getIDString();
        else
            return label;
    }
    
    public void notifyChange() {
        setChanged();
        notifyObservers();
    }
    
    public boolean isAttribute() {
        return false;
    }
    
    public boolean isClass() {
        return false;
    }
    
    public boolean isMethod() {
        return false;
    }
    
    public boolean isRelation() {
        return true;
    }
    
    public boolean isAgent() {
        return false;
    }
    
    public boolean isTask() {
        return false;
    }
    
}

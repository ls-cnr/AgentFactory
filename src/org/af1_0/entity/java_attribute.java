/*
 * java_attribute.java
 *
 * Created on 23 ottobre 2002, 16.20
 */

package org.af1_0.entity;

import java.util.Observable;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author  Luca
 */
public class java_attribute extends Observable implements entity,static_entity {
    protected String type;
    protected String name;
    protected String start_value;
    protected String visibility;
    protected java_class parent;
    
    protected LinkedList modifiers;

    /** Creates a new instance of java_attribute */
    public java_attribute() {
        parent = null;
        visibility = "private";
        modifiers = new LinkedList();
    }
    
    public String getVisibility() {
        return visibility;
    }
    
    public java_class getParent() {
        return parent;
    }
    
    public void setVisibility(String visib) {
        visibility = visib;
    }
    
    public void setParent(java_class the_parent) {
        parent = the_parent;
    }

    public String getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }
    
    public String getStartValue() {
        return start_value;
    }
    
    public void setType(String a_type) {
        type = a_type;
    }
    
    public void setName(String a_name) {
        name = a_name;
    }
    
    public void setStartValue(String a_start_value) {
        start_value = a_start_value;
    }
    
    public ListIterator getModifierList() {
        return modifiers.listIterator(0);
    }

    public void addModifier(String mod) {
        modifiers.add(mod);
    }
    
    public String[] getID() {
        String[] id;
        java_class myparent = (java_class) this.getParent();
        java_class hisparent = (java_class) myparent.getParent();
        if (hisparent != null) {
            id = new String[3];
            id[0] = hisparent.getName();
            id[1] = myparent.getName();
            id[2] = name;
        } else {
            id = new String[2];
            id[0] = myparent.getName();
            id[1] = name;
        }
        
        return id;
    }

    public String getIDString() {
        String[] ids = this.getID();
        String id = new String();
        for (int i=0; i<ids.length; i++) {
            if (i != 0)
                id += "." + ids[i];
            else
                id += ids[i];
        }
        return id;
    }

    public String toString() {
        return name + " : " + type;
    }
    public void notifyChange() {
        setChanged();
        notifyObservers();
        if (parent != null)
            parent.notifyChange();
    }
    
    public boolean isAttribute() {
        return true;
    }
    
    public boolean isClass() {
        return false;
    }
    
    public boolean isMethod() {
        return false;
    }
    
    public boolean isRelation() {
        return false;
    }
    
    public boolean isAgent() {
        return false;
    }
    
    public boolean isTask() {
        return false;
    }
}

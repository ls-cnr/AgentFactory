/*
 * java_method.java
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
public class java_method extends Observable implements entity,static_entity {
    protected String name;
    protected String type;
    protected String visibility;
    protected java_class parent;
    protected String code;
    protected boolean code_is_action;
    
    protected LinkedList exceptions;
    protected LinkedList argoments;
    protected LinkedList modifiers;
        
    protected LinkedList activations;
        
    /** Creates a new instance of java_method */
    public java_method() {
        exceptions = new LinkedList();
        argoments = new LinkedList();
        modifiers = new LinkedList();
        parent = null;
        visibility = "public";
        code_is_action = false;
        
        activations = new LinkedList();
    }
    
    public boolean isActionPattern() {
        return code_is_action;
    }
    
    public void setActionPattern(boolean is) {
        code_is_action = is;
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
    
    public void setCode(String the_code) {
        code = the_code;
    }
    
    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }
    
    public void setType(String a_type) {
        type = a_type;
    }
    
    public void setName(String a_name) {
        name = a_name;
    }
    
    public ListIterator getActivationList() {
        return activations.listIterator(0);
    }

    public ListIterator getExceptionList() {
        return exceptions.listIterator(0);
    }
    
    public ListIterator getArgomentList() {
        return argoments.listIterator(0);
    }

    public ListIterator getModifierList() {
        return modifiers.listIterator(0);
    }
    
    public void addActivation(java_class task) {
        activations.add(task);
    }

    public void addException(String exc) {
        exceptions.add(exc);
    }
    
    public void addArgoment(argoment arg) {
        argoments.add(arg);
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

    /*public String toString() {
        return name;
    }*/
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        
        java.util.Iterator it;
        
        buffer.append(visibility + " ");
        
        it = modifiers.iterator();
        while(it.hasNext()) {
            String mod = (String) it.next();
            buffer.append(mod + " ");
        }
        buffer.append(type + " " + name + "(");
        
        it = argoments.iterator();
        boolean first = true;
        while(it.hasNext()) {
            argoment arg = (argoment) it.next();
            if (first == false)
                buffer.append(", ");
            buffer.append(arg.getType() + " " + arg.getName());
            first = false;
        }
        
        buffer.append(");");
        return buffer.toString();
    }
    
    public void notifyChange() {
        setChanged();
        notifyObservers();
        if (parent != null)
            parent.notifyChange();
    }
    
    public boolean isAttribute() {
        return false;
    }
    
    public boolean isClass() {
        return false;
    }
    
    public boolean isMethod() {
        return true;
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

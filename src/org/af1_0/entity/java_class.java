/*
 * java_class.java
 *
 * Created on 23 ottobre 2002, 16.20
 */

package org.af1_0.entity;

import java.util.Observable;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;

/**
 * descrizione statica di una classe uml-java
 * una classe e' identificata da un nome, uno stereotipo e una sola super class
 * una classe può contenere attributi, metodi e altre classi
 *
 * @author  Luca
 */
public class java_class extends Observable implements entity,static_entity {
    protected String stereotype;
    protected String name;
    protected String super_class;
    protected String visibility;
    protected java_class parent;
    
    protected LinkedList attributes;
    protected LinkedList methods;
    protected LinkedList inner_classes;
    protected LinkedList interfaces;
    protected LinkedList modifiers;
    
    /** Creates a new instance of java_class */
    public java_class() {
        attributes = new LinkedList();
        methods = new LinkedList();
        inner_classes = new LinkedList();
        interfaces = new LinkedList();
        modifiers = new LinkedList();
        parent = null;
        name = "";
        stereotype = "";
        visibility = "public";
        super_class = "";
        parent = null;
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

    public String getStereotype() {
        return stereotype;
    }
    
    public String getName() {
        return name;
    }
    
    public String getExtends() {
        return super_class;
    }
    
    public void setStereotype(String a_stereotype) {
        stereotype = a_stereotype;
    }
    
    public void setName(String a_name) {
        name = a_name;
    }
    
    public void setExtends(String a_super_class) {
        super_class = a_super_class;
    }
    
    public ListIterator getAttributeList() {
        return attributes.listIterator(0);
    }
    
    public ListIterator getMethodList() {
        return methods.listIterator(0);
    }
    
    public ListIterator getInnerClassList() {
        return inner_classes.listIterator(0);
    }

    public ListIterator getInterfaceList() {
        return interfaces.listIterator(0);
    }
    
    public ListIterator getModifierList() {
        return modifiers.listIterator(0);
    }

    public void addAttribute(java_attribute attribute) {
        attributes.add(attribute);
        attribute.setParent(this);
    }
    
    public void addMethod(java_method method) {
        methods.add(method);
        method.setParent(this);
    }

    public void addInnerClass(java_class _class) {
        inner_classes.add(_class);
        _class.setParent(this);
    }
    
    public void addInterface(String _interface) {
        interfaces.add(_interface);
    }

    public void addModifier(String mod) {
        modifiers.add(mod);
    }
    
    public String[] getID() {
        String[] id;
        java_class myparent = (java_class) this.getParent();
        if (myparent != null) {
            id = new String[2];
            id[0] = myparent.getName();
            id[1] = name;
        } else {
            id = new String[1];
            id[0] = name;
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
        return name;
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
        return true;
    }
    
    public boolean isMethod() {
        return false;
    }
    
    public boolean isRelation() {
        return false;
    }
    
    public boolean isAgent() {
        return stereotype.equals("Agent");
    }
    
    public boolean isTask() {
        return stereotype.equals("Task");
    }
    
    public String debugString() {
        StringBuffer stringa = new StringBuffer();
        
        java.util.Iterator it = methods.iterator();
        while (it.hasNext()) {
            java_method method = (java_method) it.next();
            stringa.append("["+method.getName()+"\n");
            stringa.append(method.getCode()+"]\n");
        }
        
        return stringa.toString();
    }
}

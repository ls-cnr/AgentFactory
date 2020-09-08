/*
 * argoment.java
 *
 * Created on 23 ottobre 2002, 17.40
 */

package org.af1_0.entity;

/**
 *
 * @author  Luca
 */
public class argoment {
    protected String type;
    protected String name;
    
    /** Creates a new instance of argoment */
    public argoment() {
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
    
}

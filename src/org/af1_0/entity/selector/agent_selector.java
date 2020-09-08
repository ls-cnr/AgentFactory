/*
 * model_selector.java
 *
 * Created on 23 ottobre 2002, 16.36
 */

package org.af1_0.entity.selector;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author  Luca
 */
public class agent_selector { 
    protected LinkedList entities;
    
    /** Creates a new instance of model_selector */
    public agent_selector() {
        entities = new LinkedList();
    }
    
    public List getAgentNameList() {
        return entities;
    }
   
}

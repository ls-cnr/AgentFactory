/*
 * pattern.java
 *
 * Created on 25 ottobre 2002, 15.55
 */

package org.af1_0.pattern;

import java.util.LinkedList;
import java.util.ListIterator;

import org.af1_0.entity.entity;
import org.af1_0.entity.java_method;
import org.af1_0.mas.multi_agent_system;
/**
 *
 * @author  Luca
 */
public class pattern extends multi_agent_system {
    protected pattern_description description;
    
    /** Creates a new instance of pattern */
    public pattern(String platform) {
        super(platform);
    }
    
    public pattern_description getPatternDescription() {
        return description;
    }
    
    public void setPatternDescription(pattern_description a_description) {
        description = a_description;
    }
    
}

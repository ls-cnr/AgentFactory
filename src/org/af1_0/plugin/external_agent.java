/*
 * external_agent.java
 *
 * Created on 31 dicembre 2002, 15.57
 */

package org.af1_0.plugin;

import java.util.Vector;
import java.util.Iterator;

/**
 *
 * @author  Luca
 */
public class external_agent {
    protected String agent_name;
    protected Vector task_list;
    
    public external_agent() {
        agent_name = "no_name";
        task_list = new Vector();
    }

    public external_agent(String name) {
        agent_name = name;
        task_list = new Vector();
    }
    
    public void setName(String name) {
        agent_name = name;
    }
    
    public String getName() {
        return agent_name;
    }
    
    public void addTask(String name) {
        task_list.add(name);
    }
    
    public Iterator taskList() {
        return task_list.iterator();
    }
    
}

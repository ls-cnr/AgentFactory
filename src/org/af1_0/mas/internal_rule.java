/*
 * entity_merging.java
 *
 * Created on 24 ottobre 2002, 17.10
 */

package org.af1_0.mas;

import java.util.Iterator;
import org.af1_0.entity.*;

/**
 *
 * @author  Luca
 */
public class internal_rule {
    multi_agent_system myModel;
    
    /** Creates a new instance of entity_merging */
    public internal_rule(multi_agent_system a_model) {
        myModel = a_model;
    }
    
    public void appy() {
        if (myModel.getAgentPlatform().equals("FIPAOS"))
            appyDoneMethod();       
    }
    
    private void appyDoneMethod() {
        entity_factory factory = new entity_factory(myModel);
        
        Iterator it = myModel.getDynamic().values().iterator();
        
        while (it.hasNext()) {
            relation rel = (relation) it.next();
            
            java_method meth1 = rel.getStart();
            java_method meth2 = rel.getEnd();
            
            if (meth1 != null && meth2!=null) {
            // controlla se i metodi sono relativi a due task-parent diversi
                java_class parent1 = meth1.getParent();
                java_class parent2 = meth2.getParent();

                if (parent1.isTask() && parent2.isTask())
                    if (parent1 != parent2 && parent1.getParent() == parent2.getParent()) {

                        // aggiungo un done method al parent1
                        String method_name = "done"+parent2.getParent().getName()+"_"+parent2.getName();
                        factory.createMethod(parent1,"void",method_name);

                    }
            }
        }
    }
}

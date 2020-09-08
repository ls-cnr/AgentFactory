/*
 * repository_designer.java
 *
 * Created on 26 ottobre 2002, 10.53
 */

package org.af1_0.repository;

import java.util.Vector;
import javax.xml.transform.stream.StreamSource;

import org.af1_0.pattern.pattern_description;

/** Questa interfaccia è stata disegnata per fornire metodi per l'accesso al database.
 * L'implementazione dell'accesso è assegnata alle classi che realizzeranno tale
 * interfaccia e può essere locale o remoto
 * @author Luca
 */
public interface repository_designer {
    public Vector queryComponentList();
    public pattern_description queryComponentItem(String name);
    public Vector queryBehaviourList();
    public pattern_description queryBehaviourItem(String name);
    public Vector queryServiceList();
    public pattern_description queryServiceItem(String name);
    public String getActionPattern(String name,String parent, String platform);
    
    //public StreamSource getResource(String resource);
    
    public boolean isLocale();
    public boolean isRemote();
}

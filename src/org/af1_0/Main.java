/*
 * Main.java
 *
 * Created on 4 marzo 2004, 11.47
 */

package org.af1_0;

import org.af1_0.gui.AgentFactoryGUI;
import de.hunsicker.jalopy.storage.Convention;
import de.hunsicker.jalopy.storage.*;

/**
 *
 * @author  Luca
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Convention settings = Convention.getInstance();
        /*try {
            settings.exportSettings(new File("c:\\settings_prima.xml"));
        } catch(IOException e) {
            
        }*/
        settings.putBoolean(ConventionKeys.BRACE_INSERT_IF_ELSE,false);
        settings.putBoolean(ConventionKeys.BRACE_INSERT_FOR,false);
        settings.putBoolean(ConventionKeys.BRACE_INSERT_WHILE,false);
        /*try {
            settings.exportSettings(new File("c:\\settings_dopo.xml"));
        } catch(IOException e) {
            
        }*/
        
        new AgentFactoryGUI().show();
    }
    
}

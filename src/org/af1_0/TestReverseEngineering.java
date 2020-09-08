/*
 * TestReverseEngineering.java
 *
 * Created on 4 marzo 2004, 12.27
 */

package org.af1_0;

import org.af1_0.gui.AgentFactoryGUI;
import org.af1_0.reverse.java_parser;
import org.af1_0.entity.java_class;
import org.af1_0.mas.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.File;

import de.hunsicker.jalopy.Jalopy;
import de.hunsicker.jalopy.storage.Convention;
import de.hunsicker.jalopy.storage.*;

/**
 *
 * @author  Luca
 */
public class TestReverseEngineering {
    
    /** Creates a new instance of TestReverseEngineering */
    public TestReverseEngineering() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Convention settings = Convention.getInstance();
        settings.putBoolean(ConventionKeys.BRACE_INSERT_IF_ELSE,false);
        settings.putBoolean(ConventionKeys.BRACE_INSERT_FOR,false);
        settings.putBoolean(ConventionKeys.BRACE_INSERT_WHILE,false);
        /*try {
            Jalopy.setConvention(new File("c:\\settings_dopo.xml"));
        } catch(IOException e) {
            
        }*/
        AgentFactoryGUI afgui = new AgentFactoryGUI();
        afgui.show();
        
        java_parser parser = new java_parser();
        
        FileReader reader;
        try {
            reader = new FileReader("C:\\Planner.java");
            java_class my_agent = parser.performParse(reader,afgui.getModel());
            entity_merge merge = new entity_merge(afgui.getModel());
            merge.mergeAgent(my_agent);
            
            afgui.update();
        } catch(IOException e) {
            System.out.println("c'è un errore");
        }
    }
    
}

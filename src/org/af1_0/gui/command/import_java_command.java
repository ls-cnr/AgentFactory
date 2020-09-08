/*
 * import_java_command.java
 *
 * Created on 10 marzo 2004, 18.38
 */

package org.af1_0.gui.command;

import javax.swing.*;
import java.io.*;
import org.af1_0.reverse.java_parser;
import org.af1_0.entity.java_class;
import org.af1_0.mas.*;
import de.hunsicker.jalopy.Jalopy;
import de.hunsicker.jalopy.storage.Convention;
import de.hunsicker.jalopy.storage.*;


/**
 *
 * @author  Luca
 */
public class import_java_command implements command {
    private JFrame my_frame;
    private multi_agent_system model;
    
    /** Creates a new instance of import_java_command */
    public import_java_command(JFrame my_frame, multi_agent_system model) {
        this.my_frame = my_frame;
        this.model = model;
    }
    
    public void execute() {        
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        int returnVal = chooser.showOpenDialog(my_frame);
        if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
                
            java_parser parser = new java_parser();

            FileReader reader;
            try {
                reader = new FileReader(chooser.getSelectedFile());
                java_class my_agent = parser.performParse(reader,model);
                entity_merge merge = new entity_merge(model);
                merge.mergeAgent(my_agent);
            } catch(IOException e) {
                System.out.println("c'è un errore");
            }

        }
    }
    
}

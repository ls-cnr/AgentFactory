/*
 * java_indenter.java
 *
 * Created on 27 novembre 2002, 16.15
 */

package org.af1_0.gui.xml;

import java.io.Reader;
import java.io.StringReader;
import java.io.FileWriter;
import java.io.IOException;
import de.hunsicker.jalopy.Jalopy;
import java.io.File;

/**
 *
 * @author  Luca
 */
public class java_indenter_1_1 {
    private String unindented_java;
    
    /** Creates a new instance of java_indenter */
    public java_indenter_1_1(String source) {
        unindented_java = source;
    }
    
    public StringBuffer getIndented() {
        StringBuffer pretty = new StringBuffer();
        try {
            File temp = File.createTempFile("agent","java");
            FileWriter writer = new FileWriter(temp);
            writer.write(unindented_java);
            
            writer.close();
            
            Jalopy jalopy = new Jalopy();
            jalopy.setInput(temp);
            jalopy.setOutput(pretty);
            boolean fatto = jalopy.format();
            if (!fatto) {
                System.out.println("operazione non svolta!");
                pretty.append(unindented_java);
                if (jalopy.getState() == Jalopy.State.OK)
                    System.out.println(" successfully formatted");
                else if (jalopy.getState() == Jalopy.State.WARN)
                    System.out.println(" formatted with warnings");
                else if (jalopy.getState() == Jalopy.State.ERROR)
                    System.out.println(" could not be formatted: "+jalopy.getState().toString());
            }
        } catch(IOException e) {
            System.out.println("errore");
        }
        return pretty;
    }
    
}

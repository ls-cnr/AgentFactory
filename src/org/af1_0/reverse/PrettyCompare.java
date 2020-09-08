/*
 * PrettyCompare.java
 *
 * Created on 5 marzo 2004, 9.54
 */

package org.af1_0.reverse;

import org.af1_0.gui.xml.java_indenter_1_1;

/**
 *
 * @author  Luca
 */
public class PrettyCompare {
    private String a;
    private String b;
    private boolean result;
    private int i_a;
    private int i_b;
    
    /** Creates a new instance of PrettyCompare */
    public PrettyCompare(String a, String b) {
        this.a = a;
        this.b = b;
        
        boolean uguali = true;
        boolean fine = false;
        StringBuffer buffer_a = new StringBuffer(a);
        StringBuffer buffer_b = new StringBuffer(b);
        String line_a;
        String line_b;
       
        /*
        while (uguali==true && fine==false) {            
            if (buffer_a.length()>0 || buffer_b.length()>0) {
                line_a = getUnemptyLine(buffer_a);
                line_b = getUnemptyLine(buffer_b);
                if (!line_a.equals(line_b)) {
                    fine = true;
                    uguali = false;
                }
            } else {
		fine = true;
            }
        }*/
        
        char c_a;
        char c_b;
        i_a = 0;
        i_b = 0;
        
        while(uguali==true && fine==false) {
            if (i_a<a.length() || i_b<b.length()) {
                c_a = next_char_a(a);
                c_b = next_char_b(b);
                if (c_a!=c_b)
                    uguali = false;
            } else {
                fine = true;
            }
        }
        
        result = uguali;              
    }
    
    private char next_char_a(String string) {
        boolean trovato = false;
        boolean fine = false;
        char c=' ';
        while (trovato==false && fine==false) {
            if (i_a<string.length()) {
                c = string.charAt(i_a);
                i_a++;
                if (!Character.isWhitespace(c))
                    trovato=true;
            } else {
                fine=true;
                c=' ';
            }
        }
        return c;
    }
    
     private char next_char_b(String string) {
        boolean trovato = false;
        boolean fine = false;
        char c=' ';
        while (trovato==false && fine==false) {
            if (i_b<string.length()) {
                c = string.charAt(i_b);
                i_b++;
                if (!Character.isWhitespace(c))
                    trovato=true;
            } else {
                fine=true;
                c=' ';
            }
        }
        return c;
    }

     public boolean compare() {
        return result;
    }
    
    private String getUnemptyLine(StringBuffer string) {
        String line="";
        boolean trovato = false;
        while (trovato==false && string.length()>0) {
            line = getLine(string).trim();
            if (line.length()>0)
                trovato = true;
        } 
        
        return line;
    }
    
    private String getLine(StringBuffer string) {
        StringBuffer buffer = new StringBuffer();
        char c;
        boolean trovato = false;
        
        while (trovato==false && string.length()>0) {
            c = string.charAt(0);
            string.deleteCharAt(0);
            if (c!='\n')
                buffer.append(c);
            else
                trovato =true;
        }
        
        return buffer.toString();
    }
    
}

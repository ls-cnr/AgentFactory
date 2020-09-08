/*
 * java_indenter.java
 *
 * Created on 27 novembre 2002, 16.15
 */

package org.af1_0.gui.xml;

import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;


/**
 *
 * @author  Luca
 */
public class java_indenter_1 {
    private String unindented_java;
    
    /** Creates a new instance of java_indenter */
    public java_indenter_1(String source) {
        unindented_java = source;
    }
    
    public StringBuffer getIndented() {
        //StringReader in = new StringReader(unindented_java);
        StringBuffer pretty = new StringBuffer();
        final int CODE = 0;
        final int WHITESPACE = 1;
        final int POSSIBLE_START = 2;
        final int COMMENT = 3;
        final int POSSIBLE_END = 4;
        final int END = 5;
        int stato = CODE;
        int i=0;
        char c=0;
        int tab_n = 0;
        boolean inizio_riga = false;
        
        while (i<unindented_java.length()) {
            c = unindented_java.charAt(i);
            i++;
            switch (stato) {
                case CODE:
                    if (c==';') {
                        pretty.append(";\n");
                        inizio_riga = true;
                    }else if (c=='{') {
                        pretty.append("{\n");
                        inizio_riga = true;
                        tab_n++;
                    } else if (c=='}') {
                        pretty.append(tab(tab_n));
                        pretty.append("}\n");
                        inizio_riga = true;
                        tab_n--;
                    } else if ( Character.isWhitespace(c) )
                        stato = WHITESPACE;
                    else
                        pretty.append(c);
                    break;
                    
                case WHITESPACE:
                    if (c==';') {
                        pretty.append(";\n");
                        inizio_riga = true;
                        stato = CODE;
                    } else if (c=='{') {
                        pretty.append("{\n");
                        inizio_riga = true;
                        tab_n++;
                        stato = CODE;
                    } else if (c=='}') {
                        pretty.append(tab(tab_n));
                        pretty.append("}\n");
                        inizio_riga = true;
                        tab_n--;
                        stato = CODE;
                    } else if (!Character.isWhitespace(c)) {
                        if (inizio_riga) {
                            pretty.append(tab(tab_n));
                            inizio_riga = false;
                        } else
                            pretty.append(' ');
                        pretty.append(c);
                        stato = CODE;
                    }
                    break;
                    
                case END:
                    break;
                    
            }
        }
        
        
        return pretty;
    }
    
    private String tab(int num) {
        String result = new String();
        for (int i=0; i<num; i++)
            result = result + "\t";
        return result;
    }
}

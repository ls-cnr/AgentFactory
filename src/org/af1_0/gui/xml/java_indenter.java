/*
 * java_indenter.java
 *
 * Created on 27 novembre 2002, 16.15
 */

package org.af1_0.gui.xml;

import java.io.StreamTokenizer;
import java.io.Reader;
import java.io.StringReader;


/**
 *
 * @author  Luca
 */
public class java_indenter {
    private String unindented_java;
    
    /** Creates a new instance of java_indenter */
    public java_indenter(String source) {
        unindented_java = source;
    }
    
    public StringBuffer getIndented() {
        Reader in = new StringReader(unindented_java);
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        
        tokenizer.resetSyntax();
        tokenizer.wordChars(33,126);

        tokenizer.ordinaryChar(';');
        tokenizer.ordinaryChar('{');
        tokenizer.ordinaryChar('}');
        
        StringBuffer pretty_java = new StringBuffer();
        
        int token_value;
        int open_bracket = 0;
        try {
            do {
               token_value = tokenizer.nextToken();

               if (token_value == StreamTokenizer.TT_WORD)
                   pretty_java.append( tokenizer.sval + " " );
               
               if (token_value == ';')
                   pretty_java.append( ";\n" + tab( open_bracket ) );

               if (token_value == '{') {
                   open_bracket++;
                   pretty_java.append( "{\n" + tab( open_bracket) );
               }

               if (token_value == '}') {
                   open_bracket--;
                   pretty_java.append( "\n"+ tab( open_bracket) + "}\n" + tab( open_bracket) );
               }


            } while (token_value != StreamTokenizer.TT_EOF );
        } catch (java.io.IOException ex) {
            // do nothing
        }
        
        return pretty_java;
    }

    private String tab(int num) {
        String result = new String();
        for (int i=0; i<num; i++)
            result = result + "\t";
        return result;
    }  
}

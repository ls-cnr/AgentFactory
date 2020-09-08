/*
 * agent_to_java.java
 *
 * Created on 26 novembre 2002, 17.31
 */

package org.af1_0.gui.xml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringWriter;
import java.io.StringReader;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.af1_0.entity.java_class;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.mas.entity_merging;
import org.af1_0.mas.internal_rule;

/**
 *
 * @author  Luca
 */
public class agent_to_java {
    protected java_class agent;
    protected multi_agent_system mas;
    
    /** Creates a new instance of agent_to_java */
    public agent_to_java(java_class agent_to_transform,multi_agent_system model) {
        agent = agent_to_transform;
        mas = new multi_agent_system(model.getAgentPlatform());
        
        entity_merging merge = new entity_merging(mas);
        merge.addModel(model);
        
        // risolve i vincoli interni (done method)
        internal_rule rule = new internal_rule(mas);
        rule.appy();
    }

    public String getJava() {
        TransformerFactory tfactory=null;
        Transformer trasformer=null;
        
        agent_to_xml agent_xml = new agent_to_xml(agent);
        
        //System.out.println("agent_to_java riga 48");
        //System.out.println( agent_xml.getXml() );
        
        StringReader source = new StringReader( agent_xml.getXml() );
        
        StringWriter java_code = new StringWriter();
        
        try {
            tfactory = TransformerFactory.newInstance();
            trasformer = tfactory.newTransformer( mas.getJavaTransformation() ); 
            trasformer.transform(new StreamSource(source), new StreamResult(java_code));
            
        } catch (javax.xml.transform.TransformerConfigurationException pce) {
            System.err.println("[getJava] errore Parser: "+pce.getMessage());
        } catch (javax.xml.transform.TransformerException e) {
            System.err.println("[getJava] errore di trasformazione");
        } catch (java.lang.IllegalArgumentException e) {
            System.err.println("[getJava] errore di argomento illegale");
        } finally {
            //System.out.println(java_code);
            String first_step = resolve_action_pattern(java_code.getBuffer());
            java_indenter_1_1 indenter = new java_indenter_1_1( first_step );
            return ( indenter.getIndented().toString() );
        }
        
    }
    
    /* macchina a stati finiti per scroprire e sostituire gli action
     * pattern presenti nel codice 
     */
    private String resolve_action_pattern(StringBuffer code) {
        StringBuffer destination = new StringBuffer();
        StringBuffer action_pattern_name = null;
        int lunghezza = code.length();
        int cont = 0;
        int stato = 0;
        char carattere;
        
        while (cont < lunghezza) {
            carattere = code.charAt(cont);
            
            switch (stato) {
                case 0:
                    if (carattere != '#')
                        destination.append(carattere);                       
                    else
                        stato = 1;
                    cont++;
                    break;
                
                case 1:
                    if (carattere != '^')
                        stato = 0;                       
                    else {
                        action_pattern_name = new StringBuffer();
                        cont++;
                        stato = 2;
                    }                    
                    break;
                    
                case 2:
                    if (carattere != '^') 
                        action_pattern_name.append(carattere);
                    else {
                        // trovato action pattern
                        String name = getNameFrom(action_pattern_name);
                        String target = action_pattern_name.toString();
                        
                        //System.out.println("["+name+"] AND ["+target+"]");
                        
                        destination.append( mas.getActionPattern(name,target) );
                        cont++;
                        stato = 0;
                    }
                    cont++;
                    break;
            }
            
        }
        return destination.toString();
    }
    
    private String getNameFrom(StringBuffer sequence) {
        StringBuffer name = new StringBuffer();

        char carattere;
        boolean fine = false;
        
        while (fine==false) {
            carattere = sequence.charAt(0);
            sequence.deleteCharAt(0);
            
            if (carattere == '@')
                fine = true;
            else
                name.append(carattere);
        }
        
        return name.toString();
    }
    
}

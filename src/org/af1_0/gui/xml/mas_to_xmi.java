/*
 * agent_to_xml.java
 *
 * Created on 25 novembre 2002, 10.05
 */

package org.af1_0.gui.xml;

import java.util.*;

import java.io.StringWriter;
import java.io.StringReader;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.af1_0.entity.*;
import org.af1_0.mas.*;

/**
 *
 * @author  Luca
 */
public class mas_to_xmi {
    protected multi_agent_system mas;
    
    protected StringBuffer data_type = null;
    
    /** Creates a new instance of agent_to_xml */
    public mas_to_xmi(multi_agent_system mas_to_transform) {
        mas = mas_to_transform;
    }
    
    public String getXmi() {
        StringBuffer buffer = new StringBuffer();
		entity_search search = new entity_search(mas);
		List agents = search.getAgentList();
		Iterator it = agents.iterator();
		
		buffer.append("<Model platform='"+mas.getAgentPlatform()+"'>");
		
		while (it.hasNext()) {
			java_class agent = (java_class) it.next();
			agent_to_xml transform = new agent_to_xml(agent);
			buffer.append( transform.getXml() );
		}
		
		buffer.append("</Model>");
		
		
		
		return parse(buffer.toString());
    }
	
	private String parse(String in) {
        TransformerFactory tfactory=null;
        Transformer trasformer=null;
        
        StringReader source = new StringReader( in );        
        StringWriter out = new StringWriter();
        
        try {
            tfactory = TransformerFactory.newInstance();
            trasformer = tfactory.newTransformer( mas.getXmiTransformation() ); 
            trasformer.transform(new StreamSource(source), new StreamResult(out));
            
        } catch (javax.xml.transform.TransformerConfigurationException pce) {
            System.err.println("[getXmi] errore Parser: "+pce.getMessage());
        } catch (javax.xml.transform.TransformerException e) {
            System.err.println("[getXmi] errore di trasformazione");
        } catch (java.lang.IllegalArgumentException e) {
            System.err.println("[getXmi] errore di argomento illegale");
        } finally {
            return out.toString();
        }
	
	}
    
}


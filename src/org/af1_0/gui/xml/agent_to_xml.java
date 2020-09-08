/*
 * agent_to_xml.java
 *
 * Created on 25 novembre 2002, 10.05
 */

package org.af1_0.gui.xml;

import java.util.Iterator;

import org.af1_0.entity.*;
//import org.af1_0.mas.*;

/**
 *
 * @author  Luca
 */
public class agent_to_xml {
    protected java_class agent;
    
    /** Creates a new instance of agent_to_xml */
    public agent_to_xml(java_class agent_to_transform) {
        agent = agent_to_transform;
    }
    
    public String getXml() {
        StringBuffer buffer = new StringBuffer();
        
        buffer.append( writeTag(0,"Agent","name",agent.getName(),agent(),false ));
        
        return buffer.toString();
    }
    
    
    /* private section */
    private String agent() {
        StringBuffer buffer = new StringBuffer();
        Iterator it;
        
        buffer.append( writeTag(1,"Visibility",agent.getVisibility(),true ));
        buffer.append( writeTag(1,"Extends",agent.getExtends(),true ));
        
        it = agent.getAttributeList();
        while (it.hasNext()) {
            java_attribute attribute = (java_attribute) it.next();
            buffer.append( writeTag(1,"Attribute","name",attribute.getName(),"type",attribute.getType(),attribute(2,attribute),false ));
        }
        it = agent.getMethodList();
        while (it.hasNext()) {
            java_method method = (java_method) it.next();
            if (method.getType().equals("costructor"))
                buffer.append( writeTag(1,"Constructor","name",method.getName(),method(2,method),false ));
            else
                buffer.append( writeTag(1,"Method","name",method.getName(),"type",method.getType(),method(2,method),false ));
        }
        it = agent.getInnerClassList();
        while (it.hasNext()) {
            java_class task = (java_class) it.next();
            buffer.append( writeTag(1,"Task","name",task.getName(),task(2,task),false ));
        }
        it = agent.getInterfaceList();
        while (it.hasNext()) {
            String _interface = (String) it.next();
            buffer.append( writeTag(1,"Implements",_interface,true ));
        }
        it = agent.getModifierList();
        while (it.hasNext()) {
            String mod = (String) it.next();
            buffer.append( writeTag(1,"Modifier",mod,true ));
        }

        return buffer.toString();
    }
    
    private String attribute(int tab,java_attribute attribute) {
        StringBuffer buffer = new StringBuffer();
        Iterator it;

        buffer.append( writeTag(tab,"Visibility",attribute.getVisibility(),true ));
        if (attribute.getStartValue() != null && !attribute.getStartValue().equals("") )
            buffer.append( writeTag(tab,"Value",attribute.getStartValue(),true ));

        it = attribute.getModifierList();
        while (it.hasNext()) {
            String mod = (String) it.next();
            buffer.append( writeTag(tab,"Modifier",mod,true ));
        }

        return buffer.toString();        
    }
    private String method(int tab,java_method method) {
        StringBuffer buffer = new StringBuffer();
        Iterator it;

        buffer.append( writeTag(tab,"Visibility",method.getVisibility(),true ));
        if (method.getCode() != null && !method.getCode().equals("") )
            if (method.isActionPattern())
                buffer.append( writeTag(tab,"ActionPattern",method.getCode(),true ));
            else
                buffer.append( writeTag(tab,"Code",method.getCode(),true ));
                

        it = method.getArgomentList();
        while (it.hasNext()) {
            argoment arg = (argoment) it.next();
            buffer.append( writeTag(tab,"Argoment","name",arg.getName(),"type",arg.getType(),"",true ));
        }
        it = method.getExceptionList();
        while (it.hasNext()) {
            String exc = (String) it.next();
            buffer.append( writeTag(tab,"Throws",exc,true ));
        }
        it = method.getModifierList();
        while (it.hasNext()) {
            String mod = (String) it.next();
            buffer.append( writeTag(tab,"Modifier",mod,true ));
        }
        it = method.getActivationList();
        while (it.hasNext()) {
            java_class task = (java_class) it.next();
            buffer.append( writeTag(tab,"Activate",task.getName(),true ));
        }

        return buffer.toString();        
    }
    private String task(int tab,java_class task) {
        StringBuffer buffer = new StringBuffer();
        Iterator it;
        
        buffer.append( writeTag(tab,"Visibility",task.getVisibility(),true ));
        buffer.append( writeTag(tab,"Extends",task.getExtends(),true ));

        it = task.getAttributeList();
        while (it.hasNext()) {
            java_attribute attribute = (java_attribute) it.next();
            buffer.append( writeTag(tab,"Attribute","name",attribute.getName(),"type",attribute.getType(),attribute(tab+1,attribute),false ));
        }
        it = task.getMethodList();
        while (it.hasNext()) {
            java_method method = (java_method) it.next();
            if (method.getType().equals("costructor"))
                buffer.append( writeTag(tab,"Constructor","name",method.getName(),method(tab+1,method),false ));
            else
                buffer.append( writeTag(tab,"Method","name",method.getName(),"type",method.getType(),method(tab+1,method),false ));
        }
        it = task.getInterfaceList();
        while (it.hasNext()) {
            String _interface = (String) it.next();
            buffer.append( writeTag(tab,"Implements",_interface,true ));
        }
        it = task.getModifierList();
        while (it.hasNext()) {
            String mod = (String) it.next();
            buffer.append( writeTag(tab,"Modifier",mod,true ));
        }

        return buffer.toString();
    }


    private String tab(int num) {
        String result = new String();
        for (int i=0; i<num; i++)
            result = result + "\t";
        return result;
    }  
    private String writeAttribute(String name,String value) {
        return name+"=\""+value+"\"";
    }
    
    private String writeTag(int tab,String tagname,String text,boolean a_capo) {
        String ind = "";
        String ter = "";
        if (a_capo==false) {
            ind = tab(tab);
            ter = "\n";
        }    
        if (text == null)
            return tab(tab)+"</" + tagname + ">\n";
        else
            return tab(tab)+"<" + tagname + ">"+ ter + text + ind +"</" + tagname + ">\n";
    }
    private String writeTag(int tab,String tagname,String attribute,String value,String text,boolean a_capo) {
        String ind = "";
        String ter = "";
        if (a_capo==false) {
            ind = tab(tab);
            ter = "\n";
        }    
        if (text == null)
            return tab(tab)+"</" + tagname + " "+writeAttribute(attribute,value)+">\n";
        else
            return tab(tab)+"<" + tagname + " "+writeAttribute(attribute,value)+">"+ ter + text + ind +"</" + tagname + ">\n";        
    }
    private String writeTag(int tab,String tagname,String attr1,String val1,String attr2,String val2,String text,boolean a_capo) {
        String ind = "";
        String ter = "";
        if (a_capo==false) {
            ind = tab(tab);
            ter = "\n";
        }    
        if (text == null)
            return tab(tab)+"</" + tagname + " "+writeAttribute(attr1,val1)+" "+writeAttribute(attr2,val2)+">\n";
        else
            return tab(tab)+"<" + tagname + " "+writeAttribute(attr1,val1)+" "+writeAttribute(attr2,val2)+">"+ ter + text + ind +"</" + tagname + ">\n";        
    }

}

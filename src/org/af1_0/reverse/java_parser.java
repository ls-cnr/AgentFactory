/*
 * java_parser.java
 *
 * Created on 4 marzo 2004, 11.52
 */

package org.af1_0.reverse;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.*;
import org.af1_0.entity.*;
import org.af1_0.mas.multi_agent_system;
import java.io.IOException;
import java.io.StringReader;
import java.io.Reader;

/**
 *
 * @author  Luca
 */
public class java_parser {
    private JavaDocBuilder builder;
    private String my_source;
    private multi_agent_system mas;
    
    /** Creates a new instance of java_parser */
    public java_parser() {
        builder = new JavaDocBuilder();
    }
    
    public java_class performParse(String agent_source,multi_agent_system mas) {
        this.mas = mas;
        my_source = agent_source;
        builder.addSource(new StringReader(agent_source));
        JavaSource source = builder.getSources()[0];
        JavaClass agent_class = source.getClasses()[0];
        
        return parse_agent(agent_class);
    }
    
    public java_class performParse(Reader agent_source,multi_agent_system mas) {
        StringBuffer string = new StringBuffer();
        try {
            while(agent_source.ready()) {
                char c = (char) agent_source.read();
                string.append(c);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return performParse(string.toString(),mas);
        
/*        builder.addSource(agent_source);
        JavaSource source = builder.getSources()[0];
        JavaClass agent_class = source.getClasses()[0];
 
        return parse_agent(agent_class);*/
    }
    
    private java_class parse_agent(JavaClass agent_class) {
        java_class agent = new java_class();
        
        if (agent_class.getSuperClass().getValue().equals("Agent")) {
            agent.setName(agent_class.getName());
            agent.setStereotype("Agent");
            agent.setExtends("Agent");
            
            parse_attributes(agent,agent_class);
            parse_methods(agent,agent_class);
            parse_tasks(agent,agent_class);
        }
        
        return agent;
    }
    
    private void parse_attributes(java_class entity,JavaClass parsed_class) {
        int att_number = parsed_class.getFields().length;
        for (int i=0; i<att_number; i++) {
            JavaField field = parsed_class.getFields()[i];
            java_attribute attr = new java_attribute();
            attr.setName(field.getName());
            attr.setType(field.getType().getValue());
            attr.setParent(entity);
            
            entity.addAttribute(attr);
        }
    }
    
    private void parse_methods(java_class entity,JavaClass parsed_class) {
        int met_number = parsed_class.getMethods().length;
        for (int i=0; i<met_number; i++) {
            JavaMethod meth = parsed_class.getMethods()[i];
            java_method oper = new java_method();
            oper.setName(meth.getName());
            
            if (meth.isPublic())
                oper.setVisibility("public");
            else if (meth.isPrivate())
                oper.setVisibility("private");
            else
                oper.setVisibility("protected");
            
            if (meth.isConstructor()) {
                oper.setType("");
            } else {
                oper.setType(meth.getReturns().getValue());
            }
            
            // eccezioni
            // parametri
            oper.setParent(entity);
            entity.addMethod(oper);
            
            DocletTag tag = meth.getTagByName("PCL");
            if (tag!=null) {
                String action = tag.getNamedParameter("action_pattern");
                if (action.length()>0) {
                    String parsed_code = getCodeFromSource(tag.getLineNumber(),oper.getName());
                    StringBuffer buffer = new StringBuffer(action);
                    String name = getNameFrom(buffer);
                    String target = buffer.toString();
                    String original_code = mas.getActionPattern(name,target);
                    PrettyCompare compare = new PrettyCompare(parsed_code,original_code);
                    if (compare.compare()==true) {
                        oper.setActionPattern(true);
                        oper.setCode(action);
                    } else {
                        oper.setActionPattern(false);
                        oper.setCode(parsed_code);
                    }
                }
            }
            
        }
    }
    
    private void parse_tasks(java_class entity,JavaClass parsed_class) {
        int task_number = parsed_class.getInnerClasses().length;
        for (int i=0; i<task_number; i++) {
            JavaClass p_task = parsed_class.getInnerClasses()[i];
            java_class task = new java_class();
            
            task.setName(p_task.getName());
            task.setStereotype("Task");
            task.setExtends(p_task.getSuperClass().getValue());
            
            parse_attributes(task,p_task);
            parse_methods(task,p_task);
            
            task.setParent(entity);
            entity.addInnerClass(task);
        }
    }
    
    private String getCodeFromSource(int start,String method_name) {
        StringBuffer buffer = new StringBuffer();
        int num = start;
        int i=0;
        boolean trovato = false;
        char c;
        String sub;
        int lung = method_name.length();
        
        
        // salta alla riga "start"
        while (num > 1 && i<my_source.length()) {
            c = my_source.charAt(i);
            i++;
            if (c=='\n')
                num--;
        }
        
        // cerca la fine del commento
        while (trovato == false && i<my_source.length()) {
            sub = my_source.substring(i,i+2);
            if (sub.equals("*/"))
                trovato = true;
            i++;
        }
        i++;
        
        // cerca il nome del metodo
        trovato = false;
        while (trovato == false && i<my_source.length()) {
            sub = my_source.substring(i,i+lung);
            if (sub.equals(method_name))
                trovato = true;
            i++;
        }
        i+=lung-1;
        
        // cerca la parentesi graffa
        trovato = false;
        while (trovato == false && i<my_source.length()) {
            c = my_source.charAt(i);
            if (c=='{')
                trovato = true;
            i++;
        }
        
        // piccola macchina a stati che cerca la fine
        int open = 0;
        trovato = false;
        while (trovato == false && i<my_source.length()) {
            c = my_source.charAt(i);
            if (c=='{') {
                open++;
                buffer.append(c);
            } else if (c=='}' && open ==0) {
                trovato = true;
            } else if (c=='}' && open > 0) {
                open--;
                buffer.append(c);
            } else {
                buffer.append(c);
            }
            i++;
        }
        
        //        System.out.println("for method "+method_name+" is");
        //        System.out.println(buffer.toString());
        
        return buffer.toString();
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

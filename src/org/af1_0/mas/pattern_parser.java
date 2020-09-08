/*
 * pattern_resolver.java
 *
 * Created on 29 ottobre 2002, 18.10
 */

package org.af1_0.mas;

import java.util.Iterator;
import java.io.StringWriter;
import java.io.StringReader;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;

import org.af1_0.repository.repository_designer;
import org.af1_0.pattern.pattern;
import org.af1_0.pattern.pattern_description;

import org.af1_0.entity.*;
import org.af1_0.mas.*;

import java.util.GregorianCalendar;
import org.af1_0.gui.xml.*;   //TEMP
/**
 *
 * @author  Luca
 */
public class pattern_parser {
    protected repository_designer repository;
    protected multi_agent_system model;
    
    /** Creates a new instance of pattern_resolve */
    public pattern_parser(multi_agent_system the_model) {
        model = the_model;
        repository_designer repository = model.getRepository();
    }
    
    public pattern getPattern(pattern_description description) {
        pattern the_pattern = null;
        
//        System.out.println("[high-level]");
//        System.out.println(description.getXml());

        // 1. effettua la trasformazione XSL del pattern
        String low_level = HighLevelTransform( description.getXml() );
        
//        System.out.println("[low-level]");
//        System.out.println(low_level);

        // 2. effettua il parsing del file XML low-level e ne cattura la struttura
        the_pattern = PatternParsing( low_level );
        the_pattern.setPatternDescription( description );
        
//        System.out.println("[PATTERN]");
//        System.out.println(the_pattern);

        return the_pattern;
    }
    
    /* questa classe effettua la trasformazione XSLT relativa alla identificazione della
     * piattaforma da usare: vengono attualizzati parametri come AgentShell o TaskShell
     * ed elementi come AgentSetup o TaskSetup. Inoltre vengono eliminati i vincoli relativi
     * ad altre piattaforme rispetto a quella di riferimento. Vedere il foglio di stile per
     * maggiori dettagli.
     */
    private String HighLevelTransform(String xml) {
        TransformerFactory tfactory=null;
        Transformer trasformer=null;
        
        StringReader source = new StringReader( xml );
        
        StringWriter low_level_pattern = new StringWriter();
        
        try {
            tfactory = TransformerFactory.newInstance();
            trasformer = tfactory.newTransformer( model.getPlatformTransformation() );
            trasformer.transform(new StreamSource(source), new StreamResult(low_level_pattern));
            
        } catch (javax.xml.transform.TransformerConfigurationException pce) {
            System.err.println("[HighLevelTransform(parser)] errore Parser: "+pce.getMessage());
        } catch (javax.xml.transform.TransformerException e) {
            System.err.println("[HighLevelTransform(parser)] errore di trasformazione");
        } catch (java.lang.IllegalArgumentException e) {
            System.err.println("[HighLevelTransform(parser)] errore di argomento illegale");
        } finally {
            return low_level_pattern.toString();
        }
    }
    
    private pattern PatternParsing(String xml) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        StringReader source = new StringReader( xml );
        Document document = null;
        
        factory.setValidating(false);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse( new InputSource(source) );
        } catch (org.xml.sax.SAXException sxe) {
            System.err.println("[HighLevelTransform(parser)] errore SAX: "+sxe.getMessage());
        } catch (javax.xml.parsers.ParserConfigurationException pce) {
            System.err.println("[HighLevelTransform(parser)] errore Parser");
        } finally {
            return PatternParsing(document);
        }
    }
    
    private pattern PatternParsing(Document doc) {
        pattern the_pattern = model.createNewPattern();
        entity_factory factory = new entity_factory(the_pattern);
        entity_merge merge = new entity_merge(the_pattern,true);
        
        // detect root node
        Node pattern_node = detectRootNode(doc);        
        if (pattern_node == null)
            return the_pattern;
        
        // detect first level node: agent, task or relation
        NodeList nodes = pattern_node.getChildNodes();
        for (int i=0; i< nodes.getLength(); i++) {
            Node nodo = (Node) nodes.item(i);

            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;
                if ( elemento.getTagName().equals("Agent") ) {
                    pattern agent_pattern = AgentParsing(elemento);
                    merge.mergeModel(agent_pattern);

                } else if ( elemento.getTagName().equals("Task") ) {
                    pattern task_pattern = TaskParsing(elemento);
                    merge.mergeModel(task_pattern);

                } else if ( elemento.getTagName().equals("Constraint") ) {
                    String target = elemento.getAttribute("target");
                    if (target.equals("model")) {
                        ModelConstraintParsing(elemento,the_pattern);
                    } else {
                        pattern agent_pattern = AgentParsing(elemento);
                        
                        entity_search search = new entity_search(agent_pattern);
                        entity_renaming rename = new entity_renaming(agent_pattern);
                        java_class owner_agent = search.searchAgent("owner-agent");
                        rename.renameAgent(owner_agent,target);
                        
                        //System.out.println("constraint");
                        //System.out.println(agent_pattern);
                        
                        merge.mergeModel(agent_pattern);

                    }
                }
            }
            
        }

        return the_pattern;
    }
    
    private void ModelConstraintParsing(Element element,pattern the_pattern) {
        entity_factory factory = new entity_factory(the_pattern);
        entity_merge merge = new entity_merge(the_pattern,true);

        NodeList nodes = element.getChildNodes();
        for (int i=0; i< nodes.getLength(); i++) {
            Node nodo = (Node) nodes.item(i);
      
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;
                if ( elemento.getTagName().equals("Agent") ) {
                    pattern agent_pattern = AgentParsing(elemento);
                    merge.mergeModel(agent_pattern);
                
                } else if ( elemento.getTagName().equals("Relation") ) {
                    relation rel = RelationParsing(elemento, the_pattern);
                    merge.mergeRelation(rel);
                }
            }
        }
    }

    private pattern AgentParsing(Element agent_element) {
        pattern the_pattern = null;
        String pattern_name = agent_element.getAttribute("extends");
        String agent_name = agent_element.getAttribute("name");
        
        // questa istruzione serve per i behaviour pattern
        if (agent_name==null || agent_name.equals(""))
            agent_name="owner-agent";
        
        java_class agent = null;
        
        if ( pattern_name != null && !pattern_name.equals("") ) {
            pattern_description patt = model.getRepository().queryComponentItem(pattern_name);
            
            if (patt != null) {
                pattern_parser inner_pattern_parser = new pattern_parser(model);
                the_pattern = inner_pattern_parser.getPattern(patt);
            
                // rinomina l'agente presente
                entity_search search = new entity_search(the_pattern);                
                entity_renaming renaming = new entity_renaming(the_pattern);
                
                agent = search.searchAgent( patt.getName() );
                renaming.renameAgent(agent,agent_name);
            }            
        } else {
            the_pattern = model.createNewPattern();
            entity_factory factory = new entity_factory(the_pattern);
            agent = factory.createAgent( agent_name );            
        }
        
        entity_merge merge = new entity_merge(the_pattern,true);
        
        NodeList nodes = agent_element.getChildNodes();
        for (int i=0; i< nodes.getLength(); i++) {
            Node nodo = (Node) nodes.item(i);

            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;
                if ( elemento.getTagName().equals("Attribute") ) {
                    java_attribute attribute = AttributeParsing(elemento);
                    merge.mergeAttribute(agent,attribute);
                } else if ( elemento.getTagName().equals("Method") ) {
                    java_method method = MethodParsing(elemento);
                    merge.mergeMethod(agent,method);
                } else if ( elemento.getTagName().equals("Task") ) {
                    pattern task = TaskParsing(elemento);
                    
                    entity_search search = new entity_search(task);
                    entity_renaming rename = new entity_renaming(task);
                    java_class owner_agent = search.searchAgent("owner-agent");

                    rename.renameAgent(owner_agent,agent_name);
                    
                    merge.mergeModel(task);
                } else if ( elemento.getTagName().equals("Visibility") ) {
                    String text = TextParsing(elemento);
                    agent.setVisibility( text );
                } else if ( elemento.getTagName().equals("Modifier") ) {
                    String text = TextParsing(elemento);
                    agent.addModifier( text );
                } else if ( elemento.getTagName().equals("Extends") ) {
                    String text = TextParsing(elemento);
                    agent.setExtends( text );
                }
            }
        }
        
        return the_pattern;
    }

    private pattern TaskParsing(Element task_element) {
        pattern the_pattern = null;
        String pattern_name = task_element.getAttribute("extends");
        String task_name = task_element.getAttribute("name");
                
        java_class agent = null;
        java_class task = null;
        
        entity_factory factory = null;
        
        if ( pattern_name != null && !pattern_name.equals("") ) {
            pattern_description patt = model.getRepository().queryBehaviourItem(pattern_name);
            
            if (patt != null) {
                pattern_parser inner_pattern_parser = new pattern_parser(model);
                the_pattern = inner_pattern_parser.getPattern(patt);
            
                // rinomina l'agente presente
                factory = new entity_factory(the_pattern);
                entity_search search = new entity_search(the_pattern);                
                entity_renaming renaming = new entity_renaming(the_pattern);
                
                agent = search.searchAgent( "owner-agent" );
                task = search.searchTask( agent, pattern_name );
                if (task != null) {
                    renaming.renameTask(task,task_name);
                }
           }            
        } else {
            the_pattern = model.createNewPattern();
            factory = new entity_factory(the_pattern);
            agent = factory.createAgent( "owner-agent" );
        }
        
        if (task==null)
            task = factory.createTask( agent, task_name );
        
        entity_merge merge = new entity_merge(the_pattern,true);
        
        NodeList nodes = task_element.getChildNodes();
        for (int i=0; i< nodes.getLength(); i++) {
            Node nodo = (Node) nodes.item(i);

            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;
                if ( elemento.getTagName().equals("Attribute") ) {
                    java_attribute attribute = AttributeParsing(elemento);
                    merge.mergeAttribute(task,attribute);
                } else if ( elemento.getTagName().equals("Method") ) {
                    java_method method = MethodParsing(elemento);
                    merge.mergeMethod(task,method);
                } else if ( elemento.getTagName().equals("Visibility") ) {
                    String text = TextParsing(elemento);
                    task.setVisibility( text );
                } else if ( elemento.getTagName().equals("Modifier") ) {
                    String text = TextParsing(elemento);
                    task.addModifier( text );
                } else if ( elemento.getTagName().equals("Extends") ) {
                    String text = TextParsing(elemento);
                    task.setExtends( text );
                }
            }
        }        
                
        return the_pattern;
    }


    private relation RelationParsing(Element relation_element,pattern the_pattern) {
        relation rel = new relation();
        
        String label = relation_element.getAttribute("label");
        String start = relation_element.getAttribute("start");
        String end = relation_element.getAttribute("end");
        
        // individua i metodi presenti nel pattern e ne setta il riferimento        
        java_method meth1 = (java_method) the_pattern.getStatic().get(start);
        java_method meth2 = (java_method) the_pattern.getStatic().get(end);
        
        // la data (espressa in millisecondi) serve per differenziare due relation
        // relative allo stesso pattern che vengono incluse in un modello
        // l'errore era presente nella release 1.0.2
        GregorianCalendar data = new GregorianCalendar();
        
        rel.setName( label+data.get(GregorianCalendar.MILLISECOND) );
        rel.setStart( meth1 );
        rel.setEnd( meth2 );

       
        return rel;
    }

    private java_attribute AttributeParsing(Element attribute_element) {
        java_attribute attribute = new java_attribute();
        
        String name = attribute_element.getAttribute("name");
        String type = attribute_element.getAttribute("type");    
        
        attribute.setName( name );
        attribute.setType( type );

        NodeList nodes = attribute_element.getChildNodes();
        for (int i=0; i< nodes.getLength(); i++) {
            Node nodo = (Node) nodes.item(i);

            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;
                if ( elemento.getTagName().equals("Value") ) {
                    String text = TextParsing(elemento);
                    attribute.setStartValue( text );
                } else if ( elemento.getTagName().equals("Visibility") ) {
                    String text = TextParsing(elemento);
                    attribute.setVisibility( text );
                } else if ( elemento.getTagName().equals("Modifier") ) {
                    String text = TextParsing(elemento);
                    attribute.addModifier( text );
                }
            }
        }
        
        return attribute;
    }

    private java_method MethodParsing(Element method_element) {
        java_method method = new java_method();
        
        String name = method_element.getAttribute("name");
        String type = method_element.getAttribute("type");      

        method.setName( name );
        method.setType( type );
        
        NodeList nodes = method_element.getChildNodes();
        for (int i=0; i< nodes.getLength(); i++) {
            Node nodo = (Node) nodes.item(i);

            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) nodo;
                if ( elemento.getTagName().equals("Code") ) {
                    // qui va fatta la sostituzione del tag con l'action pattern
                    String text = TextParsing(elemento);
                    method.setCode( text );
                    method.setActionPattern(true);
                } else if ( elemento.getTagName().equals("Visibility") ) {
                    String text = TextParsing(elemento);
                    method.setVisibility( text );
                } else if ( elemento.getTagName().equals("Modifier") ) {
                    String text = TextParsing(elemento);
                    method.addModifier( text );
                } else if ( elemento.getTagName().equals("Argoment") ) {
                    argoment arg = new argoment();
                    arg.setName( elemento.getAttribute("name") );
                    arg.setType( elemento.getAttribute("type") );
                    method.addArgoment( arg );
                } else if ( elemento.getTagName().equals("Throws") ) {
                    String text = TextParsing(elemento);
                    method.addException( text );
                }
            }
        }
        
        return method;
    }
    
    private String TextParsing(Element elemento) {
        NodeList nodes = elemento.getChildNodes();
        String result = new String();
        
        for (int i=0; i< nodes.getLength(); i++) {
            Node nodo = (Node) nodes.item(i);

            if (nodo.getNodeType() == Node.TEXT_NODE)
                result = result + nodo.getNodeValue();
                
        }
        
        return result;
    }

    private Node detectRootNode(Document doc) {
        Node root = null;
        
        if (doc != null) {
            NodeList nodes = doc.getChildNodes();
            for (int i=0; i< nodes.getLength(); i++) {
                Node nodo = (Node) nodes.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    if ( elemento.getTagName().equals("Pattern") )
                        root = nodo;                   
                }
            }
        }
        
        return root;
    }
        
}

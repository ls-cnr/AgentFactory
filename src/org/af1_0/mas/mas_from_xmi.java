/*
 * mas_from_xmi.java
 *
 * Created on 25 ottobre 2002, 17.58
 */

package org.af1_0.mas;

import org.af1_0.pattern.pattern;
import org.af1_0.entity.*;


import java.io.StringWriter;
import java.io.StringReader;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;

import java.util.Hashtable;

/**
 *
 * @author  Luca
 */
public class mas_from_xmi extends multi_agent_system {
	private final String uml_uri = "//org.omg/UML/1.3";
	private Hashtable datatype;
	private Hashtable actions;
	
	/** Creates a new instance of mas_from_xmi */
	public mas_from_xmi(String the_platform,String xmi_model) {
		super(the_platform);
		
		datatype = new Hashtable();
		
		// trasforma in un formato intermedio
		//String intermediate = IntermediateTransform( xmi_model );
		
		//System.out.println("[model]");
		//System.out.println(intermediate);
		
		// parse from intermediate
		//ModelParsing(intermediate);
		ModelParsing(xmi_model);
	}
	
	private String IntermediateTransform(String xmi) {
		TransformerFactory tfactory=null;
		Transformer trasformer=null;
		
		StringReader source = new StringReader( xmi );
		
		StringWriter out = new StringWriter();
		
		try {
			tfactory = TransformerFactory.newInstance();
			trasformer = tfactory.newTransformer( getIntermediateTransformation() );
			trasformer.transform(new StreamSource(source), new StreamResult(out));
		} catch (javax.xml.transform.TransformerConfigurationException pce) {
			System.out.println("1[IntermediateTransform(parser)] errore Parser");
			//pce.printStackTrace();
		} catch (javax.xml.transform.TransformerException e) {
			System.err.println("2[IntermediateTransform(parser)] errore di trasformazione "+e.getLocator().getLineNumber());
			System.err.println("line: "+e.getLocator().getLineNumber());
		} catch (java.lang.IllegalArgumentException e) {
			System.err.println("[IntermediateTransform(parser)] errore di argomento illegale");
		} finally {
			return out.toString();
		}
	}
	
	private void ModelParsing(String xml) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		StringReader source = new StringReader( xml );
		Document document = null;
		
		factory.setValidating(false);
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse( new InputSource(source) );
		} catch (org.xml.sax.SAXException sxe) {
			System.err.println("[ModelTransform(parser)] errore SAX: "+sxe.getMessage());
		} catch (javax.xml.parsers.ParserConfigurationException pce) {
			System.err.println("[ModelTransform(parser)] errore Parser");
		} catch (java.io.IOException ex) {
			ex.printStackTrace();
		} finally {
			ModelParsing(document);
		}
	}
	
	private void ModelParsing(Document doc) {
		entity_factory factory = new entity_factory(this);
		entity_merge merge = new entity_merge(this,true);
		
		// detect root node
		Element xmi_node = detectRootElement(doc);
		if (xmi_node == null)
			return;
		
		Element xmi_content_node = getFirstChild(xmi_node,"XMI.content");
		if (xmi_content_node == null)
			return;
		
		Element uml_model_node = getFirstChild(xmi_content_node,"UML:Model");
		if (uml_model_node == null)
			return;
		
		Element uml_owned_node = getFirstChild(uml_model_node,"UML:Namespace.ownedElement");
		if (uml_owned_node == null)
			return;
		
		// legge prima i datatype
		NodeList nodes = uml_owned_node.getChildNodes();
		for (int i=0; i< nodes.getLength(); i++) {
			Node nodo = (Node) nodes.item(i);
			
			if (nodo.getNodeType() == Node.ELEMENT_NODE) {
				Element elemento = (Element) nodo;
				if ( elemento.getTagName().equals("UML:DataType") ) {
					String name = elemento.getAttribute("xmi.id");
					String type = elemento.getAttribute("name");
					datatype.put(name,type);
				}
			}
		}
		
		// poi gli agenti
		nodes = uml_owned_node.getChildNodes();
		for (int i=0; i< nodes.getLength(); i++) {
			Node nodo = (Node) nodes.item(i);
			
			if (nodo.getNodeType() == Node.ELEMENT_NODE) {
				Element elemento = (Element) nodo;
				if ( elemento.getTagName().equals("UML:Package") ) {
					java_class agent = AgentParsing(elemento);
					merge.mergeAgent(agent);
				}
			}
		}
	}
	
	private java_class AgentParsing(Element agent_element) {
		String agent_name = agent_element.getAttribute("name");
		java_class agent = null;
		
		Element xmi_ownedElement_node = getFirstChild(agent_element,"UML:Namespace.ownedElement");
		if (xmi_ownedElement_node == null)
			return agent;
		
		// legge prima la classe agent
		NodeList nodes = xmi_ownedElement_node.getChildNodes();
		for (int i=0; i< nodes.getLength(); i++) {
			Node nodo = (Node) nodes.item(i);
			
			if (nodo.getNodeType() == Node.ELEMENT_NODE) {
				Element elemento = (Element) nodo;
				if ( elemento.getTagName().equals("UML:Class") ) {
					String name = elemento.getAttribute("name");
					if (name.equals(agent_name))
						agent = ClassParsing(elemento);
				}
			}
		}
		
		// legge le restanti classi agent
		nodes = xmi_ownedElement_node.getChildNodes();
		for (int i=0; i< nodes.getLength(); i++) {
			Node nodo = (Node) nodes.item(i);
			
			if (nodo.getNodeType() == Node.ELEMENT_NODE) {
				Element elemento = (Element) nodo;
				if ( elemento.getTagName().equals("UML:Class") ) {
					String name = elemento.getAttribute("name");
					if (!name.equals(agent_name)) {
						java_class task = ClassParsing(elemento);
						agent.addInnerClass(task);
					}
				}
			}
		}

		return agent;
	}
	
	private java_class ClassParsing(Element class_element) {
		actions = new Hashtable();
		
		java_class classe = new java_class();
		
		String name = class_element.getAttribute("name");
		classe.setName( name );
		
		Element xmi_feature_node = getFirstChild(class_element,"UML:Classifier.feature");
		if (xmi_feature_node == null)
			return classe;
		
		NodeList nodes = xmi_feature_node.getChildNodes();
		for (int i=0; i< nodes.getLength(); i++) {
			Node nodo = (Node) nodes.item(i);
			
			if (nodo.getNodeType() == Node.ELEMENT_NODE) {
				Element elemento = (Element) nodo;
				if ( elemento.getTagName().equals("UML:Method") ) {
					String id = elemento.getAttribute("xmi.id");
					Element specification = getFirstChild(elemento,"UML:Method.body");
					if (specification != null) {
						Element core = getFirstChild(specification,"UML:ProcedureExpression");
						if (core != null) {
							String body = core.getAttribute("body");
							actions.put(id,body);
						}
					}
				}
			}
		}

		nodes = xmi_feature_node.getChildNodes();
		for (int i=0; i< nodes.getLength(); i++) {
			Node nodo = (Node) nodes.item(i);
			
			if (nodo.getNodeType() == Node.ELEMENT_NODE) {
				Element elemento = (Element) nodo;
				if ( elemento.getTagName().equals("UML:Attribute") ) {
					java_attribute attribute = AttributeParsing(elemento);
					classe.addAttribute(attribute);
				} else if ( elemento.getTagName().equals("UML:Operation") ) {
					java_method method = MethodParsing(elemento);
					classe.addMethod(method);
				}
			}
		}
		
		return classe;
	}
	
	private java_attribute AttributeParsing(Element attribute_element) {
		java_attribute attribute = new java_attribute();
		
		String name = attribute_element.getAttribute("name");
		attribute.setName( name );
		
		Element xmi_type_node = getFirstChild(attribute_element,"UML:StructuralFeature.type");
		if (xmi_type_node != null) {
			Element xmi_core_node = getFirstChild(xmi_type_node,"Foundation.Core.Classifier");
			if (xmi_core_node != null) {
				String typeref = xmi_core_node.getAttribute("xmi.idref");
				String type = (String) datatype.get(typeref);
				attribute.setType( type );
			}
		}
		
		Element xmi_value_node = getFirstChild(attribute_element,"UML:Attribute.initialValue");
		if (xmi_value_node != null) {
			Element xmi_expression_node = getFirstChild(xmi_value_node,"UML:Expression");
			if (xmi_expression_node != null) {
				String value = xmi_expression_node.getAttribute("body");
				attribute.setStartValue( value );
			}
		}
		
		return attribute;
	}
	
	private java_method MethodParsing(Element method_element) {
		java_method method = new java_method();
		
		String name = method_element.getAttribute("name");
		method.setName( name );
		method.setType( "void" );	// valore di default
		
		Element xmi_params_node = getFirstChild(method_element,"UML:BehavioralFeature.parameter");
		if (xmi_params_node != null) {
			
			NodeList nodes = xmi_params_node.getChildNodes();
			for (int i=0; i< nodes.getLength(); i++) {
				Node nodo = (Node) nodes.item(i);

				if (nodo.getNodeType() == Node.ELEMENT_NODE) {
					Element elemento = (Element) nodo;
					if ( elemento.getTagName().equals("UML:Parameter") ) {
						
						Element xmi_type_node = getFirstChild(elemento,"UML:Parameter.type");
						if (xmi_type_node != null) {
							Element xmi_classifier_node = getFirstChild(elemento,"Foundation.Core.Classifier");
							if (xmi_classifier_node != null) {
								String typeref = xmi_classifier_node.getAttribute("xmi.idref");
								String type = (String) datatype.get(typeref); 
						
								String kind = elemento.getAttribute("kind");
								if (kind.equals("return")) {
									method.setType(type);
								} else {
									String arg_name = elemento.getAttribute("name");
									argoment arg = new argoment();
									arg.setType(type);
									arg.setName(arg_name);
									method.addArgoment( arg );
								}
							}
						}
					} else if ( elemento.getTagName().equals("UML:Operation.method") ) {
						Element core = getFirstChild(elemento,"Foundation.Core.Method");
						if (core != null) {
							String idref = core.getAttribute("xmi.idref");
							String action_pattern = (String) actions.get(idref);
							method.setCode(action_pattern);
						}
					}
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
	
	private Element detectRootElement(Document doc) {
		Element root = null;
		
		if (doc != null) {
			NodeList nodes = doc.getChildNodes();
			for (int i=0; i< nodes.getLength(); i++) {
				Node nodo = (Node) nodes.item(i);
				
				if (nodo.getNodeType() == Node.ELEMENT_NODE) {
					root = (Element) nodo;
				}
			}
		}
		
		return root;
	}
	
	private Element getFirstChild(Element parent, String child_name) {
		Element child = null;
		boolean trovato = false;
		
		if (parent != null) {
			NodeList nodes = parent.getChildNodes();
			for (int i=0; i< nodes.getLength(); i++) {
				Node nodo = (Node) nodes.item(i);
				
				if (nodo.getNodeType() == Node.ELEMENT_NODE && trovato==false) {
					Element elemento = (Element) nodo;
					if (elemento.getTagName().equals(child_name)) {
						child = elemento;
						trovato = true;
					}
				}
			}
		}
		
		return child;
	}
	
}

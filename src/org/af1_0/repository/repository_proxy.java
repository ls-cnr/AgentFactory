/*
 * repository_proxy.java
 *
 * Created on 26 ottobre 2002, 10.45
 */

package org.af1_0.repository;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Vector;

import org.af1_0.pattern.pattern_description;

/**
 *
 * @author  Luca
 */
public class repository_proxy implements repository_designer {
	String remote = null;
	
	/** Creates a new instance of repository_proxy */
	public repository_proxy(String remote_server_id) {
		remote = remote_server_id;
	}
	
	public String getUrl() {
		return remote;
	}
	
	public boolean test() {
		boolean result = false;
		
		try {
			URL url = new URL("http://"+remote+":8080/org.af1_0/Rep?COMMAND=ping");
			String resp = (String) sendRequest(url);
			if (resp != null && resp.equals("pong") )
				result = true;
		} catch (IOException ex) {
		}
		
		return result;
	}
	
	public Vector queryServiceList() {
		Vector list = new Vector();
		
		try {
			URL url = new URL("http://"+remote+":8080/org.af1_0/Rep?COMMAND=ServiceList");
			list = (Vector) sendRequest(url);
		} catch (IOException ex) {
		}
		
		return list;
	}
	
	public pattern_description queryServiceItem(String name) {
		pattern_description the_pattern = new pattern_description("","","","");
		
		try {
			URL url = new URL("http://"+remote+":8080/org.af1_0/Rep?COMMAND=ServicePattern&NAME="+name);
			the_pattern = (pattern_description) sendRequest(url);
		} catch (IOException ex) {
		}
		
		return the_pattern;
	}
	
	public Vector queryComponentList() {
		Vector list = new Vector();
		
		try {
			URL url = new URL("http://"+remote+":8080/org.af1_0/Rep?COMMAND=ComponentList");
			list = (Vector) sendRequest(url);
		} catch (IOException ex) {
		}
		
		return list;
	}
	
	public pattern_description queryComponentItem(String name) {
		pattern_description the_pattern = new pattern_description("","","","");
		
		try {
			URL url = new URL("http://"+remote+":8080/org.af1_0/Rep?COMMAND=ComponentPattern&NAME="+name);
			the_pattern = (pattern_description) sendRequest(url);
		} catch (IOException ex) {
		}
		
		return the_pattern;
	}
	
	public Vector queryBehaviourList() {
		Vector list = new Vector();
		
		try {
			URL url = new URL("http://"+remote+":8080/org.af1_0/Rep?COMMAND=BehaviourList");
			list = (Vector) sendRequest(url);
		} catch (IOException ex) {
		}
		
		return list;
	}
	public pattern_description queryBehaviourItem(String name) {
		pattern_description the_pattern = new pattern_description("","","","");
		
		try {
			URL url = new URL("http://"+remote+":8080/org.af1_0/Rep?COMMAND=BehaviourPattern&NAME="+name);
			the_pattern = (pattern_description) sendRequest(url);
		} catch (IOException ex) {
		}
		
		return the_pattern;
	}
	public String getActionPattern(String name, String parent, String platform) {
		String action = null;
		try {
			URL url = new URL("http://"+remote+":8080/org.af1_0/Rep?COMMAND=ActionPattern&NAME="+name+"&PARENT="+parent+"&PLATFORM="+platform);
			action = (String) sendRequest(url);
		} catch (IOException ex) {
		}
		
		if (action == null)
			return "// no action pattern";
		
		return action;
	}
	
	
	private Object sendRequest(URL url) {
		URLConnection connection = null;
		Object object = null;
		
		try {
			connection = url.openConnection();
		} catch (IOException ex) {
			System.out.println("URL error "+ex);
		}
		
		try {
			ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
			object = input.readObject();
		} catch (IOException ex) {
			System.out.println("Input error "+ex);
		} catch (ClassNotFoundException ex) {
			System.out.println("serialization error "+ex);
		}
		
		return object;
	}
	
	public boolean isLocale() {
		return false;
	}
	
	public boolean isRemote() {
		return true;
	}
	
}

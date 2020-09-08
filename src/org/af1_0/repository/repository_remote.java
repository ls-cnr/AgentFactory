/*
 * repository_locale.java
 *
 * Created on 26 ottobre 2002, 11.42
 */

package org.af1_0.repository;

import java.net.*;

import java.util.Vector;
import java.sql.*;

import org.af1_0.pattern.pattern_description;

/** Questa classe fornisce l'accesso al repository di pattern
 * accedendo in modo diretto al database JDBC-ODBC.
 * Pertanto si presuppone che nella macchina in cui gira tale processo
 * sia installato un DNS con nome "pattern_repository_2"
 * @author Luca
 */
public class repository_remote implements repository_designer {
	protected static final String ServiceList_query = "SELECT name FROM ServicePattern ORDER BY name";
	protected static final String ComponentList_query = "SELECT name FROM ComponentPattern ORDER BY name";
	protected static final String BehaviourList_query = "SELECT name FROM BehaviourPattern ORDER BY name";
	protected static final String patt_items = "name, classification, intent, motivation, preconditions, postconditions, participants, availability, relatedpatterns, xml";
	private String error;
	
	/** Creates a new instance of repository_locale */
	public repository_remote() {
		error = null;
	}
	
	public String getError() {
		return error;
	}
	
	public Vector queryServiceList() {
		database_access repository = new database_access();
		Vector risposte = new Vector();
		
		try {
			Statement stmt = repository.openConnection();
			ResultSet rs = stmt.executeQuery(ServiceList_query);
			
			while (rs.next()) {
				String name = rs.getString("name");
				risposte.add(name);
			}
		} catch (SQLException e) {
			System.out.println("[tool.db.dbQuery]"+"queryServiceList non riuscito"+e.getMessage());
			error = new String("queryServiceItem non riuscito"+e.getMessage());
		} finally {
			repository.closeConnection();
		}
		
		return risposte;
	}
	
	public pattern_description queryServiceItem(String name) {
		String Service_query = "SELECT "+patt_items+" FROM ServicePattern WHERE name LIKE '"+name+"' ";
		database_access repository = new database_access();
		pattern_description pattern = null;
		try {
			Statement stmt = repository.openConnection();
			ResultSet rs = stmt.executeQuery(Service_query);
			
			pattern = new pattern_description(name);
			
			while (rs.next()) {
				pattern.setClassification(rs.getString("classification"));
				pattern.setIntent(rs.getString("intent"));
				pattern.setMotivation(rs.getString("motivation"));
				pattern.setPreCondition(rs.getString("preconditions"));
				pattern.setPostCondition(rs.getString("postconditions"));
				pattern.setParticipants(rs.getString("participants"));
				pattern.setAvailability(rs.getString("availability"));
				pattern.setRelatedPatterns(rs.getString("relatedpatterns"));
				pattern.setXml(rs.getString("xml"));
				//component = new pattern_description(name,descr,scena,xml);
			}
		} catch (SQLException e) {
			System.out.println("[tool.db.dbQuery]"+"queryServiceItem non riuscito"+e.getMessage());
			error = new String("queryServiceItem non riuscito"+e.getMessage());
		} finally {
			repository.closeConnection();
		}
		
		// preleva l'immagine corrispondente
		try {
			URL structure_url = new URL("http://mozart.csai.unipa.it/af/repository/images/service_"+name+"_structure.gif");
			URL collab_url = new URL("http://mozart.csai.unipa.it/af/repository/images/service_"+name+"_collaboration.gif");
			pattern.setStructure( structure_url );
			pattern.setCollaboration( collab_url );
		} catch(java.net.MalformedURLException ex) {
		}
		return pattern;
	}
	public Vector queryComponentList() {
		database_access repository = new database_access();
		Vector risposte = new Vector();
		
		try {
			Statement stmt = repository.openConnection();
			ResultSet rs = stmt.executeQuery(ComponentList_query);
			
			while (rs.next()) {
				String name = rs.getString("name");
				risposte.add(name);
			}
		} catch (SQLException e) {
			System.out.println("[tool.db.dbQuery]"+"queryComponentList non riuscito"+e.getMessage());
			error = new String("queryComponentList non riuscito"+e.getMessage());
		} finally {
			repository.closeConnection();
		}
		
		return risposte;
	}
	
	public pattern_description queryComponentItem(String name) {
		String Component_query = "SELECT "+patt_items+" FROM ComponentPattern WHERE name LIKE '"+name+"' ";
		database_access repository = new database_access();
		pattern_description pattern = null;
		try {
			Statement stmt = repository.openConnection();
			//System.out.println("QUERY: "+Component_query);
			ResultSet rs = stmt.executeQuery(Component_query);
			
			pattern = new pattern_description(name);
			
			while (rs.next()) {
				pattern.setClassification(rs.getString("classification"));
				pattern.setIntent(rs.getString("intent"));
				pattern.setMotivation(rs.getString("motivation"));
				pattern.setPreCondition(rs.getString("preconditions"));
				pattern.setPostCondition(rs.getString("postconditions"));
				pattern.setParticipants(rs.getString("participants"));
				pattern.setAvailability(rs.getString("availability"));
				pattern.setRelatedPatterns(rs.getString("relatedpatterns"));
				pattern.setXml(rs.getString("xml"));
			}
		} catch (SQLException e) {
			System.out.println("[tool.db.dbQuery]"+"queryComponentItem non riuscito"+e.getMessage());
			e.printStackTrace();
			error = new String("queryComponentItem non riuscito"+e.getMessage());
		} finally {
			repository.closeConnection();
		}
		
		// preleva l'immagine corrispondente
		try {
			URL structure_url = new URL("http://mozart.csai.unipa.it/af/repository/images/component_"+name+"_structure.gif");
			URL collab_url = new URL("http://mozart.csai.unipa.it/af/repository/images/component_"+name+"_collaboration.gif");
			pattern.setStructure( structure_url );
			pattern.setCollaboration( collab_url );
		} catch(java.net.MalformedURLException ex) {
		}
		
		return pattern;
	}
	
	public Vector queryBehaviourList() {
		database_access repository = new database_access();
		Vector risposte = new Vector();
		
		try {
			Statement stmt = repository.openConnection();
			ResultSet rs = stmt.executeQuery(BehaviourList_query);
			
			while (rs.next()) {
				String name = rs.getString("name");
				risposte.add(name);
			}
		} catch (SQLException e) {
			System.out.println("[tool.db.dbQuery]"+"queryBehaviourList non riuscito"+e.getMessage());
			e.printStackTrace();
		} finally {
			repository.closeConnection();
		}
		
		return risposte;
	}
	
	public pattern_description queryBehaviourItem(String name) {
		String Component_query = "SELECT "+patt_items+" FROM BehaviourPattern WHERE name LIKE '"+name+"' ";
		database_access repository = new database_access();
		pattern_description pattern = null;
		try {
			Statement stmt = repository.openConnection();
			ResultSet rs = stmt.executeQuery(Component_query);
			
			pattern = new pattern_description(name);
			
			while (rs.next()) {
				pattern.setClassification(rs.getString("classification"));
				pattern.setIntent(rs.getString("intent"));
				pattern.setMotivation(rs.getString("motivation"));
				pattern.setPreCondition(rs.getString("preconditions"));
				pattern.setPostCondition(rs.getString("postconditions"));
				pattern.setParticipants(rs.getString("participants"));
				pattern.setAvailability(rs.getString("availability"));
				pattern.setRelatedPatterns(rs.getString("relatedpatterns"));
				pattern.setXml(rs.getString("xml"));
			}
		} catch (SQLException e) {
			System.out.println("[tool.db.dbQuery]"+"queryBehaviourItem non riuscito"+e.getMessage());
			e.printStackTrace();
		} finally {
			repository.closeConnection();
		}
		
		// preleva l'immagine corrispondente
		try {
			URL structure_url = new URL("http://mozart.csai.unipa.it/af/repository/images/behaviour_"+name+"_structure.gif");
			URL collab_url = new URL("http://mozart.csai.unipa.it/af/repository/images/behaviour_"+name+"_collaboration.gif");
			pattern.setStructure( structure_url );
			pattern.setCollaboration( collab_url );
		} catch(java.net.MalformedURLException ex) {
		}
		
		return pattern;
	}
	
	public String getActionPattern(String name,String parent, String platform) {
		String Component_query = "SELECT name,parent,FIPAOS_code,Jade_code FROM ActionPattern ";
		String Component_filter = "WHERE name LIKE '"+name+"' AND parent LIKE '"+parent+"'";
		
		database_access repository = new database_access();
		String action_pattern = null;
		try {
			Statement stmt = repository.openConnection();
			ResultSet rs = stmt.executeQuery(Component_query+Component_filter);
			
			while (rs.next()) {
				String FIPAOS = rs.getString("FIPAOS_code");
				String Jade = rs.getString("Jade_code");
				
				if (platform.equals("FIPAOS"))
					action_pattern = FIPAOS;
				else
					action_pattern = Jade;
			}
		} catch (SQLException e) {
			System.out.println("[tool.db.dbQuery]"+"queryBehaviourItem non riuscito"+e.getMessage());
			e.printStackTrace();
		} finally {
			repository.closeConnection();
		}
		
		if (action_pattern == null)
			return "// no action pattern for this method";
		
		return action_pattern;
	}
	
	public boolean isLocale() {
		return true;
	}
	
	public boolean isRemote() {
		return false;
	}
	
/*    public StreamSource getResource(String resource) {
		JarURLConnection jarConnection = null;
		java.io.InputStream stream = null;
 
		//System.out.println(resource);
		//System.out.println("org.af1_0/"+resource);
		try {
			URL resource_url = getClass().getClassLoader().getResource("org.af1_0/"+resource);
			//System.out.println(resource_url);
			jarConnection = (JarURLConnection)resource_url.openConnection();
			stream = jarConnection.getInputStream();
		} catch (java.io.IOException ex) {
			ex.printStackTrace();
		}
		return new StreamSource( stream );
	}*/
	
}

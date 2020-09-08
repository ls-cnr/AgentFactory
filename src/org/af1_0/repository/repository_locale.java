/*
 * repository_locale.java
 *
 * Created on 26 ottobre 2002, 11.42
 */

package org.af1_0.repository;

import java.net.*;

import javax.xml.transform.stream.StreamSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.*;

import java.util.Vector;
import java.sql.*;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.af1_0.pattern.pattern_description;

/** Questa classe fornisce l'accesso al repository di pattern
 * accedendo in modo diretto al database JDBC-ODBC.
 * Pertanto si presuppone che nella macchina in cui gira tale processo
 * sia installato un DNS con nome "pattern_repository_2"
 * @author Luca
 */
public class repository_locale implements repository_designer {
	protected static final String ServiceList_query = "SELECT name FROM ServicePattern ORDER BY name";
	protected static final String ComponentList_query = "SELECT name FROM ComponentPattern ORDER BY name";
	protected static final String BehaviourList_query = "SELECT name FROM BehaviourPattern ORDER BY name";
	protected static final String patt_items = "name, classification, intent, motivation, preconditions, postconditions, participants, availability, relatedpatterns, xml";
	private String error;
	
	/** Creates a new instance of repository_locale */
	public repository_locale() {
		error = null;
	}
	
	public String getError() {
		return error;
	}
	
	public Vector queryServiceList() {
		
/*		String service_file = "ptk_resource/repository/ServicePattern.xls";
        URL resource_url = getClass().getClassLoader().getResource(service_file);
        File file = new File(resource_url.getFile());
*/        
		Vector risposte = new Vector();
		risposte = queryList("ServicePattern.xls");
		
		return risposte;
		
//		try {
//			Workbook workbook = Workbook.getWorkbook(file);
//			Sheet sheet = workbook.getSheet(0);
//			
//			int riga = 1;
//			String cell_value;
//			Cell cell;
//			boolean continua = true;
//			int numero_righe = sheet.getRows();
//
//		
//			while (riga<numero_righe) {
//				cell = sheet.getCell(0, riga);
//				cell_value = cell.getContents();
//				if (cell_value != null && cell_value.length() > 0) {
//					risposte.add(cell_value);
//					riga++;
//				} else {
//					continua = false;
//				}
//			}
//			
//			workbook.close();
//		} catch (BiffException e) {
//			System.out.println("BiffException");
//			e.printStackTrace();
//		} catch (IOException e) {
//			System.out.println("IOException");
//			e.printStackTrace();
//		}
//		
//		return risposte;
	}
	
	public pattern_description queryServiceItem(String name) {
/*		String service_file = "ptk_resource/repository/ServicePattern.xls";
        URL resource_url = getClass().getClassLoader().getResource(service_file);
        File file = new File(resource_url.getFile());
*/ 
		pattern_description pattern = null;
		pattern = queryItem("ServicePattern.xls",name);
		getItemImages(pattern,"service",name);
		return pattern;

//		try {
//			Workbook workbook = Workbook.getWorkbook(file);
//			Sheet sheet = workbook.getSheet(0);
//			
//			int riga = 1;
//			String cell_value="";
//			Cell cell;
//			int numero_righe = sheet.getRows();
//	
//			while (pattern==null && riga<numero_righe) {
//				cell = sheet.getCell(0, riga);
//				cell_value = cell.getContents();
//				if (cell_value != null && cell_value.equals(name)) {
//					pattern = new pattern_description(name);
//					pattern.setClassification(sheet.getCell(1,riga).getContents());
//					pattern.setIntent(sheet.getCell(2,riga).getContents());
//					pattern.setMotivation(sheet.getCell(3,riga).getContents());
//					pattern.setPreCondition(sheet.getCell(4,riga).getContents());
//					pattern.setPostCondition(sheet.getCell(5,riga).getContents());
//					pattern.setParticipants(sheet.getCell(6,riga).getContents());
//					pattern.setAvailability(sheet.getCell(7,riga).getContents());
//					pattern.setRelatedPatterns(sheet.getCell(8,riga).getContents());
//					
//				}
//				riga++;
//			}
//			
//			workbook.close();
//		} catch (BiffException e) {
//			System.out.println("BiffException");
//			e.printStackTrace();
//		} catch (IOException e) {
//			System.out.println("IOException");
//			e.printStackTrace();
//		}
//		
//				
//		// preleva l'immagine corrispondente
//		URL structure_url = getClass().getResource("/ptk_resource/images/service_"+name+"_structure.gif");
//		URL collab_url = getClass().getResource("/ptk_resource/images/service_"+name+"_collaboration.gif");
//		URL xml_url = getClass().getResource("/ptk_resource/repository/xml/"+name+".xml");
//		pattern.setStructure( structure_url );
//		pattern.setCollaboration( collab_url );
//		
//		try {
//			pattern.setXml(readFileAsString(xml_url.getFile()));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return pattern;
	}
	
	public Vector queryComponentList() {
		Vector risposte = new Vector();
		risposte = queryList("ComponentPattern.xls");
		
		return risposte;
	}
	
	public pattern_description queryComponentItem(String name) {
		pattern_description pattern = null;
		pattern = queryItem("ComponentPattern.xls",name);
		getItemImages(pattern,"component",name);
		return pattern;
	}
		
	public Vector queryBehaviourList() {
		Vector risposte = new Vector();
		risposte = queryList("BehaviourPattern.xls");
		
		return risposte;
	}
	
	public pattern_description queryBehaviourItem(String name) {
		pattern_description pattern = null;
		pattern = queryItem("BehaviourPattern.xls",name);
		getItemImages(pattern,"behaviour",name);
		return pattern;
	}
	
	public String getActionPattern(String name,String parent, String platform) {
		String request_file = "ptk_resource/repository/"+platform.toLowerCase()+"/"+name+"@"+parent+".j";
        URL url = getClass().getClassLoader().getResource(request_file);
        File file = new File(url.getFile());
		
        //System.out.println("request for: "+url.getFile());
        
        String action_pattern = null;
		
        try {
			action_pattern = readFileAsString(url.getFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//System.out.println("file not found");
		}
        
		if (action_pattern == null)
			action_pattern = "// no action pattern for this method";
		
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
	
	public Vector queryList(String file_name) {
		String service_file = "ptk_resource/repository/"+file_name;
        URL resource_url = getClass().getClassLoader().getResource(service_file);
        File file = new File(resource_url.getFile());
        
		Vector risposte = new Vector();
		
		try {
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);
			
			int riga = 1;
			String cell_value;
			Cell cell;
			boolean continua = true;
			int numero_righe = sheet.getRows();

		
			while (riga<numero_righe) {
				cell = sheet.getCell(0, riga);
				cell_value = cell.getContents();
				if (cell_value != null && cell_value.length() > 0) {
					risposte.add(cell_value);
					riga++;
				} else {
					continua = false;
				}
			}
			
			workbook.close();
		} catch (BiffException e) {
			System.out.println("BiffException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
		
		return risposte;
	}
	
	public pattern_description queryItem(String file_name,String name) {
		String service_file = "ptk_resource/repository/"+file_name;
        URL resource_url = getClass().getClassLoader().getResource(service_file);
        File file = new File(resource_url.getFile());
 
		pattern_description pattern = null;

		try {
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);
			
			int riga = 1;
			String cell_value="";
			Cell cell;
			int numero_righe = sheet.getRows();
	
			while (pattern==null && riga<numero_righe) {
				cell = sheet.getCell(0, riga);
				cell_value = cell.getContents();
				if (cell_value != null && cell_value.equals(name)) {
					pattern = new pattern_description(name);
					pattern.setClassification(sheet.getCell(1,riga).getContents());
					pattern.setIntent(sheet.getCell(2,riga).getContents());
					pattern.setMotivation(sheet.getCell(3,riga).getContents());
					pattern.setPreCondition(sheet.getCell(4,riga).getContents());
					pattern.setPostCondition(sheet.getCell(5,riga).getContents());
					pattern.setParticipants(sheet.getCell(6,riga).getContents());
					pattern.setAvailability(sheet.getCell(7,riga).getContents());
					pattern.setRelatedPatterns(sheet.getCell(8,riga).getContents());
					
				}
				riga++;
			}
			
			workbook.close();
		} catch (BiffException e) {
			System.out.println("BiffException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
		
		URL xml_url = getClass().getResource("/ptk_resource/repository/xml/"+name+".xml");
		
		if (xml_url==null) {
			System.out.println("errore in /ptk_resource/repository/xml/"+name+".xml");
		} else {
			System.out.println("caricato /ptk_resource/repository/xml/"+name+".xml");
			try {
				if (pattern!=null)
					pattern.setXml(readFileAsString(xml_url.getFile()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("error: "+xml_url.getFile());
				e.printStackTrace();
			}
		}
		
		return pattern;
	}
	
	public void getItemImages(pattern_description pattern, String type,String name) {
		// preleva le immagini corrispondente
		if (pattern!=null) {
			URL structure_url = getClass().getResource("/ptk_resource/images/"+type+"_"+name+"_structure.gif");
			URL collab_url = getClass().getResource("/ptk_resource/images/"+type+"_"+name+"_collaboration.gif");
			pattern.setStructure( structure_url );
			pattern.setCollaboration( collab_url );			
		}
	}


    private String readFileAsString(String filePath) throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            fileData.append(buf, 0, numRead);
        }
        reader.close();
        return fileData.toString();
    }

}

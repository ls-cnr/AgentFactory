/*
 * CodeCounter.java
 *
 * Created on 28 febbraio 2003, 17.26
 */

package org.af1_0;

import java.io.*;
// prova
/**
 *
 * @author  luca
 */
public class CodeCounter {
	
	/** Creates a new instance of CodeCounter */
	public static int count_file(File file) throws IOException {
		Reader in = new FileReader(file);
		BufferedReader reader = new BufferedReader(in);
		int righe;
		int car1;
		int car2;
		int stato = 0;
		boolean riga_vuota=true;
		
		righe = 0;
		
		car1=reader.read();
		while(reader.ready()) {
			car2=reader.read();
			
			if (stato == 0) {
				
				if (car1=='/' && car2=='*')
					stato = 1;
				else if (car1 == '/' && car2=='/')
					stato = 2;
				else {
					//Character c = new Character((char) car1);
					if (!Character.isWhitespace((char) car1))
						if (car1!='{' && car1!='}')
							riga_vuota=false;
					//System.out.print(c);
					if (car1 == '\n') {
						if (riga_vuota == false)
							righe++;
						riga_vuota = true;
					}
					
				}
				
			} else if (stato == 1) {
				
				if (car1=='*' && car2=='/') {
					stato = 0;
					car2=reader.read();		// scarta anchew il '/'
				}
				
			} else if (stato == 2) {
				
				if (car1 == '\n') {
					//System.out.println(car1);
					if (riga_vuota == false)
						righe++;
					riga_vuota = true;
					stato = 0;
				}
				
			}
			
			car1 = car2;
		}
		return righe;		
	}
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: java org.af1_0.CodeCounter -f filename");
			System.out.println("or: java org.af1_0.CodeCounter -d filter");
		} else {
			String param = args[0];
			String argom = args[1];
						
			try {
				if (param.equals("-f")) {
					File filename = new File(argom);
					int l = CodeCounter.count_file(filename);
					System.out.println("Lines: "+l);
				} else if (param.equals("-d")) {
					File filename = new File(".");
					FileFilter filter = new myFilter(argom);
					File childs[] = filename.listFiles(filter);
					int tot = 0;
					for (int j=0; j<childs.length; j++) {
						if (childs[j].isFile()) {
							int l = CodeCounter.count_file(childs[j]);
							tot += l;
							System.out.println(childs[j].getName()+": "+l);
						}
					}
					System.out.println("Totale: "+tot);
				} else {
					System.out.println("Usage: java org.af1_0.CodeCounter -f filename");
					System.out.println("or: java org.af1_0.CodeCounter -d filter");
				}
			} catch( FileNotFoundException ex ) {
				ex.printStackTrace();
			} catch( IOException ex ) {
				ex.printStackTrace();
			}
		}
	}
	
}

class myFilter implements FileFilter {
	private String estenzione;
	
	public myFilter(String est) {
		estenzione = "."+est;
	}
	public boolean accept(File pathname) {
		if (pathname.isFile()) {
		
			String name = pathname.getName();
			int est_len = estenzione.length();

			String name_est = name.substring( name.length()-est_len);
			//System.out.println(name_est);
			if (name_est.equals(estenzione))
				return true;
		
		}	
		return false;
	}
}
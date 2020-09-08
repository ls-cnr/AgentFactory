/*
 * multi_agent_system.java
 *
 * Created on 25 ottobre 2002, 17.44
 */

package org.af1_0.mas;

import java.util.Iterator;
import java.net.*;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

import org.af1_0.entity.*;
import org.af1_0.entity.selector.agent_selector;
import org.af1_0.pattern.*;
import org.af1_0.repository.*;

/**
 * Questa classe fornisce una implementazione concreta del progetto, specializzato per una determinata piattaforma ad agenti
 *
 * @author  Luca
 */
public class multi_agent_system extends model {
    protected String platform;
    protected repository_designer repository;
    
    /**
     * Creates a new instance of multi_agent_system
     * @param the_platform nome della piattaforma ad agenti che deve essere usato per generare il codice. Usare "FIPAOS" oppure "Jade"
     */
    public multi_agent_system(String the_platform) {
        platform = the_platform;
        //repository = new repository_locale();
        repository = new repository_locale();
    }
    
    /**
     * factory oer nuovi pattern (specifici per la piattaforma ad agenti già specificata)
     * @return un pattern vuoto
     */
    public pattern createNewPattern() {
        return new pattern(platform);
    }
    
    /**
     * Imposta la piattaforma ad agenti
     * @param the_platform vedere costruttore della classe
     */
    public void setAgentPlatform(String the_platform) {
        platform = the_platform;
    }
    
    /**
     * Preleva la piattaforma ad agenti impostata
     * @return la stringa relativa alla piattaforma ad agenti
     */
    public String getAgentPlatform() {
        return platform;
    }
    
    
    public String getAgentShell() {
        String shell = "unknown";
        
        if (platform.equals("FIPAOS"))
            shell = "FIPAOSAgent";
        else if (platform.equals("Jade"))
            shell = "Agent";
        
        return shell;
    }
    
    public String getTaskShell() {
        String shell = "unknown";
        
        if (platform.equals("FIPAOS"))
            shell = "Task";
        else if (platform.equals("Jade"))
            shell = "Behaviour";
        
        return shell;
    }
    
    public StreamSource getJavaTransformation() {
        String res = "xml1_0/Java_transform.xsl";
        return getResource(res);
    }
    
    public StreamSource getXmiTransformation() {
        String res = "xml1_0/xmi_transform.xsl";
        return getResource(res);
    }
    
    public StreamSource getIntermediateTransformation() {
        String res = "xml1_0/from_xmi_transform.xsl";
        return getResource(res);
    }
    
    public StreamSource getPlatformTransformation() {
        String res;
        if (platform.equals("FIPAOS"))
            res = "xml1_0/FIPA-OS_transform.xsl";
        else
            res = "xml1_0/Jade_transform.xsl";
        return getResource(res);
    }
    
    private StreamSource getResource(String resource) {
        JarURLConnection jarConnection = null;
        java.io.InputStream stream = null;
        
        try {
            System.out.println("carico: ptk_resource/"+resource);
            URL resource_url = getClass().getClassLoader().getResource("ptk_resource/"+resource);
            stream = resource_url.openStream();
            return new StreamSource( stream );
        } catch (java.io.IOException e) {
            System.err.println("Impossibile aprire file");
        }
        
        try {
            URL resource_url = getClass().getClassLoader().getResource("ptk_resource/"+resource);
            jarConnection = (JarURLConnection)resource_url.openConnection();
            stream = jarConnection.getInputStream();
            return new StreamSource( stream );
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
        
        return new StreamSource( stream );
    }
    
    public repository_designer getRepository() {
        return repository;
    }
    
    public String getActionPattern(String name,String parent) {
        return repository.getActionPattern(name,parent,platform);
    }
    
    public void setRepository(repository_designer rep) {
        repository = rep;
    }
    
    /** questa funzione viene usata per applicare un pattern ad un
     * sistema multi agente.
     * @param the_pattern rappresenta il pattern che deve essere applicato.
     * Il pattern contiene la descrizione statica e dinamica
     * di un sottosistema.
     * La descrizione statica può contenere un task, un agente o più agenti
     * @param the_target Indica quale agente o quale insieme di agenti deve
     * ricevere le funzionalità descritte dal pattern.
     */
    public void apply_pattern(pattern the_pattern,boolean is_task) {
        entity_merging merging = new entity_merging(this);
        
        merging.addModel(the_pattern);
    }
    
    public void merge(multi_agent_system model) {
        entity_merging merging = new entity_merging(this);
        
        merging.addModel(model);
    }
}

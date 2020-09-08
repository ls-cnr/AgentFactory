/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.plugin;

import java.util.Vector;
import java.util.Iterator;
import java.util.LinkedList;

import org.af1_0.gui.command.*;
import org.af1_0.gui.*;
import org.af1_0.gui.wizard.wizard_control;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.mas.*;
import org.af1_0.pattern.*;

import org.af1_0.gui.xml.*;

/**
 * Questo comando viene attivato quando si seleziona OK nella selezione del pattern
 * da applicare al modello corrente
 *
 * @author  Luca
 */
public class behaviour_wizard_com_sheet2 implements command {
    protected WizardDialogCOM wizard_dialog;
    protected multi_agent_system current_model;
    protected Vector lista_agenti_esterni;
    
    /** Creates a new instance of component_wizard_start */
    public behaviour_wizard_com_sheet2(WizardDialogCOM dialog,multi_agent_system project,Vector agenti_esterni) {
        wizard_dialog = dialog;
        current_model = project;
        lista_agenti_esterni = agenti_esterni;
    }
    
    public void execute() {
        wizard_control wizard_panel = wizard_dialog.getWizardPanel();
        
        // 1.0 releva il pattern selezionato dalla scheda precedente
        pattern_description patt = wizard_panel.getPatternListPanel().getPatternSelected();
        if (patt == null)   // non è stato selezionato nessun pattern
            return;

        //pattern_resolver resolver = new pattern_resolver(current_model); 
        pattern_parser resolver = new pattern_parser(current_model); 
        pattern the_pattern = resolver.getPattern( patt );
        
        java_class agent = searchAgent( the_pattern );       
        LinkedList rel_list = new LinkedList(the_pattern.getDynamic().values());   
        
        wizard_panel.getPatternOverviewPanel().setRelations( rel_list );
        wizard_panel.getPatternOverviewPanel().setAgent( agent );
        
        // 2.0 mostra la scheda OVERVIEW
        wizard_panel.showCard("overview");
        
        // 3.0 imposta i pulsanti
        wizard_panel.setCancel(new cancel_com(wizard_dialog));
        wizard_panel.setPrevious( new behaviour_wizard_com_sheet1( wizard_dialog,current_model,lista_agenti_esterni) );
        wizard_panel.setNext( new behaviour_wizard_com_sheet3(wizard_dialog,current_model,lista_agenti_esterni,the_pattern)  );
        wizard_panel.setOk( null );
    }
    
    private java_class searchAgent(pattern a_pattern) {
        java_class agente = null;
        
        Iterator it = a_pattern.getStatic().values().iterator();
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isAgent())
                agente = (java_class) ent;
        }
        
        return agente;
    }
    
}

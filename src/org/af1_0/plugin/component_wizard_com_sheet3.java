/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.plugin;

import java.util.Vector;
import java.util.Iterator;

import org.af1_0.gui.command.model.*;
import org.af1_0.gui.command.*;
import org.af1_0.gui.*;
import org.af1_0.gui.wizard.wizard_control;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.pattern.*;
import org.af1_0.mas.*;

/**
 *
 * @author  Luca
 */
public class component_wizard_com_sheet3 implements command {
    protected WizardDialogCOM wizard_dialog;
    protected multi_agent_system current_model;
    protected pattern the_pattern;
    protected Vector lista_agenti_esterni;
    
    /** Creates a new instance of component_wizard_start */
    public component_wizard_com_sheet3(WizardDialogCOM dialog, multi_agent_system project, pattern a_pattern,Vector agenti_esterni) {
        wizard_dialog = dialog;
        current_model = project;
        the_pattern = a_pattern;
        lista_agenti_esterni = agenti_esterni;
    }
    
    public void execute() {
        wizard_control wizard_panel = wizard_dialog.getWizardPanel();
        
        // 1.0 imposta il nome corrente dell'agente
        java_class agent = searchAgent(the_pattern);
        wizard_panel.getSelectionPanel().setAgentName( agent.getName() );
        
        // 2.0 mostra la scheda ENTITY SELECTION
        wizard_panel.showCard("target");
        
        // 3.0 imposta i pulsanti
        wizard_panel.setCancel( new cancel_com(wizard_dialog) );
        wizard_panel.setPrevious( new component_wizard_com_sheet2(wizard_dialog,current_model,lista_agenti_esterni) );
        wizard_panel.setNext( null );
        wizard_panel.setOk( new component_com_apply(wizard_dialog,current_model,the_pattern) );
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

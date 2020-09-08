package org.af1_0.plugin;

/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

import java.util.Vector;
import java.util.Iterator;

import org.af1_0.gui.command.*;
import org.af1_0.gui.*;
import org.af1_0.gui.wizard.wizard_control;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.mas.*;
import org.af1_0.pattern.*;

/**
 *
 * @author  Luca
 */
public class behaviour_wizard_com_sheet3 implements command {
    protected WizardDialogCOM wizard_dialog;
    protected multi_agent_system current_model;
    protected pattern the_pattern;
    protected Vector lista_agenti_esterni;
    
    /** Creates a new instance of component_wizard_start */
    public behaviour_wizard_com_sheet3(WizardDialogCOM dialog, multi_agent_system project, Vector agenti_esterni,pattern a_pattern) {
        wizard_dialog = dialog;
        current_model = project;
        the_pattern = a_pattern;
        lista_agenti_esterni = agenti_esterni;
    }
    
    public void execute() {
        wizard_control wizard_panel = wizard_dialog.getWizardPanel();
        
        // 1.0 mostra la scheda AGENT SELECTION
        wizard_panel.showCard("agent");
        
        // 3.0 imposta i pulsanti
        wizard_panel.setCancel(new cancel_com(wizard_dialog));
        wizard_panel.setPrevious( new behaviour_wizard_com_sheet2( wizard_dialog,current_model,lista_agenti_esterni ) );
        wizard_panel.setNext( new behaviour_wizard_com_sheet4( wizard_dialog,current_model,lista_agenti_esterni,the_pattern) );
        wizard_panel.setOk( null );
    }
    
}

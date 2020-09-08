/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.plugin;

import java.util.Vector;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;

import org.af1_0.gui.command.*;
import org.af1_0.gui.wizard.wizard_control;
import org.af1_0.entity.*;
import org.af1_0.pattern.*;
import org.af1_0.mas.*;

/**
 *
 * @author  Luca
 */
public class behaviour_wizard_com implements command {
    protected WizardDialogCOM wizard_dialog;
    protected multi_agent_system current_model;
    protected Vector lista_agenti_esterni;
    
    /** Creates a new instance of component_wizard_start */
    public behaviour_wizard_com(WizardDialogCOM dialog,multi_agent_system project,Vector agenti_esterni) {
        wizard_dialog = dialog;
        current_model = project;
        lista_agenti_esterni = agenti_esterni;
    }
    
    public void execute() {
        wizard_control wizard_panel = wizard_dialog.getWizardPanel();
        
        wizard_panel.getPatternListPanel().setTitle("Behaviour pattern list");
        wizard_panel.getPatternOverviewPanel().setTitle("Behaviour pattern overview");
        wizard_panel.getAgentTargetPanel().setTitle("Agent target selection");
        wizard_panel.getSelectionPanel().setTitle("Task target selection");        
        
        Vector pattern_list = preleva_pattern_da_repository();
        wizard_panel.getPatternListPanel().setPatternList(pattern_list);  
        
        Vector agent_list = preleva_agenti_dal_modello();
        wizard_panel.getAgentTargetPanel().setAgentList(agent_list);
        
        // 3.0 mostra la prima scheda
        behaviour_wizard_com_sheet1 sheet1 = new behaviour_wizard_com_sheet1( wizard_dialog, current_model, lista_agenti_esterni);
        
        sheet1.execute();
    }
    
    private Vector preleva_pattern_da_repository() {
        Vector pattern_list = new Vector();
        Vector names = current_model.getRepository().queryBehaviourList();
        Iterator it = names.iterator();
        while (it.hasNext()) {
            String pattern_name = (String) it.next();
            pattern_list.add( current_model.getRepository().queryBehaviourItem(pattern_name) );
        }
        
        // effettua l'ordinamento dei pattern per la visualizzazione
        Collections.sort(pattern_list, new Comparator() {
            public int compare(Object a, Object b) {
                String aid = ((pattern_description)a).getName();
                String bid = ((pattern_description)b).getName();
                return aid.compareTo(bid);
            }
        });
        
        return pattern_list;
    }   
    
    private Vector preleva_agenti_dal_modello() {
        Vector agent_list = new Vector();
        
        Iterator it = lista_agenti_esterni.iterator();
        while (it.hasNext()) {
            external_agent ent = (external_agent) it.next();
            agent_list.add(ent.getName());
        }
        return agent_list;
    }        
}

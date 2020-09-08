/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.gui.command.service_wizard;

import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import java.util.LinkedList;

import org.af1_0.gui.command.*;
import org.af1_0.gui.command.model.*;
import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.pattern.*;
import org.af1_0.mas.*;

/**
 * Procedura che mostra la scheda overview del primo ruolo
 * 1. Preleva il pattern dal repository
 * 2. Effettua il parsing del pattern
 * 3. Preleva la lista di ruoli dal pattern
 * 4. Imposta la vista statica e dinamica con il primo agente.
 * 5. Visualizza il pannello overview
 * @author  Luca
 */
public class service_wizard_sheet2 implements command {
	protected multi_agent_system current_model;
	protected controls_panel controls;
	protected TreeModelUpdate tree_model;
	private int corrente;
	private pattern the_pattern;
	private String names[];
	
	/** Creates a new instance of component_wizard_start */
	public service_wizard_sheet2(controls_panel the_controls, multi_agent_system current, TreeModelUpdate model) {
		current_model = current;
		controls = the_controls;
		tree_model = model;
		corrente = 0;
	}
	public service_wizard_sheet2(controls_panel the_controls, multi_agent_system current, TreeModelUpdate model,pattern the_pattern,int corrente,String names[]) {
		current_model = current;
		controls = the_controls;
		tree_model = model;
		this.corrente = corrente;
		this.the_pattern = the_pattern;
		this.names = names;
	}
	
	public void execute() {
		//System.out.println("eseguo istanza n."+corrente);
		// 1.0 releva il pattern selezionato dalla scheda precedente
		if (corrente==0) {
			pattern_description patt = controls.getWizardPanel().getPatternListPanel().getPatternSelected();
			if (patt == null)   // non è stato selezionato nessun pattern
				return;
			
			pattern_parser resolver = new pattern_parser(current_model);
			the_pattern = resolver.getPattern( patt );		
		} 
		
		entity_search search = new entity_search(the_pattern);
		List role_list = search.getAgentList();		
		
		if (corrente == 0) {
			names = new String[role_list.size()];			
		} else {
			// preleva la precedente digitazione
			String agent_name = controls.getWizardPanel().getRoleTargetPanel().getEntityName();
			//System.out.println("precedente digitazione: "+agent_name);
			if ( agent_name == null)   // non è stato selezionato nessun task
				return;
			
			names[corrente-1] = agent_name;
		}

		// imposta la visualizzazione del ruolo corrente
		if (corrente < role_list.size() ) {
			java_class agent = (java_class) role_list.get(corrente);
			
			//System.out.println("mostro "+agent.getName());
			
			controls.getWizardPanel().getRoleTargetPanel().setAgentName( agent.getName() );
			controls.getWizardPanel().getRoleTargetPanel().setRoleDescription( "description of "+agent.getName()+" in service pattern" );
			controls.getWizardPanel().getRoleTargetPanel().setTitle("Role "+agent.getName()+" pattern selection");
						
			// 2.0 mostra la scheda ENTITY SELECTION
			controls.getWizardPanel().showCard("role");
			
			if (corrente==0) {
				controls.getWizardPanel().setCancel( new cancel(controls) );
				controls.getWizardPanel().setPrevious( new service_wizard_sheet1(controls,current_model,tree_model) );
				controls.getWizardPanel().setNext( new service_wizard_sheet2(controls,current_model,tree_model,the_pattern,corrente+1,names) );
				controls.getWizardPanel().setOk( null );
			} else if (corrente < names.length-1) {
				// 3.0 imposta i pulsanti
				controls.getWizardPanel().setCancel( new cancel(controls) );
				controls.getWizardPanel().setPrevious( new service_wizard_sheet2(controls,current_model,tree_model,the_pattern,corrente-1,names) );
				controls.getWizardPanel().setNext( new service_wizard_sheet2(controls,current_model,tree_model,the_pattern,corrente+1,names) );
				controls.getWizardPanel().setOk( null );
			} else {
				// 3.0 imposta i pulsanti
				controls.getWizardPanel().setCancel( new cancel(controls) );
				controls.getWizardPanel().setPrevious( new service_wizard_sheet2(controls,current_model,tree_model,the_pattern,corrente-1,names) );
				controls.getWizardPanel().setNext( null );
				controls.getWizardPanel().setOk( new service_apply(controls,current_model,tree_model,the_pattern,names) );
			}
			
		}
		
	}
}
/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.gui.command.component_wizard;

import java.util.Vector;
import java.util.Iterator;

import org.af1_0.gui.command.*;
import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.mas.*;

/**
 *
 * @author  Luca
 */
public class component_wizard_sheet1 implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    protected TreeModelUpdate tree_model;
    
    /** Creates a new instance of component_wizard_start */
    public component_wizard_sheet1(controls_panel the_controls, multi_agent_system current, TreeModelUpdate model) {
        current_model = current;
        controls = the_controls;
        tree_model = model;
    }
    
    public void execute() {
        // 1.0 mostra la scheda COMPONENT LIS
        controls.getWizardPanel().showCard("component_list");
        
        // 2.0 imposta i pulsanti
        controls.getWizardPanel().setCancel( new cancel(controls) );
        controls.getWizardPanel().setPrevious( null );
        controls.getWizardPanel().setNext( new component_wizard_sheet2(controls,current_model,tree_model)  );
        controls.getWizardPanel().setOk( null );
    }
    
}

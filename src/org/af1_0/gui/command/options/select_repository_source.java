/*
 * component_wizard_start.java
 *
 * Created on 30 novembre 2002, 11.19
 */

package org.af1_0.gui.command.options;

import java.util.Vector;
import java.util.Iterator;

import org.af1_0.gui.command.*;
import org.af1_0.gui.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.mas.multi_agent_system;
import org.af1_0.entity.*;
import org.af1_0.mas.*;
import org.af1_0.repository.*;

/**
 *
 * @author  Luca
 */
public class select_repository_source implements command {
    protected multi_agent_system current_model;
    protected controls_panel controls;
    
    /** Creates a new instance of component_wizard_start */
    public select_repository_source(controls_panel the_controls, multi_agent_system current) {
        current_model = current;
        controls = the_controls;
    }
    
    public void execute() {
        controls.getOptionPanel().setTitle("Repository connection panel");
        
        repository_designer repository = current_model.getRepository();
        if (repository.isLocale()) {
            controls.getOptionPanel().getRepositoryPanel().setCurrentRepository(0);
            controls.getOptionPanel().getRepositoryPanel().setUserUrl("");
        } else {
            repository_proxy proxy = (repository_proxy) repository;
            String url = proxy.getUrl();
            if (url.equals("mozart.csai.unipa.it")) {
                controls.getOptionPanel().getRepositoryPanel().setCurrentRepository(1);
                controls.getOptionPanel().getRepositoryPanel().setUserUrl("");
            } else {
                controls.getOptionPanel().getRepositoryPanel().setCurrentRepository(2);
                controls.getOptionPanel().getRepositoryPanel().setUserUrl(url);
            }    
        }        
        
        controls.getOptionPanel().showCard("repository");
        
        controls.getOptionPanel().setOk( new change_repository(controls,current_model) );
        controls.getOptionPanel().setCancel( new cancel(controls) );

        ((java.awt.CardLayout) controls.getLayout()).show(controls,"options");
     }
    
}

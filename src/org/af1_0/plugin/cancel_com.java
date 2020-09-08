/*
 * add_new_agent.java
 *
 * Created on 2 novembre 2002, 10.49
 */

package org.af1_0.plugin;

import org.af1_0.gui.command.command;

/** Questa classe svolge il compito di aggiungere un nuovo agente al multi-agent system corrente
 * @author Luca
 */
public class cancel_com implements command {
    protected WizardDialogCOM wizard_dialog;
    
    /** Creates a new instance of add_new_agent */
    public cancel_com(WizardDialogCOM dialog) {
        wizard_dialog = dialog;
    }
    
    /** Esegue l'operazione
     */    
    public void execute() {
        wizard_dialog.terminate(false);
    }
    
}

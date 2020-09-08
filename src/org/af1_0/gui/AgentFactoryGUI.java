/*
 * AgentFactoryGUI.java
 *
 * Created on 1 novembre 2002, 14.59
 */

package org.af1_0.gui;

import java.util.Iterator;
import java.io.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;


import org.af1_0.mas.multi_agent_system;
import org.af1_0.gui.command.*;
import org.af1_0.gui.command.model.*;
import org.af1_0.gui.command.behaviour_wizard.*;
import org.af1_0.gui.command.component_wizard.*;
import org.af1_0.gui.command.service_wizard.*;
import org.af1_0.gui.command.options.*;
import org.af1_0.gui.panel.controls_panel;
import org.af1_0.gui.command.panels.*;
import org.af1_0.entity.*;
import org.af1_0.mas.*;
import org.af1_0.gui.xml.*;
import org.af1_0.gui.command.*;

// temp
import org.af1_0.pattern.*;
import org.af1_0.repository.*;


/**
 *
 * @author  Luca
 */
public class AgentFactoryGUI extends javax.swing.JFrame {
    private multi_agent_system current_project;
    private org.af1_0.gui.DynamicTreePanel tree_panel;
    private org.af1_0.gui.TreeModelUpdate tree_model_update;

    private ByteArrayOutputStream buffer_err;
    private ByteArrayOutputStream buffer_out;
    private PrintStream buffer_print_err;
    private PrintStream buffer_print_out;

    private controls_panel controls;
    
    private org.af1_0.gui.command.command add_new_agent_command;
    private org.af1_0.gui.command.command add_new_relation_command;
    private org.af1_0.gui.command.command apply_component_pattern;
    private org.af1_0.gui.command.command apply_behaviour_pattern;
    private org.af1_0.gui.command.command apply_service_pattern;
    
    private org.af1_0.gui.command.command import_java;
    private org.af1_0.gui.command.command import_java_dir;
    
    private org.af1_0.gui.command.command refresh_the_model;
    private org.af1_0.gui.command.command choose_repository;
    private org.af1_0.gui.command.command new_project;
    private org.af1_0.gui.command.command show_about_box;
    
    /** Creates new form AgentFactoryGUI */
    public AgentFactoryGUI() {
        current_project = new multi_agent_system("Jade");
        tree_panel = new org.af1_0.gui.DynamicTreePanel("System: Jade");
        tree_model_update = new org.af1_0.gui.TreeModelUpdate(tree_panel);
        controls = new controls_panel();

        buffer_err = new ByteArrayOutputStream();
        buffer_print_err = new PrintStream(buffer_err);

        buffer_out = new ByteArrayOutputStream();
        buffer_print_out = new PrintStream(buffer_out);

        //System.setErr(buffer_print_err);
        //System.setOut(buffer_print_out);

        initCommands();
        
        initComponents();

        PreliminarTests tests = new PreliminarTests();
    }

    public TreeModelUpdate getModelUpdate() {
        return tree_model_update;
    }

    public multi_agent_system getModel() {
        return current_project;
    }
    
    public multi_agent_system getmodel() {
        return current_project;
    }
    
    public void setModel(multi_agent_system my_model) {
        current_project = my_model;
    }
    
    public void update() {
        refresh_the_model.execute();
    }
    
    private void initCommands() {
        add_new_agent_command = new insert_agent(controls,current_project,tree_model_update);
        add_new_relation_command = new insert_relation(controls,current_project,tree_model_update);
        
        apply_component_pattern = new component_wizard(controls,current_project,tree_model_update);
        apply_behaviour_pattern = new behaviour_wizard(controls,current_project,tree_model_update);
        apply_service_pattern = new service_wizard(controls,current_project,tree_model_update);
        
        refresh_the_model = new refresh_model(current_project, tree_model_update);
        choose_repository = new select_repository_source(controls,current_project);
        new_project = new new_project_option(controls,current_project,tree_model_update,tree_panel);
        
        show_about_box = new show_info_panel(controls);
        
        import_java = new import_java_command(this,getModel());
        import_java_dir = new import_java_dir_command(this,getModel());
        
        // command for DynamicTreePanel
        tree_panel.setCommandFactory( new command_factory(current_project,controls,tree_panel,tree_model_update) );
    }
    
    private void prova() {
        entity_factory factory = new entity_factory(current_project);
        
        java_class agente1 = factory.createAgent("agent1");
        java_class task1 = factory.createTask(agente1,"task1");
        java_attribute attrib = factory.createAttribute(agente1,"String","AGENT_NAME");
        java_method meth1 = factory.createMethod(agente1,"","agent1");
        java_method meth2 = factory.createMethod(agente1,"void","setup");
        java_method meth3 = factory.createMethod(task1,"","task1");
        java_method meth4 = factory.createMethod(task1,"void","action");
        
        relation rel1 = factory.createRelation("rel1",meth1,meth2);
        relation rel2 = factory.createRelation("rel2",meth2,meth3);
        relation rel3 = factory.createRelation("rel3",meth3,meth4);
        
        tree_model_update.insert_agent(agente1);
        tree_model_update.insert_relation(rel1);
        tree_model_update.insert_relation(rel2);
        tree_model_update.insert_relation(rel3);
        
                /*
                pattern_resolver resolver = new pattern_resolver(current_project);
                pattern the_pattern = resolver.getPattern( current_project.getRepository().queryComponentItem("Prova") );
                tree_model_update.insert_pattern(current_project,the_pattern);
                current_project.apply_pattern(the_pattern,false);
                 */
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = tree_panel;
        jPanel1 = controls;
        jMenuBar1 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        NewMenu = new javax.swing.JMenuItem();
        ImportXmiMenu = new javax.swing.JMenuItem();
        ImportJavaMenu = new javax.swing.JMenuItem();
        ImportJavaDirMenu = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        ExportXmiMenu = new javax.swing.JMenuItem();
        ExportJavaMenu = new javax.swing.JMenuItem();
        RefreshMenu = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        ExitMenu = new javax.swing.JMenuItem();
        EditMasMenu = new javax.swing.JMenu();
        AddAgentMenu = new javax.swing.JMenuItem();
        AddRelationMenu = new javax.swing.JMenuItem();
        PatternMenu = new javax.swing.JMenu();
        ApplyBehaviour = new javax.swing.JMenuItem();
        ApplyComponent = new javax.swing.JMenuItem();
        ApplyService = new javax.swing.JMenuItem();
        RepositoryMenu = new javax.swing.JMenu();
        RepositoryLocation = new javax.swing.JMenuItem();
        HelpMenu = new javax.swing.JMenu();
        AboutMenuItem = new javax.swing.JMenuItem();
        CopyToClipItem = new javax.swing.JMenuItem();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jSplitPane1.setDividerLocation(180);
        jSplitPane1.setLeftComponent(jPanel2);

        jPanel1.setPreferredSize(new java.awt.Dimension(500, 10));
        jSplitPane1.setRightComponent(jPanel1);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        FileMenu.setText("File");
        NewMenu.setText("New Multi Agent System");
        NewMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewMenuActionPerformed(evt);
            }
        });

        FileMenu.add(NewMenu);

        ImportXmiMenu.setText("Import from XMI");
        ImportXmiMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportXmiMenuActionPerformed(evt);
            }
        });

        FileMenu.add(ImportXmiMenu);

        ImportJavaMenu.setText("Import Java File");
        ImportJavaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportJavaMenuActionPerformed(evt);
            }
        });

        FileMenu.add(ImportJavaMenu);

        ImportJavaDirMenu.setText("Import Java Directory");
        ImportJavaDirMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportJavaDirMenuActionPerformed(evt);
            }
        });

        FileMenu.add(ImportJavaDirMenu);

        FileMenu.add(jSeparator1);

        ExportXmiMenu.setText("Export to XMI");
        ExportXmiMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportXmiMenuActionPerformed(evt);
            }
        });

        FileMenu.add(ExportXmiMenu);

        ExportJavaMenu.setText("Export to java");
        ExportJavaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportJavaMenuActionPerformed(evt);
            }
        });

        FileMenu.add(ExportJavaMenu);

        RefreshMenu.setText("RefreshModel");
        RefreshMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshMenuActionPerformed(evt);
            }
        });

        FileMenu.add(RefreshMenu);

        FileMenu.add(jSeparator2);

        ExitMenu.setText("Exit");
        ExitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitMenuActionPerformed(evt);
            }
        });

        FileMenu.add(ExitMenu);

        jMenuBar1.add(FileMenu);

        EditMasMenu.setText("Edit");
        AddAgentMenu.setText("Add new agent");
        AddAgentMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddAgentMenuActionPerformed(evt);
            }
        });

        EditMasMenu.add(AddAgentMenu);

        AddRelationMenu.setText("Add new relation");
        AddRelationMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddRelationMenuActionPerformed(evt);
            }
        });

        EditMasMenu.add(AddRelationMenu);

        jMenuBar1.add(EditMasMenu);

        PatternMenu.setText("Pattern");
        ApplyBehaviour.setText("Behaviour pattern Wizard");
        ApplyBehaviour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplyBehaviourActionPerformed(evt);
            }
        });

        PatternMenu.add(ApplyBehaviour);

        ApplyComponent.setText("Component pattern Wizard");
        ApplyComponent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplyComponentActionPerformed(evt);
            }
        });

        PatternMenu.add(ApplyComponent);

        ApplyService.setText("Service pattern Wizard");
        ApplyService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplyServiceActionPerformed(evt);
            }
        });

        PatternMenu.add(ApplyService);

        jMenuBar1.add(PatternMenu);

        RepositoryMenu.setText("Manage repository");
        RepositoryLocation.setText("Change repository");
        RepositoryLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RepositoryLocationActionPerformed(evt);
            }
        });

        RepositoryMenu.add(RepositoryLocation);

        jMenuBar1.add(RepositoryMenu);

        HelpMenu.setText("Help");
        AboutMenuItem.setText("About AgentFactory");
        AboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutMenuItemActionPerformed(evt);
            }
        });

        CopyToClipItem.setText("Copy Java Console to Clipboard");
        CopyToClipItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CopyMenuItemActionPerformed(evt);
            }
        });

        HelpMenu.add(CopyToClipItem);
        HelpMenu.add(AboutMenuItem);

        jMenuBar1.add(HelpMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }//GEN-END:initComponents

    private void ImportJavaDirMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportJavaDirMenuActionPerformed
        import_java_dir.execute();
        update();
    }//GEN-LAST:event_ImportJavaDirMenuActionPerformed

    private void ImportJavaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportJavaMenuActionPerformed
        import_java.execute();
        update();
    }//GEN-LAST:event_ImportJavaMenuActionPerformed
    
    private void AboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutMenuItemActionPerformed
        show_about_box.execute();
    }//GEN-LAST:event_AboutMenuItemActionPerformed
    
    private void RepositoryLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RepositoryLocationActionPerformed
        choose_repository.execute();
    }//GEN-LAST:event_RepositoryLocationActionPerformed
    
    private void ExitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitMenuActionPerformed
        System.exit(0);
    }//GEN-LAST:event_ExitMenuActionPerformed
    
    private void ExportJavaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportJavaMenuActionPerformed
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showSaveDialog(this);
        if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            try {
                java.io.File dir = chooser.getSelectedFile();
                if (dir.isDirectory() && dir.exists()) {
                    entity_search search = new entity_search(current_project);
                    Iterator it = search.getAgentList().iterator();
                    while( it.hasNext() ) {
                        java_class agent = (java_class) it.next();
                        agent_to_java transf = new agent_to_java(agent,current_project);
                        String java_code = transf.getJava();
                        
                        File agent_file = new File(dir,agent.getName()+".java");
                        java.io.FileWriter out = new java.io.FileWriter(agent_file);
                        out.write(java_code);
                        out.close();
                    }
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_ExportJavaMenuActionPerformed
    
    private void ImportXmiMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportXmiMenuActionPerformed
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            try {
                java.io.File myfile = chooser.getSelectedFile();
                java.io.BufferedReader in = new java.io.BufferedReader(new FileReader(myfile));
                StringBuffer xmi = new StringBuffer();
                while (in.ready()) {
                    xmi.append( in.readLine() );
                }
                in.close();
                
                mas_from_xmi model = new mas_from_xmi(current_project.getAgentPlatform(),xmi.toString());
                
                current_project.merge(model);
                refresh_the_model.execute();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_ImportXmiMenuActionPerformed
    
    private void ExportXmiMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportXmiMenuActionPerformed
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
        int returnVal = chooser.showSaveDialog(this);
        if(returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            //System.out.println("You chose to open this file: " +
            try {
                java.io.File myfile = chooser.getSelectedFile();
                java.io.FileWriter out = new java.io.FileWriter(myfile);
                mas_to_xmi transf = new mas_to_xmi(current_project);
                out.write(transf.getXmi());
                out.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_ExportXmiMenuActionPerformed
    
    private void NewMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewMenuActionPerformed
        new_project.execute();
    }//GEN-LAST:event_NewMenuActionPerformed
    
    private void RefreshMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshMenuActionPerformed
        refresh_the_model.execute();
    }//GEN-LAST:event_RefreshMenuActionPerformed
    
    // event thrown when Service Wizard is selected
    private void ApplyServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApplyServiceActionPerformed
        apply_service_pattern.execute();
    }//GEN-LAST:event_ApplyServiceActionPerformed
    
    // event thrown when Component Wizard is selected
    private void ApplyComponentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApplyComponentActionPerformed
        apply_component_pattern.execute();
    }//GEN-LAST:event_ApplyComponentActionPerformed
    
    // event thrown when Behaviour Wizard is selected
    private void ApplyBehaviourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApplyBehaviourActionPerformed
        apply_behaviour_pattern.execute();
    }//GEN-LAST:event_ApplyBehaviourActionPerformed
    
    // event thrown when ADD NEW AGENT is selected
    private void AddRelationMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddRelationMenuActionPerformed
        add_new_relation_command.execute();
    }//GEN-LAST:event_AddRelationMenuActionPerformed
    
    // event thrown when ADD NEW RELATION is selected
    private void AddAgentMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddAgentMenuActionPerformed
        add_new_agent_command.execute();
    }//GEN-LAST:event_AddAgentMenuActionPerformed

    private void CopyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddAgentMenuActionPerformed
        Toolkit toolkit = getToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        String toClipboard = "Console Message:\n" + buffer_out.toString() + "\n\nSystem Errors:\n"+buffer_err.toString() ;
        StringSelection ss = new StringSelection(toClipboard);
        clipboard.setContents(ss,ss);
    }//GEN-LAST:event_AddAgentMenuActionPerformed


    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new AgentFactoryGUI().show();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutMenuItem;
    private javax.swing.JMenuItem CopyToClipItem;
    private javax.swing.JMenuItem AddAgentMenu;
    private javax.swing.JMenuItem AddRelationMenu;
    private javax.swing.JMenuItem ApplyBehaviour;
    private javax.swing.JMenuItem ApplyComponent;
    private javax.swing.JMenuItem ApplyService;
    private javax.swing.JMenu EditMasMenu;
    private javax.swing.JMenuItem ExitMenu;
    private javax.swing.JMenuItem ExportJavaMenu;
    private javax.swing.JMenuItem ExportXmiMenu;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenu HelpMenu;
    private javax.swing.JMenuItem ImportJavaDirMenu;
    private javax.swing.JMenuItem ImportJavaMenu;
    private javax.swing.JMenuItem ImportXmiMenu;
    private javax.swing.JMenuItem NewMenu;
    private javax.swing.JMenu PatternMenu;
    private javax.swing.JMenuItem RefreshMenu;
    private javax.swing.JMenuItem RepositoryLocation;
    private javax.swing.JMenu RepositoryMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
    
}

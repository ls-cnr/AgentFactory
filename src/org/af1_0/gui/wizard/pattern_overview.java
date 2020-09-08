/*
 * pattern_list.java
 *
 * Created on 30 novembre 2002, 13.44
 */

package org.af1_0.gui.wizard;

import java.util.LinkedList;

import org.af1_0.entity.*;
import org.af1_0.gui.*;

/**
 *
 * @author  Luca
 */
public class pattern_overview extends javax.swing.JPanel {
    protected classDiagram class_panel = new classDiagram();
    protected activityDiagram activity_panel = new activityDiagram();
    
    /** Creates new form pattern_list */
    public pattern_overview() {
        initComponents();
    }
    
    public void setRelations(LinkedList list) {
        activity_panel.setRelations(list);
    }

    public void setAgent(java_class agent) {
        class_panel.setAgent( agent );
        activity_panel.setAgent( agent );
    }
    
    public void setTitle(String titolo) {
        jLabel1.setText(titolo);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = class_panel;
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel4 = activity_panel;

        setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Titolo");
        add(jLabel1, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setViewportView(jPanel3);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Static stucture", jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setViewportView(jPanel4);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Dynamic structure", jPanel2);

        add(jTabbedPane1, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
    
}
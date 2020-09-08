/*
 * edit_relation_panel.java
 *
 * Created on 2 novembre 2002, 17.14
 */

package org.af1_0.gui.panel;

import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;

import org.af1_0.entity.*;
import org.af1_0.gui.command.command;

/**
 *
 * @author  Luca
 */
public class edit_relation_panel extends javax.swing.JPanel {
    protected relation rel = new relation();
    protected LinkedList method_list = null;
    protected Vector method_id_list = null;
    command ok_command = null;
    command cancel_command = null;
    
    /** Creates new form edit_relation_panel */
    public edit_relation_panel() {
        initComponents();
    }
    
    public void setMethodList(LinkedList a_method_list) {
        method_list = a_method_list;
        method_id_list = new Vector();
        
        // effettua l'ordinamento dei metodi per la visualizzazione
        Collections.sort(a_method_list, new Comparator() {
            public int compare(Object a, Object b) {
                String aid = ((java_method)a).getIDString();
                String bid = ((java_method)b).getIDString();
                return aid.compareTo(bid);
            }
        });
        
        Iterator it = a_method_list.iterator();
        while (it.hasNext()) {
            java_method method = (java_method) it.next();
            method_id_list.add( method.getIDString() );
        }
        
        parent.setModel( new DefaultComboBoxModel(method_id_list)) ;
        child.setModel( new DefaultComboBoxModel(method_id_list)) ;
    }
    
    public void setSuggested(relation the_rel) {
        rel.setName( the_rel.getName() );
        rel.setStart( the_rel.getStart() );
        rel.setEnd( the_rel.getEnd() );
        if (rel.getStart() != null && rel.getEnd() != null) {
            parent.setSelectedItem(rel.getStart().getIDString() );
            child.setSelectedItem(rel.getEnd().getIDString() );
        }
        nameField.setText(rel.getName());
    }
    
    public relation getRelation() {
        return rel;
    }

    public void setOkCommand(command ok) {
        ok_command = ok;
    }
    
    public void setCancelCommand(command cancel) {
        cancel_command = cancel;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        OKButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        parent = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        child = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        OKButton.setText("Ok");
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        jPanel1.add(OKButton);

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        jPanel1.add(CancelButton);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.GridLayout(4, 1));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Parent:");
        jPanel3.add(jLabel1);

        jPanel3.add(parent);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Child:");
        jPanel3.add(jLabel2);

        jPanel3.add(child);

        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        jLabel3.setText("Relation name: ");
        jPanel4.add(jLabel3);

        nameField.setPreferredSize(new java.awt.Dimension(200, 20));
        jPanel4.add(nameField);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        rel.setName(null);
        if (cancel_command != null)
            cancel_command.execute();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        int index1 = parent.getSelectedIndex();
        int index2 = child.getSelectedIndex();
        
        if (index1 >= 0 && index2 >= 0) {
            java_method meth1 = (java_method) method_list.get(index1);
            java_method meth2 = (java_method) method_list.get(index2);

            rel.setStart(meth1);
            rel.setEnd(meth2);
            rel.setName( nameField.getText() );

            if (ok_command != null)
                ok_command.execute();
        }
    }//GEN-LAST:event_OKButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField nameField;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton OKButton;
    private javax.swing.JButton CancelButton;
    private javax.swing.JComboBox child;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox parent;
    // End of variables declaration//GEN-END:variables
    
}
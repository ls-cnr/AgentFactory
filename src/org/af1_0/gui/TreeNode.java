/*
 * TreeNode.java
 *
 * Created on 1 novembre 2002, 16.01
 */

package org.af1_0.gui;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import java.util.Observer;
import java.util.Observable;

import org.af1_0.entity.entity;

/** Questa classe rappresenta un nodo dell'albero che rimane in attesa del dato ad esso associato e
 *  aggiorna in maniera automatica la sua rappresentazione grafica quando il dato subisce variazioni
 * @author  Luca
 */
public class TreeNode implements Observer {
    DefaultMutableTreeNode myNode = null;
    DefaultTreeModel myTree = null;
    entity node = null;
    
    /** Creates a new instance of TreeNode */
    public TreeNode(DefaultTreeModel model, java.util.Observable observable) {
        // si registra come observer del dato
        observable.addObserver(this);
        myNode = new DefaultMutableTreeNode(observable);
        myTree = model;
    }
    
    public MutableTreeNode getMutableTreeNode() {
        return myNode;
    }
    
    public void update(java.util.Observable observable, Object obj) {
        myNode.setUserObject(observable.toString());
        myTree.nodeChanged(myNode);
    }
    
    public void setNodeEntity(entity ent) {
        node = ent;
    }
    
    public entity getNodeEntity() {
        return node;
    }
    
}

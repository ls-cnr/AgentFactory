/*
 * treeUpdate.java
 *
 * Created on 21 settembre 2002, 12.53
 */

package org.af1_0.gui;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.LinkedList;
import java.util.Iterator;

import org.af1_0.entity.*;
import org.af1_0.mas.model;

/** Si occupa di inserire i nodi relativi alle entità di un modello nel DynamicTreePanel
 *
 * @author Luca
 */
public class TreeModelUpdate {
    DynamicTreePanel tree_panel;
    LinkedList agent_nodes = null;
    LinkedList task_nodes = null;
    LinkedList attribute_nodes = null;
    LinkedList method_nodes = null;
    LinkedList relation_nodes = null;
    
    /** Creates a new instance of treeUpdate */
    public TreeModelUpdate(DynamicTreePanel the_tree_panel) {
        tree_panel = the_tree_panel;
        agent_nodes = new LinkedList();
        task_nodes = new LinkedList();
        attribute_nodes = new LinkedList();
        method_nodes = new LinkedList();
        relation_nodes = new LinkedList();
    }
    
    public TreeNode searchNode(MutableTreeNode a_node) {
        Iterator it;
        TreeNode searched_node = null;
        
        it = agent_nodes.iterator();
        while (it.hasNext() && searched_node==null) {
            TreeNode node = (TreeNode) it.next();
            if (node.getMutableTreeNode()==a_node)
                searched_node = node;
        }
            
        it = task_nodes.iterator();
        while (it.hasNext() && searched_node==null) {
            TreeNode node = (TreeNode) it.next();
            if (node.getMutableTreeNode()==a_node)
                searched_node = node;
        }

        it = attribute_nodes.iterator();
        while (it.hasNext() && searched_node==null) {
            TreeNode node = (TreeNode) it.next();
            if (node.getMutableTreeNode()==a_node)
                searched_node = node;
        }

        it = method_nodes.iterator();
        while (it.hasNext() && searched_node==null) {
            TreeNode node = (TreeNode) it.next();
            if (node.getMutableTreeNode()==a_node)
                searched_node = node;
        }

        it = relation_nodes.iterator();
        while (it.hasNext() && searched_node==null) {
            TreeNode node = (TreeNode) it.next();
            if (node.getMutableTreeNode()==a_node)
                searched_node = node;
        }

        return searched_node;
    }
    
    public void insert_pattern(model the_pattern) {
        Iterator it = the_pattern.getStatic().values().iterator();
        
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isAgent()) {
                java_class agent = (java_class) ent;
                
                    insert_agent(agent);
            }
        }
        
        it = the_pattern.getDynamic().values().iterator();
        
        while (it.hasNext()) {
            entity ent = (entity) it.next();
            if (ent.isRelation()) {
                relation rel = (relation) ent;
                
                    insert_relation(rel);
            }
        }
    }

    public void clearAll() {
        tree_panel.setNonePopMenu();
        agent_nodes = new LinkedList();
        task_nodes = new LinkedList();
        attribute_nodes = new LinkedList();
        method_nodes = new LinkedList();
        relation_nodes = new LinkedList();
        tree_panel.clear();
    }
    
    /** Inserisce un agente e tutti i suoi componenti nell'albero
     */    
    public void insert_agent(java_class agent) {
        Iterator it;
        TreeNode agent_node = containsAgent(agent.getName());
        
        if (agent_node == null) {
            agent_node = new TreeNode(tree_panel.getTreeModel(),agent);
            agent_node.setNodeEntity(agent);
            tree_panel.insertNodeAtLast(agent_node.getMutableTreeNode(), tree_panel.getRoot() );
            agent_nodes.add(agent_node);
        }
        
        it = agent.getAttributeList();
        while (it.hasNext()) {
            java_attribute attribute = (java_attribute) it.next();
            insert_attribute(agent_node,attribute);
        }
        it = agent.getMethodList();
        while (it.hasNext()) {
            java_method method = (java_method) it.next();
            insert_method(agent_node,method);
        }
        it = agent.getInnerClassList();
        while (it.hasNext()) {
            java_class task = (java_class) it.next();
            insert_task(agent_node,task);
        }
    }
    
    public void insert_relation(relation rel) {
        TreeNode rel_node = containsRelation(rel.getName());
        
        if (rel_node == null) {
            rel_node = new TreeNode(tree_panel.getTreeModel(),rel);
            rel_node.setNodeEntity(rel);
            tree_panel.insertNodeAtLast(rel_node.getMutableTreeNode(), tree_panel.getRoot() );
            relation_nodes.add(rel_node);
        }
    }

    /** Inserisce un task e tutti i suoi componenti nell'albero
     */    
    public void insert_task(TreeNode parent,java_class task) {
        Iterator it;
        TreeNode task_node = containsTask(task.getIDString());
        
        if (task_node == null) {
            task_node = new TreeNode(tree_panel.getTreeModel(),task);
            task_node.setNodeEntity(task);
            tree_panel.insertNodeAtLast(task_node.getMutableTreeNode(), parent.getMutableTreeNode() );
            task_nodes.add(task_node);
        }
        
        it = task.getAttributeList();
        while (it.hasNext()) {
            java_attribute attribute = (java_attribute) it.next();
            insert_attribute(task_node,attribute);
        }
        it = task.getMethodList();
        while (it.hasNext()) {
            java_method method = (java_method) it.next();
            insert_method(task_node,method);
        }
    }
    
    public void insert_task(MutableTreeNode parent,java_class task) {
        Iterator it;
        TreeNode task_node = containsTask(task.getIDString());
        
        if (task_node == null) {
            task_node = new TreeNode(tree_panel.getTreeModel(),task);
            task_node.setNodeEntity(task);
            tree_panel.insertNodeAtLast(task_node.getMutableTreeNode(), parent );
            task_nodes.add(task_node);
        }

        it = task.getAttributeList();
        while (it.hasNext()) {
            java_attribute attribute = (java_attribute) it.next();
            insert_attribute(task_node,attribute);
        }
        it = task.getMethodList();
        while (it.hasNext()) {
            java_method method = (java_method) it.next();
            insert_method(task_node,method);
        }
    }

    public void insert_attribute(TreeNode parent,java_attribute attribute) {
        TreeNode a_node = containsAttribute(attribute.getIDString());
        
        if (a_node == null) {
            a_node = new TreeNode(tree_panel.getTreeModel(),attribute);
            a_node.setNodeEntity(attribute);
            tree_panel.insertNodeAtLast(a_node.getMutableTreeNode(), parent.getMutableTreeNode() );
            attribute_nodes.add(a_node);
        }
    }

    public void insert_attribute(MutableTreeNode parent,java_attribute attribute) {
        TreeNode a_node = containsAttribute(attribute.getIDString());
        
        if (a_node == null) {
            a_node = new TreeNode(tree_panel.getTreeModel(),attribute);
            a_node.setNodeEntity(attribute);
            tree_panel.insertNodeAtLast(a_node.getMutableTreeNode(), parent );
            attribute_nodes.add(a_node);
        }
    }

    public void insert_method(TreeNode parent,java_method method) {
        TreeNode a_node = containsMethod(method.getIDString());
        
        if (a_node == null) {
            a_node = new TreeNode(tree_panel.getTreeModel(),method);
            a_node.setNodeEntity(method);
            tree_panel.insertNodeAtLast(a_node.getMutableTreeNode(), parent.getMutableTreeNode() );
            method_nodes.add(a_node);
        }
    }

    public void insert_method(MutableTreeNode parent,java_method method) {
        TreeNode a_node = containsMethod(method.getIDString());
        
        if (a_node == null) {
            a_node = new TreeNode(tree_panel.getTreeModel(),method);
            a_node.setNodeEntity(method);
            tree_panel.insertNodeAtLast(a_node.getMutableTreeNode(), parent );
            method_nodes.add(a_node);
        }
    }
    
    private TreeNode containsAgent(String name) {
        TreeNode result = null;
        
        Iterator it = agent_nodes.iterator();
        while (it.hasNext()) {
            TreeNode node = (TreeNode) it.next();
            java_class agent = (java_class) node.getNodeEntity();
            if (agent.getName().equals(name))
                result = node;
        }
        
        return result;
    }
        
    private TreeNode containsTask(String name) {
        TreeNode result = null;
        
        Iterator it = task_nodes.iterator();
        while (it.hasNext()) {
            TreeNode node = (TreeNode) it.next();
            java_class task = (java_class) node.getNodeEntity();
            if (task.getIDString().equals(name))
                result = node;
        }
        
        return result;
    }

    private TreeNode containsRelation(String name) {
        TreeNode result = null;
        
        Iterator it = relation_nodes.iterator();
        while (it.hasNext()) {
            TreeNode node = (TreeNode) it.next();
            relation rel = (relation) node.getNodeEntity();
            if (rel.getName().equals(name))
                result = node;
        }
        
        return result;
    }

    private TreeNode containsAttribute(String name) {
        TreeNode result = null;
        
        Iterator it = attribute_nodes.iterator();
        while (it.hasNext()) {
            TreeNode node = (TreeNode) it.next();
            java_attribute attribute = (java_attribute) node.getNodeEntity();
            if (attribute.getIDString().equals(name))
                result = node;
        }
        
        return result;
    }
    
    private TreeNode containsMethod(String name) {
        TreeNode result = null;
        
        Iterator it = method_nodes.iterator();
        while (it.hasNext()) {
            TreeNode node = (TreeNode) it.next();
            java_method method = (java_method) node.getNodeEntity();
            if (method.getIDString().equals(name))
                result = node;
        }
        
        return result;
    }
}
